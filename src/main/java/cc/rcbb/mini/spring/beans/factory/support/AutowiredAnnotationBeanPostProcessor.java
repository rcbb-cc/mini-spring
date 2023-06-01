package cc.rcbb.mini.spring.beans.factory.support;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.annotation.Autowired;
import cc.rcbb.mini.spring.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * <p>
 * AutowiredAnnotationBeanPostProcessor
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/11
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;

        Class<?> clazz = bean.getClass();
        Field[] fieldArray = clazz.getDeclaredFields();
        if (fieldArray != null) {
            // 对每个属性进行判断，如果带有@Autowired注解则进行处理
            for (Field field : fieldArray) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    // 根据属性名查找同名的bean
                    String fieldName = field.getName();
                    Object autowiredObj = this.getBeanFactory().getBean(fieldName);
                    field.setAccessible(true);
                    try {
                        field.set(bean, autowiredObj);
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
