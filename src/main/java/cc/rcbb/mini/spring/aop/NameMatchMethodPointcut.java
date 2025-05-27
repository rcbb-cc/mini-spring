package cc.rcbb.mini.spring.aop;

import cc.rcbb.mini.spring.util.PatternMatchUtils;

import java.lang.reflect.Method;

/**
 * <p>
 * NameMatchMethodPointcut
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/26
 */
public class NameMatchMethodPointcut implements MethodMatcher, Pointcut {

    private String mappedName = "";

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (mappedName.equals(method.getName()) || isMatch(method.getName(), mappedName)) {
            return true;
        }
        return false;
    }

    private boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
