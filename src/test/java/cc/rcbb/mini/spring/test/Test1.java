package cc.rcbb.mini.spring.test;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.context.ClassPathXmlApplicationContext;

/**
 * <p>
 * Test1
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public class Test1 {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        TestService testService = (TestService) ctx.getBean("testService");
        testService.sayHello();

        BaseBaseService baseBaseService = (BaseBaseService) ctx.getBean("baseBaseService");
        baseBaseService.sayHello();

        BaseService baseService = (BaseService) ctx.getBean("baseService");
        baseService.sayHello();
    }

}
