package cc.rcbb.mini.spring.web;

import cc.rcbb.mini.spring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * <p>
 * XmlWebApplicationContext
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/31
 */
public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
