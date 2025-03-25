package cc.rcbb.mini.spring.context;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.ListableBeanFactory;
import cc.rcbb.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import cc.rcbb.mini.spring.beans.factory.config.ConfigurableBeanFactory;
import cc.rcbb.mini.spring.beans.factory.config.ConfigurableListableBeanFactory;
import cc.rcbb.mini.spring.core.env.Environment;
import cc.rcbb.mini.spring.core.env.EnvironmentCapable;

/**
 * <p>
 * ApplicationContext
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/25
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {

    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeansException, IllegalStateException;

    void close();

    boolean isActive();

}
