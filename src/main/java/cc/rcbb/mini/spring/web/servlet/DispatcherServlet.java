package cc.rcbb.mini.spring.web.servlet;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.web.AnnotationConfigWebApplicationContext;
import cc.rcbb.mini.spring.web.WebApplicationContext;
import cc.rcbb.mini.spring.web.XmlScanComponentHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * <p>
 * DispatcherServlet
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/26
 */
public class DispatcherServlet extends HttpServlet {

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    private WebApplicationContext webApplicationContext;
    private WebApplicationContext parentApplicationContext;
    private String contextConfigLocation;
    private List<String> packageNames = new ArrayList<>();
    private List<String> controllerNames = new ArrayList<>();
    private Map<String, Object> controllerObjs = new HashMap<>();
    private Map<String, Class<?>> controllerClasses = new HashMap<>();

    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        this.contextConfigLocation = config.getInitParameter("contextConfigLocation");
        System.out.println("contextConfigLocation = " + this.contextConfigLocation);
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(this.contextConfigLocation);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        System.out.println("xmlPath = " + xmlPath);
        System.out.println("packageNames = " + this.packageNames);

        this.webApplicationContext = new AnnotationConfigWebApplicationContext(this.contextConfigLocation, this.parentApplicationContext);

        this.refresh();
    }

    protected void refresh() {
        this.initController();

        this.initHandlerMappings(this.webApplicationContext);
        this.initHandlerAdapters(this.webApplicationContext);
        this.initViewResolvers(this.webApplicationContext);
    }

    private void initViewResolvers(WebApplicationContext webApplicationContext) {

    }

    private void initHandlerAdapters(WebApplicationContext webApplicationContext) {
        this.handlerAdapter = new RequestMappingHandlerAdapter(webApplicationContext);
    }

    private void initHandlerMappings(WebApplicationContext webApplicationContext) {
        this.handlerMapping = new RequestMappingHandlerMapping(webApplicationContext);
    }

    protected void initController() {
        this.controllerNames = Arrays.asList(this.webApplicationContext.getBeanDefinitionNames());
        for (String controllerName : this.controllerNames) {
            try {
                this.controllerClasses.put(controllerName, Class.forName(controllerName));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                this.controllerObjs.put(controllerName, this.webApplicationContext.getBean(controllerName));
                System.out.println("controller = " + controllerName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try {
            this.doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpServletRequest processedRequest = req;
        HandlerMethod handlerMethod = null;

        handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }
        HandlerAdapter ha = this.handlerAdapter;
        ha.handle(processedRequest, res, handlerMethod);
    }

}
