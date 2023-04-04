package cc.rcbb.mini.spring.beans.factory;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.config.BeanDefinition;

/**
 * <p>
 * SingletonBeanRegistry
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/3
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    Boolean containsSingleton(String beanName);

    String[] getSingletonNames();

}
