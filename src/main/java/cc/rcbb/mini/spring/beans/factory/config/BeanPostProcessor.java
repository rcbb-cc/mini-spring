package cc.rcbb.mini.spring.beans.factory.config;

import cc.rcbb.mini.spring.beans.BeansException;

/**
 * <p>
 * BeanPostProcessor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/20
 */
public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
