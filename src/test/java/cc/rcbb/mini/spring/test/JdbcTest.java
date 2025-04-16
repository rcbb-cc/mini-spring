package cc.rcbb.mini.spring.test;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.context.ClassPathXmlApplicationContext;

/**
 * <p>
 * JdbcTest
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/15
 */
public class JdbcTest {

    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        TestUserService testUserService = (TestUserService) ctx.getBean("testUserService");
        TestUser testUser = testUserService.getTestUser(1);
        System.out.println(testUser);
    }

}
