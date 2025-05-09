package cc.rcbb.mini.spring.beans.factory;

/**
 * <p>
 * FactoryBean
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/24
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }

}
