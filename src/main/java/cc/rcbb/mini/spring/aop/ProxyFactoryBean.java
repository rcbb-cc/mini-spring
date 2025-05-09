package cc.rcbb.mini.spring.aop;

import cc.rcbb.mini.spring.beans.factory.FactoryBean;
import cc.rcbb.mini.spring.util.ClassUtils;

/**
 * <p>
 * ProxyFactoryBean
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/5
 */
public class ProxyFactoryBean implements FactoryBean<Object> {

    private AopProxyFactory aopProxyFactory;
    private String[] interceptorNames;
    private String targetName;
    private Object target;
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private Object singletonInstance;

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    protected AopProxy createAopProxy() {
        return aopProxyFactory.createAopProxy(target);
    }

    @Override
    public Object getObject() throws Exception {
        return getSingletonInstance();
    }

    private synchronized Object getSingletonInstance() {
        if (this.singletonInstance == null) {
            this.singletonInstance = getProxy(createAopProxy());
        }
        return this.singletonInstance;
    }

    protected Object getProxy(AopProxy aopProxy) {
        return aopProxy.getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    public AopProxyFactory getAopProxyFactory() {
        return aopProxyFactory;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    public void setInterceptorNames(String... interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
