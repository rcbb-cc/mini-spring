package cc.rcbb.mini.spring.aop;

/**
 * <p>
 * AopProxyFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/5
 */
public interface AopProxyFactory {

    AopProxy createAopProxy(Object target, Advisor advisor);

}
