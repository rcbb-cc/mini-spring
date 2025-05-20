package cc.rcbb.mini.spring.aop;

import java.lang.reflect.Method;

/**
 * <p>
 * AfterReturningAdvice
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/20
 */
public interface AfterReturningAdvice extends AfterAdvice {

    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;

}
