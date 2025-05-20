package cc.rcbb.mini.spring.test.advice;

import cc.rcbb.mini.spring.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * <p>
 * MyAfterAdvice
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/20
 */
public class MyAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("my interceptor after method call");
    }
}
