package cc.rcbb.mini.spring.beans.factory.config;

/**
 * <p>
 * SingletonBeanRegistry
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/17
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames();

}
