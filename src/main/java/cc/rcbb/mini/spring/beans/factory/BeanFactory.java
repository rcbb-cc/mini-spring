package cc.rcbb.mini.spring.beans.factory;

import cc.rcbb.mini.spring.beans.config.BeanDefinition;
import cc.rcbb.mini.spring.beans.BeansException;

/**
 * <p>
 * BeanFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/3
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void refresh();

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    void registerBean(String beanName, Object obj);

    void registerBeanDefinition(BeanDefinition beanDefinition);

    Class<?> getType(String name);

}
