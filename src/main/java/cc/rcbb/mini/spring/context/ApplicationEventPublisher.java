package cc.rcbb.mini.spring.context;

/**
 * <p>
 * ApplicationEventPublisher
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/4
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

}
