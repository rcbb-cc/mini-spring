package cc.rcbb.mini.spring.context;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import cc.rcbb.mini.spring.beans.factory.config.BeanPostProcessor;
import cc.rcbb.mini.spring.beans.factory.config.ConfigurableListableBeanFactory;
import cc.rcbb.mini.spring.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * AbstractApplicationContext
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/25
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private Environment environment;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
    private long startupDate;
    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        this.postProcessBeanFactory(this.getBeanFactory());

        this.registerBeanPostProcessors(this.getBeanFactory());

        this.initApplicationEventPublisher();

        this.onRefresh();

        this.registerListeners();

        this.finishRefresh();
    }

    abstract void postProcessBeanFactory(ConfigurableListableBeanFactory bf);
    abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory bf);
    abstract void initApplicationEventPublisher();
    abstract void onRefresh();
    abstract void registerListeners();
    abstract void finishRefresh();


    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return this.getBeanFactory().getBean(name);
    }

    @Override
    public boolean containsBean(String name) {
        return this.getBeanFactory().containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.getBeanFactory().isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.getBeanFactory().isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return this.getBeanFactory().getType(name);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return this.getBeanFactory().getBeanNamesForType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return this.getBeanFactory().getBeansOfType(type);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.getBeanFactory().addBeanPostProcessor(beanPostProcessor);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return this.getBeanFactory().getBeanPostProcessorCount();
    }

    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {
        this.getBeanFactory().registerDependentBean(beanName, dependentBeanName);
    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return this.getBeanFactory().getDependentBeans(beanName);
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return this.getBeanFactory().getDependenciesForBean(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        this.getBeanFactory().registerSingleton(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.getBeanFactory().getSingleton(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.getBeanFactory().containsSingleton(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return this.getBeanFactory().getSingletonNames();
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
