package cc.rcbb.mini.spring.beans;

/**
 * <p>
 * BeanDefinition
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public class BeanDefinition {

    private String id;
    private String className;

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
