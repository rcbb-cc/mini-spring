package cc.rcbb.mini.spring.web.servlet.view;

import cc.rcbb.mini.spring.web.servlet.View;
import cc.rcbb.mini.spring.web.servlet.ViewResolver;

/**
 * <p>
 * InternalResourceViewResolver
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/7
 */
public class InternalResourceViewResolver implements ViewResolver {

    private Class<?> viewClass = null;
    private String viewClassName = "";
    private String prefix = "";
    private String suffix = "";
    private String contentType;

    public InternalResourceViewResolver() {
        if (getViewClass() == null) {
            setViewClass(JstlView.class);
        }
    }

    public InternalResourceViewResolver(Class<?> viewClass, String prefix, String suffix) {
        this.viewClass = viewClass;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
        Class<?> clz = null;
        try {
            clz = Class.forName(viewClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setViewClass(clz);
    }

    @Override
    public View resolveViewName(String viewName) throws Exception {
        return buildView(viewName);
    }

    private View buildView(String viewName) throws Exception {
        Class<?> viewClass = getViewClass();
        View view = (View) viewClass.newInstance();
        view.setUrl(getPrefix() + viewName + getSuffix());
        view.setContentType(getContentType());
        return view;
    }

    protected String getViewClassName() {
        return this.viewClassName;
    }

    public void setViewClass(Class<?> viewClass) {
        this.viewClass = viewClass;
    }

    protected Class<?> getViewClass() {
        return this.viewClass;
    }

    public void setPrefix(String prefix) {
        this.prefix = (prefix != null ? prefix : "");
    }

    protected String getPrefix() {
        return this.prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = (suffix != null ? suffix : "");
    }

    protected String getSuffix() {
        return this.suffix;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected String getContentType() {
        return this.contentType;
    }
}
