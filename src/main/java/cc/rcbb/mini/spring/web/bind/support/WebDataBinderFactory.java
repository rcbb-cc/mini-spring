package cc.rcbb.mini.spring.web.bind.support;

import cc.rcbb.mini.spring.web.bind.WebDataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * WebDataBinderFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/2
 */
public class WebDataBinderFactory {

    public WebDataBinder createBinder(HttpServletRequest request, Object target, String objectName) {
        WebDataBinder dataBinder = new WebDataBinder(target, objectName);
        initBinder(dataBinder, request);
        return dataBinder;
    }

    protected void initBinder(WebDataBinder dataBinder, HttpServletRequest request) {

    }

}
