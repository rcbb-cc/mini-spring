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

    public TestUser getTestUser(Integer id) {
        return testUserDao.get(id);
    }

    public void setTestUserDao(TestUserDao testUserDao) {
        this.testUserDao = testUserDao;
    }
}
