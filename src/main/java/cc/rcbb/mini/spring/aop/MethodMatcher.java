package cc.rcbb.mini.spring.aop;

import java.lang.reflect.Method;

/**
 * <p>
 * MethodMatcher
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/26
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);

}
