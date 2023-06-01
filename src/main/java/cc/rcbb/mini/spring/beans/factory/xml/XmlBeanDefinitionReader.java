package cc.rcbb.mini.spring.beans.factory.xml;

import cc.rcbb.mini.spring.beans.factory.PropertyValue;
import cc.rcbb.mini.spring.beans.factory.PropertyValues;
import cc.rcbb.mini.spring.beans.factory.config.BeanDefinition;
import cc.rcbb.mini.spring.beans.factory.config.ConstructorArgumentValue;
import cc.rcbb.mini.spring.beans.factory.config.ConstructorArgumentValues;
import cc.rcbb.mini.spring.beans.factory.support.AbstractBeanFactory;
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

    AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");

            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            // 处理构造器参数
            List<Element> constructorArgs = element.elements("constructor-arg");
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            for (Element e : constructorArgs) {
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                constructorArgumentValues.addArgumentValue(new ConstructorArgumentValue(type, name, value));
            }
            beanDefinition.setConstructorArgumentValues(constructorArgumentValues);

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

            String[] refArray = refList.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);

            this.beanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }

}
