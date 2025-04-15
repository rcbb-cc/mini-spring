package cc.rcbb.mini.spring.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 * DefaultObjectMapper
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/7
 */
public class DefaultObjectMapper implements ObjectMapper {

    String dateFormat = "yyyy-MM-dd";
    DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    String decimalFormat = "#,##0.00";
    DecimalFormat decimalFormatter = new DecimalFormat(decimalFormat);

    public DefaultObjectMapper() {
    }

    @Override
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.datetimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public void setDecimalFormat(String decimalFormat) {
        this.decimalFormat = decimalFormat;
        this.decimalFormatter = new DecimalFormat(decimalFormat);
    }

    @Override
    public String writeValuesAsString(Object obj) {
        if (obj instanceof String) {
            return obj.toString();
        }
        String jsonStr = "{";

        Class<?> clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            Object value = null;
            String strValue = "";
            String fieldName = field.getName();
            field.setAccessible(true);
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (value instanceof Date) {
                LocalDate localDate = ((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                strValue = localDate.format(this.datetimeFormatter);
            } else if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
                strValue = this.decimalFormatter.format(value);
            } else {
                strValue = value.toString();
            }

            if (jsonStr.equals("{")) {
                jsonStr += "\"" + fieldName + "\":\"" + strValue + "\"";
            } else {
                jsonStr += ",\"" + fieldName + "\":\"" + strValue + "\"";
            }
        }
        jsonStr += "}";
        return jsonStr;
    }
}
