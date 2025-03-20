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
    private TestServiceImpl testService;

    public TestServiceImpl getTestService() {
        return testService;
    }

    public void setTestService(TestServiceImpl testService) {
        this.testService = testService;
    }

    public BaseBaseService() {
    }

    public void sayHello() {
        System.out.println("Base Base Service says hello");

    }
}

