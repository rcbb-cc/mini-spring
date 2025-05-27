package cc.rcbb.mini.spring.test;

/**
 * <p>
 * ActionImpl
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/5/5
 */
public class ActionImpl implements IAction {
    @Override
    public void doAction() {
        System.out.println("doAction");
    }

    @Override
    public void doSomething() {
        System.out.println("doSomething");
    }
}
