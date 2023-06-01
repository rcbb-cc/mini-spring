package cc.rcbb.mini.spring.beans.factory.config;

import cc.rcbb.mini.spring.beans.BeansException;

/**
 * <p>
 * BeanPostProcessor
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/11
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
