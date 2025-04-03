package cc.rcbb.mini.spring.web.servlet;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.web.RequestMapping;
import cc.rcbb.mini.spring.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * <p>
 * RequestMappingHandlerMapping
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/31
 */
public class RequestMappingHandlerMapping implements HandlerMapping {

    WebApplicationContext wac;

    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac) {
        this.wac = wac;
        this.initMapping();
    }

    private void initMapping() {
        Object obj = null;
        Class<?> clz = null;
        String[] controllerNames = this.wac.getBeanDefinitionNames();
        for (String controllerName : controllerNames) {
            try {
                clz = Class.forName(controllerName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                obj = this.wac.getBean(controllerName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
            Method[] methods = clz.getDeclaredMethods();
            if (methods != null) {
                for (Method method : methods) {
                    boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                    if (isRequestMapping) {
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                        this.mappingRegistry.getMappingObjs().put(urlMapping, obj);
                        this.mappingRegistry.getMappingMethods().put(urlMapping, method);
                        this.mappingRegistry.getMappingMethodNames().put(urlMapping, method.getName());
                        this.mappingRegistry.getMappingClasses().put(urlMapping, clz);
                    }
                }
            }
        }
    }

    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        String path = request.getServletPath();
        if (!this.mappingRegistry.getUrlMappingNames().contains(path)) {
            return null;
        }
        Method method = this.mappingRegistry.getMappingMethods().get(path);
        Object obj = this.mappingRegistry.getMappingObjs().get(path);
        Class<?> clz = this.mappingRegistry.getMappingClasses().get(path);
        String methodName = this.mappingRegistry.getMappingMethodNames().get(path);
        return new HandlerMethod(method, obj, clz, methodName);
    }
}
