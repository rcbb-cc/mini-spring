package cc.rcbb.mini.spring.context;

import java.util.EventObject;

/**
 * <p>
 * ApplicationEvent
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/4
 */
public class ApplicationEvent extends EventObject {

    private static final long serialVersionUID = 1L;
    protected String msg = null;

    public ApplicationEvent(Object arg0) {
        super(arg0);
        this.msg = arg0.toString();
    }

}
