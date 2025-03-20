package cc.rcbb.mini.spring.beans.factory;

import cc.rcbb.mini.spring.beans.BeansException;

/**
 * <p>
 * BeanFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    //void registerBeanDefinition(BeanDefinition beanDefinition);

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);

}
