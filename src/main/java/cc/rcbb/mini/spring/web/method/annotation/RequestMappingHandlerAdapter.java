package cc.rcbb.mini.spring.web.method.annotation;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.context.ApplicationContext;
import cc.rcbb.mini.spring.context.ApplicationContextAware;
import cc.rcbb.mini.spring.http.converter.HttpMessageConverter;
import cc.rcbb.mini.spring.web.bind.WebDataBinder;
import cc.rcbb.mini.spring.web.bind.annotation.ResponseBody;
import cc.rcbb.mini.spring.web.bind.support.WebDataBinderFactory;
import cc.rcbb.mini.spring.web.method.HandlerMethod;
import cc.rcbb.mini.spring.web.servlet.HandlerAdapter;
import cc.rcbb.mini.spring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <p>
 * RequestMappingHandlerAdapter
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/31
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter, ApplicationContextAware {

    private ApplicationContext applicationContext = null;
    private HttpMessageConverter messageConverter = null;


    public RequestMappingHandlerAdapter() {
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return this.handleInternal(request, response, (HandlerMethod) handler);
    }

    private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        try {
            return invokeHandlerMethod(request, response, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];
        int i = 0;
        for (Parameter methodParameter : methodParameters) {
            Object methodParamObj = methodParameter.getType().newInstance();
            WebDataBinder webDataBinder = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
            webDataBinder.bind(request);
            methodParamObjs[i] = methodParamObj;
            i++;
        }
        Method invocableMethod = handlerMethod.getMethod();
        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
        Class<?> returnType = invocableMethod.getReturnType();

        ModelAndView modelAndView = null;
        if (invocableMethod.isAnnotationPresent(ResponseBody.class)) {
            this.messageConverter.write(returnObj, response);
        } else if (returnType == void.class) {

        } else {
            if (returnObj instanceof ModelAndView) {
                modelAndView = (ModelAndView) returnObj;
            } else if (returnObj instanceof String) {
                String target = (String) returnObj;
                modelAndView = new ModelAndView(target);
            }
        }
        return modelAndView;
    }

    public HttpMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
