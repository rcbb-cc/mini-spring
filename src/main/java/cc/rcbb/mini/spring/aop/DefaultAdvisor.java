package cc.rcbb.mini.spring.aop;

/**
 * <p>
 * DefaultAdvisor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/20
 */
public class DefaultAdvisor implements Advisor {

    private MethodInterceptor methodInterceptor;

    public DefaultAdvisor() {

    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

}
