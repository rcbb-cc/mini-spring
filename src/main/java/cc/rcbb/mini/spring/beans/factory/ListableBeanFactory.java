package cc.rcbb.mini.spring.beans.factory;

import cc.rcbb.mini.spring.beans.BeansException;

import java.util.Map;

/**
 * <p>
 * ListableBeanFactory
 * </p>
 * 将 Factory 内部管理的 Bean 作为一个集合来对待。
 * 获取 Bean 的数量。
 * 获取所有 Bean 的名字。
 * 按照某个类型获取 Bean 列表。
 *
 * @author rcbb.cc
 * @date 2023/6/5
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 是否包含该 beanName 的 BeanDefinition
     *
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取 BeanDefinition 的数量
     *
     * @return
     */
    int getBeanDefinitionCount();

    /**
     * 获取 BeanDefinition Name 的数组
     *
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * 根据某个类型获取 Bean name 的数组
     *
     * @param type
     * @return
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * 根据某个类型获取 Bean 列表
     *
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

}
