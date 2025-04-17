package cc.rcbb.mini.spring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * ResultSetExtractor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/16
 */
public interface ResultSetExtractor<T> {

    T extractData(ResultSet resultSet) throws SQLException;

}
