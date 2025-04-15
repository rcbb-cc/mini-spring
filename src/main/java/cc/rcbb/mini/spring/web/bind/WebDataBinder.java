package cc.rcbb.mini.spring.web.bind;

import cc.rcbb.mini.spring.beans.AbstractPropertyAccessor;
import cc.rcbb.mini.spring.beans.PropertyEditor;
import cc.rcbb.mini.spring.beans.PropertyValues;
import cc.rcbb.mini.spring.util.WebUtils;
import cc.rcbb.mini.spring.web.BeanWrapperImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * WebDataBinder
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/2
 */
public class WebDataBinder {

    private Object target;

    private Class<?> clz;

    private String objectName;

    AbstractPropertyAccessor propertyAccessor;


    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String targetName) {
        this.target = target;
        this.objectName = targetName;
        this.clz = this.target.getClass();
        this.propertyAccessor = new BeanWrapperImpl(this.target);
    }

    public void bind(HttpServletRequest request) {
        PropertyValues propertyValues = assignParameters(request);
        addBindValues(propertyValues, request);
        doBind(propertyValues);
    }

    private void doBind(PropertyValues propertyValues) {
        applyPropertyValues(propertyValues);
    }

    private void applyPropertyValues(PropertyValues propertyValues) {
        getPropertyAccessor().setPropertyValues(propertyValues);
    }

    private AbstractPropertyAccessor getPropertyAccessor() {
        return this.propertyAccessor;
    }

    private void addBindValues(PropertyValues propertyValues, HttpServletRequest request) {

    }

    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(map);
    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }

}
