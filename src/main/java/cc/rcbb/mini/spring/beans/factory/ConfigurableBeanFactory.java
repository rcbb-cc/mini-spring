package cc.rcbb.mini.spring.beans.factory;

import cc.rcbb.mini.spring.beans.factory.config.BeanPostProcessor;
import cc.rcbb.mini.spring.beans.factory.config.SingletonBeanRegistry;

/**
 * <p>
 * ConfigurableBeanFactory
 * </p>
 * 维护 Bean 之间的依赖关系以及支持 Bean 处理器
 *
 * @author rcbb.cc
 * @date 2023/6/5
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTORYPE = "protorype";

    /**
     * 添加 Bean后置处理器
     *
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 获取 Bean后置处理器的数量
     *
     * @return
     */
    int getBeanPostProcessorCount();

    /**
     * 注册依赖 Bean
     *
     * @param beanName
     * @param dependentBeanName
     */
    void registerDependentBean(String beanName, String dependentBeanName);

    /**
     * 获取所有依赖 Bean 名称数组
     *
     * @param beanName
     * @return
     */
    String[] getDependentBeans(String beanName);

    /**
     * 根据 beanName 获取所有依赖 Bean 名称数组
     * @param beanName
     * @return
     */
    String[] getDependenciesForBean(String beanName);

}
