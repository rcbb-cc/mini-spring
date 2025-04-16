package cc.rcbb.mini.spring.test;

import cc.rcbb.mini.spring.beans.factory.annotation.Autowired;
import cc.rcbb.mini.spring.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;

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

}
