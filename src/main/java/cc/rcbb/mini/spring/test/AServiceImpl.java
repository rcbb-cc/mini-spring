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

    private int level;

    private String property1;

    private String property2;

    private BaseService baseService;

    public AServiceImpl() {
    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
        System.out.println(this.name + "," + this.level);
    }

    @Override
    public void sayHello() {
        System.out.println(this.property1 + "," + this.property2);
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public BaseService getBaseService() {
        return baseService;
    }

    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }
}
