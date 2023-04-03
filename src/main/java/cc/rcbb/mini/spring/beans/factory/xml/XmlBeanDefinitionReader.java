package cc.rcbb.mini.spring.beans.factory.xml;

import cc.rcbb.mini.spring.beans.config.BeanDefinition;
import cc.rcbb.mini.spring.beans.factory.BeanFactory;
import cc.rcbb.mini.spring.core.Resource;
import org.dom4j.Element;

/**
 * <p>
 * Xml
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/3
 */
public class XmlBeanDefinitionReader {

    BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }

}
