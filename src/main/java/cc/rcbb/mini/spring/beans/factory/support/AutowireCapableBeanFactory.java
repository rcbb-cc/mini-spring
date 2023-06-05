package cc.rcbb.mini.spring.beans.factory.support;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.BeanFactory;

/**
 * <p>
 * AutowireCapableBeanFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/6/1
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    Object applyBeanPostProcessorsBeforeInitialization(Object singleton, String beanName) throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(Object singleton, String beanName) throws BeansException;

}
