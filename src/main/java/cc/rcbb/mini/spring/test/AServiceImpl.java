package cc.rcbb.mini.spring.test;

/**
 * <p>
 * AServiceImpl
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/3
 */
public class AServiceImpl implements AService{
    @Override
    public void sayHello() {
        System.out.println("a service 1 say hello");
    }
}
