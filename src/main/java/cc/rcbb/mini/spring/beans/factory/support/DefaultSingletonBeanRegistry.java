package cc.rcbb.mini.spring.beans.factory.support;

import cc.rcbb.mini.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * DefaultSingletonBeanRegistry
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/4
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected List<String> beanNames = new ArrayList<>();

    protected Map<String, Object> singletonObjectMap = new ConcurrentHashMap<>(256);

    protected Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>();

    protected Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjectMap) {
            Object oldObject = this.singletonObjectMap.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            this.singletonObjectMap.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjectMap.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletonObjectMap.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletonObjectMap) {
            this.beanNames.remove(beanName);
            this.singletonObjectMap.remove(beanName);
        }
    }

    public void registerDependentBean(String beanName, String dependentBeanName) {
        Set<String> dependentBeanSet = this.dependentBeanMap.get(beanName);
        if (dependentBeanSet != null && dependentBeanSet.contains(dependentBeanName)) {
            return;
        }
        synchronized (this.dependentBeanMap) {
            dependentBeanSet = this.dependentBeanMap.get(beanName);
            if (dependentBeanSet == null) {
                dependentBeanSet = new LinkedHashSet<>(8);
                this.dependentBeanMap.put(beanName, dependentBeanSet);
            }
            dependentBeanSet.add(dependentBeanName);
        }
        synchronized (this.dependenciesForBeanMap) {
            Set<String> dependenciesForBeanSet = this.dependenciesForBeanMap.get(dependentBeanName);
            if (dependenciesForBeanSet == null) {
                dependenciesForBeanSet = new LinkedHashSet<>(8);
                this.dependenciesForBeanMap.put(dependentBeanName, dependenciesForBeanSet);
            }
            dependenciesForBeanSet.add(beanName);
        }
    }

    public boolean hasDependentBean(String beanName) {
        return this.dependentBeanMap.containsKey(beanName);
    }

    public String[] getDependentBeans(String beanName) {
        Set<String> dependentBeanSet = this.dependentBeanMap.get(beanName);
        if (dependentBeanSet == null) {
            return new String[0];
        }
        return (String[]) dependentBeanSet.toArray();
    }

    public String[] getDependenciesForBean(String beanName) {
        Set<String> dependenciesForBeanSet = this.dependenciesForBeanMap.get(beanName);
        if (dependenciesForBeanSet == null) {
            return new String[0];
        }
        return (String[]) dependenciesForBeanSet.toArray();
    }

}
