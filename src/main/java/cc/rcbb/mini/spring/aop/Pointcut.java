package cc.rcbb.mini.spring.aop;

/**
 * <p>
 * Pointcut
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/26
 */
public interface Pointcut {

    MethodMatcher getMethodMatcher();

}
