package cc.rcbb.mini.spring.beans.factory;

/**
 * <p>
 * PropertyValue
 * </p>
 *
 * @author lvhao
 * @date 2023/4/4
 */
public class PropertyValue {

    private final String type;
    private final String name;

    private final Object value;

    public PropertyValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
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
}
