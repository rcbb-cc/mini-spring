package cc.rcbb.mini.spring.beans.factory;

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

    void refresh() throws BeansException, IllegalStateException;

    Object getBean(String beanName) throws BeansException;

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    void registerBean(String beanName, Object obj);

    Class<?> getType(String name);

}
