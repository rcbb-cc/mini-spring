package cc.rcbb.mini.spring.test;

import cc.rcbb.mini.spring.batis.SqlSession;
import cc.rcbb.mini.spring.batis.SqlSessionFactory;
import cc.rcbb.mini.spring.beans.factory.annotation.Autowired;
import cc.rcbb.mini.spring.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 * TestUserDao
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/27
 */
public class TestUserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public TestUser get1(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        final String sqlId = "cc.rcbb.mini.spring.test.TestUserDao.get1";
        return (TestUser) sqlSession.selectOne(sqlId, new Object[]{id}, (preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            TestUser user = null;
            if (resultSet.next()) {
                user = new TestUser();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                user.setBirthday(resultSet.getDate("birthday").toLocalDate());
            }
            return user;
        }));
    }

    public TestUser get(Integer id) {
        final String sql = "select id, name, age, birthday from tb_user where id = ?";
        return (TestUser) jdbcTemplate.query(sql, new Object[]{id}, (preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            TestUser user = null;
            if (resultSet.next()) {
                user = new TestUser();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                user.setBirthday(resultSet.getDate("birthday").toLocalDate());
            }
            return user;
        }));
    }

    public List<TestUser> list() {
        final String sql = "select id, name, age, birthday from tb_user";
        return jdbcTemplate.query(sql, null, (resultSet, rowNum) -> {
            TestUser user = new TestUser();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            user.setBirthday(resultSet.getDate("birthday").toLocalDate());
            return user;
        });
    }

    public List<TestUser> list(Integer age) {
        final String sql = "select id, name, age, birthday from tb_user where age > ?";
        return jdbcTemplate.query(sql, new Object[]{age}, (resultSet, rowNum) -> {
            TestUser user = new TestUser();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            user.setBirthday(resultSet.getDate("birthday").toLocalDate());
            return user;
        });
    }

}
