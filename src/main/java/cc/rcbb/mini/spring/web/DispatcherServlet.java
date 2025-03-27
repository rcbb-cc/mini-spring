package cc.rcbb.mini.spring.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * DispatcherServlet
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/26
 */
public class DispatcherServlet extends HttpServlet {

    private WebApplicationContext webApplicationContext;
    private String contextConfigLocation;
    private List<String> packageNames = new ArrayList<>();
    private List<String> controllerNames = new ArrayList<>();
    private Map<String, Object> controllerObjs = new HashMap<>();
    private Map<String, Class<?>> controllerClasses = new HashMap<>();
    private List<String> urlMappingNames = new ArrayList<>();
    private Map<String, Object> mappingObjs = new HashMap<>();
    private Map<String, Method> mappingMethods = new HashMap<>();

    public DispatcherServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
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

        this.refresh();
    }

    protected void refresh() {
        this.initController();
        this.initMapping();
    }

    protected void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();
            if (methods != null) {
                for (Method method : methods) {
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (isRequestMapping) {
                        String methodName = method.getName();
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        System.out.println("urlMapping = " + urlMapping);
                        this.urlMappingNames.add(urlMapping);
                        this.mappingObjs.put(urlMapping, obj);
                        this.mappingMethods.put(urlMapping, method);
                    }
                }
            }
        }
    }

    protected void initController() {
        this.controllerNames = this.scanPackages(this.packageNames);
        for (String controllerName : this.controllerNames) {
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName, clz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                obj = clz.newInstance();
                this.controllerObjs.put(controllerName, obj);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packageNames) {
            tempControllerNames.addAll(this.scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        System.out.println("packageName = " + packageName);
        // 将以.分隔的包名换成以/分隔的uri
        String packagePath = packageName.replaceAll("\\.", "/");
        System.out.println("packagePath = " + packagePath);

        List<String> tempControllerNames = new ArrayList<>();
        URL url = this.getClass().getClassLoader().getResource(packagePath);
        System.out.println("url = " + url);

        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        System.out.println("path = " + path);
        if (!this.urlMappingNames.contains(path)) {
            System.out.println("nomatch ptah " + path);
            return;
        }
        Object obj = null;
        Object objResult = null;
        try {
            Method method = this.mappingMethods.get(path);
            System.out.println("method = " + method.toString());
            obj = this.mappingObjs.get(path);
            System.out.println("obj = " + obj.toString());
            objResult = method.invoke(obj);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        resp.getWriter().append(objResult.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
