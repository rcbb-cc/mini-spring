package cc.rcbb.mini.spring.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * SimpleBeanFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public class SimpleBeanFactory implements BeanFactory {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();
    private Map<String, Object> singletons = new HashMap<>();

    public SimpleBeanFactory() {
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = singletons.get(beanName);
        if (singleton == null) {
            int index = beanName.indexOf(beanName);
            if (index == -1) {
                throw new BeansException();
            } else {
                BeanDefinition beanDefinition = beanDefinitions.get(index);
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                    singletons.put(beanName, singleton);
                } catch (ClassNotFoundException e) {
                    throw new BeansException(e.getMessage());
                } catch (InstantiationException e) {
                    throw new BeansException(e.getMessage());
                } catch (IllegalAccessException e) {
                    throw new BeansException(e.getMessage());
                }
            }
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
