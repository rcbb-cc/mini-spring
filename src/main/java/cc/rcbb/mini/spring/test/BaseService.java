package cc.rcbb.mini.spring.test;

/**
 * <p>
 * BaseService
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/4
 */
public class BaseService {

    private BaseBaseService bbs;

    public BaseBaseService getBbs() {
        return bbs;
    }
    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }
    public BaseService() {
    }
    public void sayHello() {
        System.out.print("Base Service says hello");
        bbs.sayHello();
    }

}
