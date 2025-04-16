package cc.rcbb.mini.spring.jdbc.core;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

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
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg instanceof String) {
                        preparedStatement.setString(i + 1, (String) arg);
                    } else if (arg instanceof Integer) {
                        preparedStatement.setInt(i + 1, (int) arg);
                    } else if (arg instanceof java.util.Date) {
                        preparedStatement.setDate(i + 1, new java.sql.Date(((java.util.Date) arg).getTime()));
                    }
                }
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

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
