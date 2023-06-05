package cc.rcbb.mini.spring.core.env;

/**
 * <p>
 * PropertyResolver
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/6/5
 */
public interface PropertyResolver {

    boolean containsProperty(String key);

    String getProperty(String key);

    String getProperty(String key, String defaultValue);

    <T> T getProperty(String key, Class<T> targetType);

    <T> T getProperty(String key, Class<T> targetType, T defaultValue);

    <T> Class<T> getPropertyAsClass(String key, Class<T> targetType);

    String getRequiredProperty(String key) throws IllegalStateException;

    <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;

    String resolverPlaceholders(String text);

    String resolverRequiredPlaceholders(String text) throws IllegalStateException;
}
