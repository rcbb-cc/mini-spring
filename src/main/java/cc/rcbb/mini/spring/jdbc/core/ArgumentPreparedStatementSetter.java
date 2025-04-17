package cc.rcbb.mini.spring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>
 * ArgumentPreparedStatementSetter
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/16
 */
public class ArgumentPreparedStatementSetter {

    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args) {
        this.args = args;
    }

    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                Object arg = this.args[i];
                doSetValue(preparedStatement, i+1, arg);
            }
        }
    }

    protected void doSetValue(PreparedStatement preparedStatement, int position, Object arg) throws SQLException {
        if (arg instanceof String) {
            preparedStatement.setString(position, (String) arg);
        } else if (arg instanceof Integer) {
            preparedStatement.setInt(position, (int) arg);
        } else if (arg instanceof java.util.Date) {
            preparedStatement.setDate(position, new java.sql.Date(((java.util.Date) arg).getTime()));
        }
    }

}
