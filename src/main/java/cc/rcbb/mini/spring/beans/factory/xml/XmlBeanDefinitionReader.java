package cc.rcbb.mini.spring.beans.factory.xml;

import cc.rcbb.mini.spring.beans.config.BeanDefinition;
import cc.rcbb.mini.spring.beans.factory.*;
import cc.rcbb.mini.spring.core.Resource;
import org.dom4j.Element;

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

            List<Element> propertyElements = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();
            for (Element e : propertyElements) {
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                propertyValues.addPropertyValue(new PropertyValue(type, name, value));
            }
            beanDefinition.setPropertyValues(propertyValues);

            List<Element> constructorArgs = element.elements("constructor-arg");
            ArgumentValues argumentValues = new ArgumentValues();
            for (Element e : constructorArgs) {
                String type = e.attributeValue("type");
                String name = e.attributeValue("name");
                String value = e.attributeValue("value");
                argumentValues.addArgumentValue(new ArgumentValue(type, name, value));
            }
            beanDefinition.setConstructorArgumentValues(argumentValues);
            this.simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }

}
