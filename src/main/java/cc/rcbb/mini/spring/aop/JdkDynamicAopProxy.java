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
    Advisor advisor;

    public JdkDynamicAopProxy(Object target, Advisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public Object getProxy() {
        Object object = Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
        return object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("doAction".equals(method.getName())) {
            Class<?> targetClass = target != null ? target.getClass() : null;
            MethodInterceptor interceptor = this.advisor.getMethodInterceptor();
            MethodInvocation invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass);
            System.out.println("before call real object, dynamic proxy.");
            return interceptor.invoke(invocation);
        }
        return null;
    }
}
