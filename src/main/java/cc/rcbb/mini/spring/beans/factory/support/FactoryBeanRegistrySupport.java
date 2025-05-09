package cc.rcbb.mini.spring.beans.factory.support;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.FactoryBean;

/**
 * <p>
 * FactoryBeanRegistrySupport
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/24
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean) {
        return factoryBean.getObjectType();
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        Object object = doGetObjectFromFactoryBean(factory, beanName);
        try {
            object = postProcessObjectFromFactoryBean(object, beanName);
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName) {
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException {
        return object;
    }

    protected FactoryBean<?> getFactoryBean(String beanName, Object beanInstance) throws BeansException {
        if (!(beanInstance instanceof FactoryBean)) {
            throw new BeansException(
                    "Bean instance of type [" + beanInstance.getClass() + "] is not a FactoryBean");
        }
        return (FactoryBean<?>) beanInstance;
    }

}
