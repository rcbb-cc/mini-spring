package cc.rcbb.mini.spring.context;

/**
 * <p>
 * ContextRefreshEvent
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/24
 */
public class ContextRefreshEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public ContextRefreshEvent(Object source) {
        super(source);
    }

    public String toString() {
        return this.msg;
    }
}
