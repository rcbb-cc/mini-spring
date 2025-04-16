package cc.rcbb.mini.spring.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <p>
 * PreparedStatementCallback
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/15
 */
public interface PreparedStatementCallback {

    Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException;

}
