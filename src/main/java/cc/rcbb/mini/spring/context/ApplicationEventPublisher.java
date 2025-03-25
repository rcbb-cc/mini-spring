package cc.rcbb.mini.spring.context;

/**
 * <p>
 * ApplicationEventPublisher
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/17
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

    void addApplicationListener(ApplicationListener listener);

}
