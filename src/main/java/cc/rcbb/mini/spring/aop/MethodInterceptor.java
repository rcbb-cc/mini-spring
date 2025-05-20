package cc.rcbb.mini.spring.aop;

/**
 * <p>
 * MethodInterceptor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/19
 */
public interface MethodInterceptor extends Interceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;

}
