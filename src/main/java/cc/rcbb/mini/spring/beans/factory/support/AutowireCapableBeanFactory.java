package cc.rcbb.mini.spring.beans.factory.support;

import cc.rcbb.mini.spring.beans.BeansException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * AutowireCapableBeanFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/6/1
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessorList.remove(beanPostProcessor);
        this.beanPostProcessorList.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessorList.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessorList() {
        return this.beanPostProcessorList;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object singleton, String beanName) throws BeansException {
        Object result = singleton;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            beanPostProcessor.setBeanFactory(this);
            result = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object singleton, String beanName) throws BeansException {
        Object result = singleton;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            beanPostProcessor.setBeanFactory(this);
            result = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }


}
