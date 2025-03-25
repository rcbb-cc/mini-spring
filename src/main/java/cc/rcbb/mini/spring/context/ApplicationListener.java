package cc.rcbb.mini.spring.context;

import java.util.EventListener;

/**
 * <p>
 * ApplicationListener
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/24
 */
public class ApplicationListener implements EventListener {

    void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }

}
