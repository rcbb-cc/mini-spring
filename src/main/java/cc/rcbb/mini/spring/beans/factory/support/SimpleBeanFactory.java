package cc.rcbb.mini.spring.beans.factory.support;

import cc.rcbb.mini.spring.beans.*;
import cc.rcbb.mini.spring.beans.factory.BeanFactory;
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
 * SimpleBeanFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/13
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>(256);
    private List<String> beanDefinitionNames = new ArrayList<>();
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    public SimpleBeanFactory() {
    }

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanName, beanDefinition);
        this.beanDefinitionNames.add(beanName);
        if (!beanDefinition.isLazyInit()) {
            try {
                this.getBean(beanName);
            } catch (BeansException e) {
            }
        }
    }

    @Override
    public void removeBeanDefinition(String beanName) {
        this.beanDefinitions.remove(beanName);
        this.beanDefinitionNames.remove(beanName);
        this.removeBeanDefinition(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitions.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitions.containsKey(beanName);
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) {
                BeanDefinition beanDefinition = this.beanDefinitions.get(beanName);
                if (beanDefinition == null) {
                    throw new BeansException("No bean.");
                }
                singleton = createBean(beanDefinition);
                this.registerSingleton(beanName, singleton);

                // beanPostProcessor
                // step 1 : postProcessBeforeInitialization

                // step 2 : afterPropertiesSet

                // step 3 : init-method
                if (beanDefinition.getInitMethodName() != null) {
                    // 初始化方法调用
                }
                // step 4 : postProcessAfterInitialization。

            }
        }
        return singleton;
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object object = doCreateBean(beanDefinition);

        this.earlySingletonObjects.put(beanDefinition.getId(), object);

        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.handleProperties(beanDefinition, clz, object);
        return object;
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object object) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];

                if (!propertyValue.isRef()) {
                    if ("String".equals(propertyValue.getType()) || "java.lang.String".equals(propertyValue.getType())) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(propertyValue.getType()) || "java.lang.Integer".equals(propertyValue.getType())) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(propertyValue.getType())) {
                        paramTypes[0] = int.class;
                    } else {
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = propertyValue.getValue();
                } else {
                    try {
                        paramTypes[0] = Class.forName(propertyValue.getType());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        paramValues[0] = getBean((String) propertyValue.getValue());
                    } catch (BeansException e) {
                        throw new RuntimeException(e);
                    }
                }

                String propertyName = propertyValue.getName();
                String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(object, paramValues);
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
        Constructor<?> constructor = null;
        Object object = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());

            ConstructorArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            if (!argumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
                Object[] paramValues = new Object[argumentValues.getArgumentCount()];
                for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);

                    if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    } else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf(argumentValue.getValue().toString());
                    } else if ("int".equals(argumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf(argumentValue.getValue().toString()).intValue();
                    } else {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }
                }

                try {
                    constructor = clz.getConstructor(paramTypes);
                    object = constructor.newInstance(paramValues);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } else {
                object = clz.newInstance();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public boolean containsBean(String name) {
        return this.containsSingleton(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitions.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitions.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitions.get(name).getClass();
    }

}
