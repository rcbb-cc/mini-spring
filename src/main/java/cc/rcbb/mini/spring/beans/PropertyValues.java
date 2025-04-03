package cc.rcbb.mini.spring.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * PropertyValues
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/17
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        this.propertyValueList = new ArrayList<>(10);
    }

    public PropertyValues(Map<String, Object> map) {
        this.propertyValueList = new ArrayList<>(10);
        for (Map.Entry<String, Object> e : map.entrySet()) {
            PropertyValue propertyValue = new PropertyValue(e.getKey(), e.getValue());
            this.propertyValueList.add(propertyValue);
        }
    }

    public List<PropertyValue> getPropertyValueList() {
        return this.propertyValueList;
    }

    public int size() {
        return this.propertyValueList.size();
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    public void addPropertyValue(String propertyType, String propertyName, Object propertyValue, boolean isRef) {
        addPropertyValue(new PropertyValue(propertyType, propertyName, propertyValue, isRef));
    }

    public void removePropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.remove(propertyValue);
    }

    public void removePropertyValue(String propertyName) {
        this.propertyValueList.remove(getPropertyValue(propertyName));
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[this.propertyValueList.size()]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : this.propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }

    public Object get(String propertyName) {
        PropertyValue propertyValue = getPropertyValue(propertyName);
        return (propertyValue != null ? propertyValue.getValue() : null);
    }

    public boolean contains(String propertyName) {
        return (getPropertyValue(propertyName) != null);
    }

    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }
}
