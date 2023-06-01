package cc.rcbb.mini.spring.beans.factory.support;

import cc.rcbb.mini.spring.beans.factory.config.BeanDefinition;
import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.*;
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
 * @date 2023/4/3
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private List<String> beanDefinitionNameList = new ArrayList<>();
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private final Map<String, Object> earlySingletonObjectMap = new HashMap<>(10);

    public SimpleBeanFactory() {
    }

    @Override
    public void refresh() {
        for (String beanName : beanDefinitionNameList) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿Bean实例
        Object singleton = this.getSingleton(beanName);
        // 如果为空，则获取它的定义来创建实例
        if (singleton == null) {
            singleton = this.earlySingletonObjectMap.get(beanName);
            if (singleton == null) {
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition == null) {
                    throw new BeansException("No bean.");
                }
                singleton = createBean(beanDefinition);
                this.registerSingleton(beanName, singleton);
            }
        }
        if (singleton == null) {
            throw new BeansException("bean is null.");
        }
        return singleton;
    }

    @Override
    public boolean containsBean(String name) {
        return this.containsSingleton(name);
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
                throw new RuntimeException(e);
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

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        // 创建毛坯bean实例
        Object obj = doCreateBean(beanDefinition);

        this.earlySingletonObjectMap.put(beanDefinition.getId(), obj);

        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // 补齐property
        this.handleProperties(beanDefinition, clz, obj);

        return obj;
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        System.out.println("handle properties for bean : " + beanDefinition.getId());
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

    /**
     * 创建毛坯实例，仅仅调用构造方法，没有进行属性处理
     *
     * @param beanDefinition
     * @return
     */
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

}
