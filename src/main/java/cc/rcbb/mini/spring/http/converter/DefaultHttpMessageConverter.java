package cc.rcbb.mini.spring.http.converter;

import cc.rcbb.mini.spring.util.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * DefaultHttpMessageConverter
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/7
 */
public class DefaultHttpMessageConverter implements HttpMessageConverter {

    String defaultContentType = "text/json;charset=UTF-8";
    String defaultCharacterEncoding = "UTF-8";
    ObjectMapper objectMapper;

    public DefaultHttpMessageConverter() {
    }

    @Override
    public void write(Object obj, HttpServletResponse response) throws IOException {
        response.setContentType(defaultContentType);
        response.setCharacterEncoding(defaultCharacterEncoding);

        writeInternal(obj, response);

        response.flushBuffer();
    }

    private void writeInternal(Object obj, HttpServletResponse response) throws IOException {
        String jsonStr = this.objectMapper.writeValuesAsString(obj);
        PrintWriter writer = response.getWriter();
        writer.write(jsonStr);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
