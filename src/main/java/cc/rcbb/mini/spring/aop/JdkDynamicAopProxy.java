package cc.rcbb.mini.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>
 * JdkDynamicAopProxy
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/5
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    Object target;

    public JdkDynamicAopProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object getProxy() {
        Object object = Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
        return object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("doAction".equals(method.getName())) {
            System.out.println("before call real object, dynamic proxy.");
            return method.invoke(target, args);
        }
        return null;
    }
}
