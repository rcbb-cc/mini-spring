package cc.rcbb.mini.spring.beans.factory.config;

/**
 * <p>
 * ArgumentValue
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/17
 */
public class ConstructorArgumentValue {

    private String type;
    private String name;
    private Object value;

    public ConstructorArgumentValue(String type, Object value) {
        this.type = type;
        this.value = value;
    }

    public ConstructorArgumentValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
