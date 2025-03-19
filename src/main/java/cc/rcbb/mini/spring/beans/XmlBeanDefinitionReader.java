package cc.rcbb.mini.spring.beans;

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

    private SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);

            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues argumentValues = new ArgumentValues();
            for (Element constructorElement : constructorElements) {
                ArgumentValue argumentValue = new ArgumentValue(
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

            this.simpleBeanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
    }

}
