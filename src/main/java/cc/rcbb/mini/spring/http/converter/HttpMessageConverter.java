package cc.rcbb.mini.spring.http.converter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * HttpMessageConverter
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/7
 */
public interface HttpMessageConverter {

    void write(Object obj, HttpServletResponse response) throws IOException;

}
