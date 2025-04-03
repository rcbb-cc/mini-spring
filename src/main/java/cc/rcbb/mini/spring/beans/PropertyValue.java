package cc.rcbb.mini.spring.beans;

/**
 * <p>
 * PropertyValue
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/17
 */
public class PropertyValue {
    private String type;
    private String name;
    private Object value;
    private boolean isRef;

    public PropertyValue(String name, Object value) {
        this("", name, value, false);
    }

    public PropertyValue(String type, String name, Object value, boolean isRef) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.isRef = isRef;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public boolean isRef() {
        return isRef;
    }
}
