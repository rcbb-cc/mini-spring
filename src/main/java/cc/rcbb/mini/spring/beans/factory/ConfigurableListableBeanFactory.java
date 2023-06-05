package cc.rcbb.mini.spring.beans.factory;

import cc.rcbb.mini.spring.beans.factory.support.AutowireCapableBeanFactory;

/**
 * <p>
 * ConfigurableListableBeanFactory
 * </p>
 * 将 ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory 合并
 *
 * @author rcbb.cc
 * @date 2023/6/5
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
}
