package cc.rcbb.mini.spring.test;

import cc.rcbb.mini.spring.ClassPathXmlApplicationContext;

/**
 * <p>
 * Test1
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public class Test1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new
                ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) ctx.getBean("aservice");
        aService.sayHello();
    }

}
