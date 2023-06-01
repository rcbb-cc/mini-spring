package cc.rcbb.mini.spring.beans.factory.support;

import cc.rcbb.mini.spring.beans.factory.config.BeanDefinition;

/**
 * <p>
 * BeanDefinitionRegistry
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/4
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);

}
