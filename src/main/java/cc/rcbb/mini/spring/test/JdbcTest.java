package cc.rcbb.mini.spring.test;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.context.ClassPathXmlApplicationContext;

import java.util.List;

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
        TestUserDao testUserDao = (TestUserDao) ctx.getBean("testUserDao");
        TestUser testUser = testUserDao.get(1);
        System.out.println(testUser);
        System.out.println();
        List<TestUser> list = testUserDao.list();
        for (TestUser user : list) {
            System.out.println(user);
        }
        System.out.println();
        list = testUserDao.list(20);
        for (TestUser user : list) {
            System.out.println(user);
        }
        System.out.println();
        TestUser testUser1 = testUserDao.get1(2);
        System.out.println(testUser1);
    }

}
