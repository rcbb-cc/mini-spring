package cc.rcbb.mini.spring.aop;

import java.lang.reflect.Method;

/**
 * <p>
 * MethodBeforeAdvice
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/20
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    void before(Method method, Object[] args, Object target) throws Throwable;

}
