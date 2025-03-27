package cc.rcbb.mini.spring.test;

import cc.rcbb.mini.spring.beans.factory.annotation.Autowired;

/**
 * <p>
 * TestUserService
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/27
 */
public class TestUserService {

    @Autowired
    private TestUserDao testUserDao;

    public TestUser getTestUser() {
        return testUserDao.getTestUser();
    }

    public void setTestUserDao(TestUserDao testUserDao) {
        this.testUserDao = testUserDao;
    }
}
