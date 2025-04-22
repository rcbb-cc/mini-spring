package cc.rcbb.mini.spring.batis;

import cc.rcbb.mini.spring.jdbc.core.JdbcTemplate;
import cc.rcbb.mini.spring.jdbc.core.PreparedStatementCallback;

/**
 * <p>
 * DefaultSqlSession
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/21
 */
public class DefaultSqlSession implements SqlSession {

    private JdbcTemplate jdbcTemplate;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Override
    public Object selectOne(String sqlId, Object[] args, PreparedStatementCallback preparedStatementCallback) {
        System.out.println("sqlId = " + sqlId);
        String sql = this.sqlSessionFactory.getMapperNode(sqlId).getSql();
        System.out.println("sql = " + sql);
        return this.jdbcTemplate.query(sql, args, preparedStatementCallback);
    }

}
