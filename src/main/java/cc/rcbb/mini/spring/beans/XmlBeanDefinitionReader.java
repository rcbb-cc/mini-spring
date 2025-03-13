package cc.rcbb.mini.spring.beans;

import cc.rcbb.mini.spring.core.Resource;
import org.dom4j.Element;

/**
 * <p>
 * XmlBeanDefinitionReader
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public class XmlBeanDefinitionReader {

    private BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }

}
