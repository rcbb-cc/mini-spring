package cc.rcbb.mini.spring.beans.factory.xml;

import cc.rcbb.mini.spring.beans.BeanDefinition;
import cc.rcbb.mini.spring.beans.PropertyValue;
import cc.rcbb.mini.spring.beans.PropertyValues;
import cc.rcbb.mini.spring.beans.factory.config.ConstructorArgumentValue;
import cc.rcbb.mini.spring.beans.factory.config.ConstructorArgumentValues;
import cc.rcbb.mini.spring.beans.factory.support.AbstractBeanFactory;
import cc.rcbb.mini.spring.core.Resource;
import org.dom4j.Element;

import java.util.List;

/**
 * <p>
 * XmlBeanDefinitionReader
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public class XmlBeanDefinitionReader {

    private AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory simpleBeanFactory) {
        this.beanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            String initMethod = element.attributeValue("init-method");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            beanDefinition.setInitMethodName(initMethod);

            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
            for (Element constructorElement : constructorElements) {
                ConstructorArgumentValue argumentValue = new ConstructorArgumentValue(
                        constructorElement.attributeValue("type"),
                        constructorElement.attributeValue("name"),
                        constructorElement.attributeValue("value")
                );
                argumentValues.addArgumentValue(argumentValue);
            }
            beanDefinition.setConstructorArgumentValues(argumentValues);

            List<Element> propertyElements = element.elements("property");
            PropertyValues propertyValues = new PropertyValues();
            for (Element propertyElement : propertyElements) {
                boolean isRef = false;
                String ref = propertyElement.attributeValue("ref");
                if (ref != null && ref != "") {
                    isRef = true;
                }
                PropertyValue propertyValue = new PropertyValue(
                        propertyElement.attributeValue("type"),
                        propertyElement.attributeValue("name"),
                        isRef ? ref : propertyElement.attributeValue("value"),
                        isRef
                );
                propertyValues.addPropertyValue(propertyValue);
            }
            beanDefinition.setPropertyValues(propertyValues);

            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }

}
