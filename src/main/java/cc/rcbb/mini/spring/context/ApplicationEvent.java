package cc.rcbb.mini.spring.context;

import java.util.EventObject;

/**
 * <p>
 * ApplicationEvent
 * </p>
 *
 * @author lvhao
 * @date 2023/4/4
 */
public class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }

}
