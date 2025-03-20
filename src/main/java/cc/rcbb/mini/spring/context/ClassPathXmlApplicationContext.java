package cc.rcbb.mini.spring.context;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.BeanFactory;
import cc.rcbb.mini.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import cc.rcbb.mini.spring.beans.factory.config.AutowireCapableBeanFactory;
import cc.rcbb.mini.spring.beans.factory.xml.XmlBeanDefinitionReader;
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

    private AutowireCapableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

        this.beanFactory = beanFactory;
        if (isRefresh) {
            try {
                this.refresh();
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void refresh() throws BeansException, IllegalStateException {
        this.registerBeanPostProcessors(this.beanFactory);
        this.onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
        //if (supportAutowire) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
        //}
    }

    private void onRefresh() {
        this.beanFactory.refresh();
    }


    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
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
