package cc.rcbb.mini.spring.batis;

import cc.rcbb.mini.spring.jdbc.core.JdbcTemplate;
import cc.rcbb.mini.spring.jdbc.core.PreparedStatementCallback;

/**
 * <p>
 * SqlSession
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/21
 */
public interface SqlSession {

    void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    Object selectOne(String sqlId, Object[] args, PreparedStatementCallback preparedStatementCallback);

}
