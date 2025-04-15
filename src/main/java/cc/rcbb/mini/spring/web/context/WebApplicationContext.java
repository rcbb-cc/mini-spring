package cc.rcbb.mini.spring.web.context;

import cc.rcbb.mini.spring.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * <p>
 * WebApplicationContext
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/27
 */
public interface WebApplicationContext extends ApplicationContext {

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();

    void setServletContext(ServletContext servletContext);

}
