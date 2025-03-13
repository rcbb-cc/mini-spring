package cc.rcbb.mini.spring.beans;

/**
 * <p>
 * BeanFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    void registerBeanDefinition(BeanDefinition beanDefinition);

}
