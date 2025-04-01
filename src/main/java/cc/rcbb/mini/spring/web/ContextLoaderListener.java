package cc.rcbb.mini.spring.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <p>
 * ContextLoaderListener
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/27
 */
public class ContextLoaderListener implements ServletContextListener {
    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
    private WebApplicationContext context;

    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext context) {
        this.context = context;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.initWebApplicationContext(servletContextEvent.getServletContext());
    }

    private void initWebApplicationContext(ServletContext servletContext) {
        String locationParam = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        System.out.println("locationParam = " + locationParam);
        WebApplicationContext wac = new XmlWebApplicationContext(locationParam);
        wac.setServletContext(servletContext);
        this.context = wac;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
