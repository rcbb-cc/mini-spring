package cc.rcbb.mini.spring.aop;

import java.lang.reflect.Method;

/**
 * <p>
 * MethodInvocation
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/19
 */
public interface MethodInvocation {

    Method getMethod();

    Object[] getArguments();

    Object getThis();

    Object proceed() throws Throwable;

}
