package cc.rcbb.mini.spring.test;

/**
 * <p>
 * AServiceImpl
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public class TestServiceImpl implements TestService {

    private String name;
    private int level;
    private String property1;
    private String property2;
    private BaseService baseService;

    public TestServiceImpl() {
    }

    public TestServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
        System.out.println(this.name + "," + this.level);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
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

    @Override
    public void sayHello() {
        System.out.println(this.property1 + "," + this.property2);
    }
}
