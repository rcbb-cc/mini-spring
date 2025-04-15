package cc.rcbb.mini.spring.web.servlet;

/**
 * <p>
 * ViewResolver
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/7
 */
public interface ViewResolver {

    View resolveViewName(String viewName) throws Exception;

}
