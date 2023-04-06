package cc.rcbb.mini.spring.beans.factory.xml;

import cc.rcbb.mini.spring.beans.config.BeanDefinition;
import cc.rcbb.mini.spring.beans.factory.*;
import cc.rcbb.mini.spring.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Xml
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/3
 */
public class XmlBeanDefinitionReader {

    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");

            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            // 处理构造器参数
            List<Element> constructorArgs = element.elements("constructor-arg");
            ArgumentValues argumentValues = new ArgumentValues();
            for (Element e : constructorArgs) {
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                argumentValues.addArgumentValue(new ArgumentValue(type, name, value));
            }
            beanDefinition.setConstructorArgumentValues(argumentValues);

            // 处理属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();
            List<String> refList = new ArrayList<>();
            for (Element e : propertyElements) {
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                String ref = e.attributeValue("ref");
                String v = "";

                boolean isRef = false;
                if (value != null && !value.equals("")) {
                    isRef = false;
                    v = value;
                } else if (ref != null && !ref.equals("")) {
                    isRef = true;
                    // 如果是ref，则将ref里的值赋值给v
                    v = ref;
                    refList.add(ref);
                }
                // value填充值是v
                propertyValues.addPropertyValue(new PropertyValue(type, name, v, isRef));
            }
            beanDefinition.setPropertyValues(propertyValues);

            String[] refs = refList.toArray(new String[0]);
            beanDefinition.setDependsOn(refs);

            this.simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }

}
