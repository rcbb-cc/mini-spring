package cc.rcbb.mini.spring.aop;

/**
 * <p>
 * Advisor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/19
 */
public interface Advisor {

    MethodInterceptor getMethodInterceptor();

    void setMethodInterceptor(MethodInterceptor methodInterceptor);

}
