package cc.rcbb.mini.spring.test.advice;

import cc.rcbb.mini.spring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * <p>
 * MyBeforeAdvice
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/20
 */
public class MyBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("my interceptor before method cal");
    }
}
