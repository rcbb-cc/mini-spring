package cc.rcbb.mini.spring.jdbc.core;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 * StatementCallback
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/15
 */
public interface StatementCallback {

    Object doInStatement(Statement statement) throws SQLException;

}
