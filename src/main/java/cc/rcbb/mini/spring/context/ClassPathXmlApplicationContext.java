package cc.rcbb.mini.spring.context;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.BeanFactory;
import cc.rcbb.mini.spring.beans.factory.support.AutowireCapableBeanFactory;
import cc.rcbb.mini.spring.beans.factory.support.AutowiredAnnotationBeanPostProcessor;
import cc.rcbb.mini.spring.beans.factory.xml.XmlBeanDefinitionReader;
import cc.rcbb.mini.spring.core.ClassPathXmlResource;
import cc.rcbb.mini.spring.core.Resource;

/**
 * <p>
 * ClassPathXmlApplicationContext
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/3
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    AutowireCapableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        // 解析XML文件中的内容
        Resource resource = new ClassPathXmlResource(fileName);
        AutowireCapableBeanFactory beanFactory = new AutowireCapableBeanFactory();
        // 加载解析内容，构建BeanDefinition
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 读取BeanDefinition的配置信息，实例化Bean，注入到BeanFactory
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            this.refresh();
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public void refresh() {
        registerBeanPostProcessors(this.beanFactory);
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    private void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanFactory.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return this.getClass();
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
