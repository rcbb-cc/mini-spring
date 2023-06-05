package cc.rcbb.mini.spring.beans.factory.support;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * AbstractAutowireCapableBeanFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/6/5
 */
public class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessorList.remove(beanPostProcessor);
        this.beanPostProcessorList.add(beanPostProcessor);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return this.beanPostProcessorList.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessorList() {
        return this.beanPostProcessorList;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object singleton, String beanName) throws BeansException {
        Object result = singleton;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
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
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessorList()) {
            beanPostProcessor.setBeanFactory(this);
            result = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }


}
