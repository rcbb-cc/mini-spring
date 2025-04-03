package cc.rcbb.mini.spring.web;

import cc.rcbb.mini.spring.beans.AbstractPropertyAccessor;
import cc.rcbb.mini.spring.beans.PropertyEditor;
import cc.rcbb.mini.spring.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>
 * BeanWrapperImpl
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/2
 */
public class BeanWrapperImpl extends AbstractPropertyAccessor {

    Object wrappedObject;

    Class<?> clz;

    public BeanWrapperImpl(Object object) {
        super();
        this.wrappedObject = object;
        this.clz = object.getClass();
    }

    @Override
    public void setPropertyValue(PropertyValue propertyValue) {
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(propertyValue.getName());
        PropertyEditor propertyEditor = this.getCustomEditor(propertyHandler.getPropertyClz());
        if (propertyEditor == null) {
            propertyEditor = this.getDefaultEditor(propertyHandler.getPropertyClz());
        }
        if (propertyEditor != null) {
            propertyEditor.setAsText((String) propertyValue.getValue());
            propertyHandler.setValue(propertyEditor.getValue());
        } else {
            propertyHandler.setValue(propertyValue.getValue());
        }
    }

    class BeanPropertyHandler {
        Method writeMethod = null;
        Method readMethod = null;
        Class<?> propertyClz = null;

        public BeanPropertyHandler(String propertyName) {
            try {
                Field field = clz.getDeclaredField(propertyName);
                propertyClz = field.getType();
                this.writeMethod = clz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
                this.readMethod = clz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        public Object getValue() {
            Object result = null;
            writeMethod.setAccessible(true);

            try {
                result = readMethod.invoke(wrappedObject);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            return result;
        }

        public void setValue(Object value) {
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrappedObject, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public Class<?> getPropertyClz() {
            return propertyClz;
        }
    }
}
