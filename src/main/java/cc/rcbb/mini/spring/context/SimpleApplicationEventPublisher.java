package cc.rcbb.mini.spring.context;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * SimpleApplicationEventPublisher
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/24
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {

    List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }
}
