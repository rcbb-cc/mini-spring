package cc.rcbb.mini.spring.beans.factory.annotation;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.config.AutowireCapableBeanFactory;
import cc.rcbb.mini.spring.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * <p>
 * AutowiredAnnotationBeanPostProcessor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/20
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        Class<?> clz = result.getClass();
        Field[] fields = clz.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    String fieldName = field.getName();
                    Object autowireObject = this.getBeanFactory().getBean(fieldName);
                    field.setAccessible(true);
                    try {
                        field.set(bean, autowireObject);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
