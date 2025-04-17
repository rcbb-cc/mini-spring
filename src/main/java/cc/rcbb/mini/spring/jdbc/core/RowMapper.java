package cc.rcbb.mini.spring.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * RowMapper
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/16
 */
public interface RowMapper<T> {

    T mapRow(ResultSet resultSet, int rowNum) throws SQLException;

}
