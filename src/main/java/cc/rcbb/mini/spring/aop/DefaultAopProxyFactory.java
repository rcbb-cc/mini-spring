package cc.rcbb.mini.spring.aop;

/**
 * <p>
 * DefaultAopProxyFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/5
 */
public class DefaultAopProxyFactory implements AopProxyFactory {
    @Override
    public AopProxy createAopProxy(Object target, PointcutAdvisor advisor) {
        return new JdkDynamicAopProxy(target, advisor);
    }
}
