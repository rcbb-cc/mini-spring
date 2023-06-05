package cc.rcbb.mini.spring.context;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * SimpleApplicationEventPublisher
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/6/5
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {

    List<ApplicationListener> listenerList = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : listenerList) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listenerList.add(listener);
    }
}
