package cc.rcbb.mini.spring.util;

/**
 * <p>
 * ObjectMapper
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/7
 */
public interface ObjectMapper {
    void setDateFormat(String dateFormat);

    void setDecimalFormat(String decimalFormat);

    String writeValuesAsString(Object obj);
}
