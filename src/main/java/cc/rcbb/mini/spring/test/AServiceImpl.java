package cc.rcbb.mini.spring.test;

/**
 * <p>
 * AServiceImpl
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/3
 */
public class AServiceImpl implements AService {

    private String name;

    public AServiceImpl() {
    }

    public AServiceImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void sayHello() {
        System.out.println("a service 1 say hello");
    }
}
