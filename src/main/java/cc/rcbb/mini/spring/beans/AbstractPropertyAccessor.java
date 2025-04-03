package cc.rcbb.mini.spring.beans;

/**
 * <p>
 * AbstractPropertyAccessor
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/2
 */
public abstract class AbstractPropertyAccessor extends PropertyEditorRegistrySupport {

    PropertyValues propertyValues;

    public AbstractPropertyAccessor() {
        super();
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
        for (PropertyValue propertyValue: this.propertyValues.getPropertyValues()) {
            setPropertyValue(propertyValue);
        }
    }

    public abstract void setPropertyValue(PropertyValue propertyValue);

}
