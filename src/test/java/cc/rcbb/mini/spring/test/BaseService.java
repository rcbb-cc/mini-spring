package cc.rcbb.mini.spring.test;

import cc.rcbb.mini.spring.beans.factory.annotation.Autowired;

/**
 * <p>
 * BaseService
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/19
 */
public class BaseService {
    @Autowired
    private BaseBaseService baseBaseService;

    public BaseBaseService getBaseBaseService() {
        return baseBaseService;
    }

    public void setBaseBaseService(BaseBaseService baseBaseService) {
        this.baseBaseService = baseBaseService;
    }

    public BaseService() {
    }

    public void sayHello() {
        System.out.print("Base Service says hello");
        baseBaseService.sayHello();
    }
}
