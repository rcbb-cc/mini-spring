package cc.rcbb.mini.spring.beans.factory.config;

import cc.rcbb.mini.spring.beans.factory.BeanFactory;

/**
 * <p>
 * ConfigurableBeanFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/24
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependentBeanName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);
}
