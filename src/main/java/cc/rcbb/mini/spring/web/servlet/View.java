package cc.rcbb.mini.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * View
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/7
 */
public interface View {

    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;

    default String getContentType() {
        return null;
    }

    void setContentType(String contentType);

    void setUrl(String url);

    String getUrl();

    void setRequestContextAttribute(String requestContextAttribute);

    String getRequestContextAttribute();

}
