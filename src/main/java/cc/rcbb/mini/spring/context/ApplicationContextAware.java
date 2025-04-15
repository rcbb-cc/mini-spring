package cc.rcbb.mini.spring.context;

import cc.rcbb.mini.spring.beans.BeansException;

/**
 * <p>
 * ApplicationContextAware
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/7
 */
public interface ApplicationContextAware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
