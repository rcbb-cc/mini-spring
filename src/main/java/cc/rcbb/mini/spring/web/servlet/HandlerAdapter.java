package cc.rcbb.mini.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * HandlerAdapter
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/31
 */
public interface HandlerAdapter {

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

}
