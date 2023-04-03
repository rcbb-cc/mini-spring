package cc.rcbb.mini.spring.test;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.context.ClassPathXmlApplicationContext;

/**
 * <p>
 * Test1
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/3
 */
public class Test1 {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        AService aservice = (AService) ctx.getBean("aservice");
        aservice.sayHello();
    }

}
