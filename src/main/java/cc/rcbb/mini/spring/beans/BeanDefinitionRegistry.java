package cc.rcbb.mini.spring.beans;

/**
 * <p>
 * BeanDefinitionRegistry
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/17
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    void removeBeanDefinition(String beanName);

    BeanDefinition getBeanDefinition(String beanName);

    boolean containsBeanDefinition(String beanName);
}
