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

    void registerBeanDefinition(BeanDefinition beanDefinition);

}
