package cc.rcbb.mini.spring.aop;

/**
 * <p>
 * PointcutAdvisor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/26
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();

}
