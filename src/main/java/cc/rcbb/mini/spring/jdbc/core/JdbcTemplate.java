package cc.rcbb.mini.spring.jdbc.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * <p>
 * JdbcTemplate
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/15
 */
public class JdbcTemplate {

    private DataSource dataSource;

    public JdbcTemplate() {

    }

    public Object query(StatementCallback statementCallback) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            return statementCallback.doInStatement(statement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Object query(String sql, Object[] args, PreparedStatementCallback preparedStatementCallback) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (args != null) {
                ArgumentPreparedStatementSetter argumentPreparedStatementSetter = new ArgumentPreparedStatementSetter(args);
                argumentPreparedStatementSetter.setValues(preparedStatement);
                return preparedStatementCallback.doInPreparedStatement(preparedStatement);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        RowMapperResultSetExtractor<T> rowMapperResultSetExtractor = new RowMapperResultSetExtractor(rowMapper);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (args != null) {
                ArgumentPreparedStatementSetter argumentPreparedStatementSetter = new ArgumentPreparedStatementSetter(args);
                argumentPreparedStatementSetter.setValues(preparedStatement);
            }
            return rowMapperResultSetExtractor.extractData(preparedStatement.executeQuery());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
