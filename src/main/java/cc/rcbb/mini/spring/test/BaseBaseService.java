package cc.rcbb.mini.spring.test;

/**
 * <p>
 * BaseService
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/4
 */
public class BaseBaseService {

    private AServiceImpl aService;

    public AServiceImpl getAService() {
        return aService;
    }
    public void setAService(AServiceImpl aService) {
        this.aService = aService;
    }
    public BaseBaseService() {
    }
    public void sayHello() {
        System.out.println("Base Base Service says hello");

    }
}
