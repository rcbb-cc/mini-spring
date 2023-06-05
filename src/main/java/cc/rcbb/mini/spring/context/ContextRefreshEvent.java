package cc.rcbb.mini.spring.context;

/**
 * <p>
 * ContextRefreshEvent
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/6/5
 */
public class ContextRefreshEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public ContextRefreshEvent(Object arg0) {
        super(arg0);
    }

    public String toString() {
        return this.msg;
    }

}
