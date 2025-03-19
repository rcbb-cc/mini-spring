package cc.rcbb.mini.spring.test;

/**
 * <p>
 * BaseBaseService
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/19
 */
public class BaseBaseService {
    private AServiceImpl as;

    public AServiceImpl getAs() {
        return as;
    }

    public void setAs(AServiceImpl as) {
        this.as = as;
    }

    public BaseBaseService() {
    }

    public void sayHello() {
        System.out.println("Base Base Service says hello");

    }
}

