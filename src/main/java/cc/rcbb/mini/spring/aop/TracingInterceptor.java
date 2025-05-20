package cc.rcbb.mini.spring.aop;

/**
 * <p>
 * TracingInterceptor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/19
 */
public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("method " + invocation.getMethod() + " is called on " + invocation.getThis() + " with args " + invocation.getArguments());
        Object ret = invocation.proceed();
        System.out.println("method " + invocation.getMethod() + " returns " + ret);
        return ret;
    }
}
