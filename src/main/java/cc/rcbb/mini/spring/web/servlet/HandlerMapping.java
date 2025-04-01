package cc.rcbb.mini.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * HandlerMapping
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/31
 */
public interface HandlerMapping {

    HandlerMethod getHandler(HttpServletRequest request) throws Exception;

}
