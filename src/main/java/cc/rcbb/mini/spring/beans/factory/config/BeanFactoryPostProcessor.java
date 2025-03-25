package cc.rcbb.mini.spring.beans.factory.config;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.BeanFactory;

/**
 * <p>
 * BeanFactoryPostProcessor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/25
 */
public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
