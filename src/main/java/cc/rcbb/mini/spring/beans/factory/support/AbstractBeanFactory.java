package cc.rcbb.mini.spring.beans.factory.support;

import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.BeanFactory;
import cc.rcbb.mini.spring.beans.factory.PropertyValue;
import cc.rcbb.mini.spring.beans.factory.PropertyValues;
import cc.rcbb.mini.spring.beans.factory.config.BeanDefinition;
import cc.rcbb.mini.spring.beans.factory.config.ConstructorArgumentValue;
import cc.rcbb.mini.spring.beans.factory.config.ConstructorArgumentValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * AbstractBeanFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/11
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNameList = new ArrayList<>();
    private final Map<String, Object> earlySingletonObjectMap = new HashMap<>(16);

    public AbstractBeanFactory() {
    }

    @Override
    public void refresh() {
        for (String beanName : beanDefinitionNameList) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);

        if (singleton == null) {
            singleton = this.earlySingletonObjectMap.get(beanName);

            if (singleton == null) {
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                singleton = createBean(beanDefinition);

                this.registerBean(beanName, singleton);

                // 进行beanPostProcessor处理
                applyBeanPostProcessorsBeforeInitialization(singleton, beanName);
                if (beanDefinition.getInitMethodName() != null && !"".equals(beanDefinition.getInitMethodName())) {
                    this.invokeInitMethod(beanDefinition, singleton);
                }
                applyBeanPostProcessorsAfterInitialization(singleton, beanName);
            }
        }
        if (singleton == null) {
            throw new BeansException("bean is null.");
        }
        return singleton;
    }

    private void invokeInitMethod(BeanDefinition beanDefinition, Object object) {
        Class<?> clz = object.getClass();
        Method method = null;
        try {
            method = clz.getMethod(beanDefinition.getInitMethodName());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            method.invoke(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    abstract public Object applyBeanPostProcessorsBeforeInitialization(Object singleton, String beanName) throws BeansException;

    abstract public Object applyBeanPostProcessorsAfterInitialization(Object singleton, String beanName) throws BeansException;

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = doCreateBean(beanDefinition);
        this.earlySingletonObjectMap.put(beanDefinition.getId(), obj);

        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        populateBean(beanDefinition, clz, obj);

        return obj;
    }

    private void populateBean(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        handleProperties(beanDefinition, clz, obj);
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        // 处理属性
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String name = propertyValue.getName();
                String type = propertyValue.getType();
                Object value = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();

                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];

                if (!isRef) {
                    if ("String".equals(type) ||
                            "java.lang.String".equals(type)) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(type) ||
                            "java.lang.Integer".equals(type)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(type)) {
                        paramTypes[0] = int.class;
                    } else {
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = value;
                } else {
                    try {
                        paramTypes[0] = Class.forName(type);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        paramValues[0] = getBean((String) value);
                    } catch (BeansException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 按照setXxx 的规范查找set方法
                String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                try {
                    // 调用set方法设置属性值
                    method.invoke(obj, paramValues);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // 处理构造器参数
        ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
        try {
            if (!constructorArgumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
                Object[] paramValues = new Object[constructorArgumentValues.getArgumentCount()];
                for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue constructorArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                    // 对每一个参数，分数据类型进行处理
                    if ("String".equals(constructorArgumentValue.getType()) ||
                            "java.lang.String".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    } else if ("Integer".equals(constructorArgumentValue.getType()) ||
                            "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                    } else if ("int".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue()).intValue();
                    } else {
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    }
                }
                // 按照特定构造器创建实例
                con = clz.getConstructor(paramTypes);
                obj = con.newInstance(paramValues);
            } else {
                obj = clz.newInstance();
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(beanDefinition.getId() + " bean created. " + beanDefinition.getClassName() + " : " + obj.toString());
        return obj;
    }

    @Override
    public boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNameList.add(name);
        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNameList.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

}
