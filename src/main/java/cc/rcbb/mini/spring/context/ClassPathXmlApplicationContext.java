package cc.rcbb.mini.spring.context;

import cc.rcbb.mini.spring.beans.*;
import cc.rcbb.mini.spring.core.ClassPathXmlResource;
import cc.rcbb.mini.spring.core.Resource;

/**
 * <p>
 * ClassPathXmlApplicationContext
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    private SimpleBeanFactory simpleBeanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        this.simpleBeanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this.simpleBeanFactory);
        reader.loadBeanDefinitions(resource);
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.simpleBeanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBean(String name) {
        return this.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

}
