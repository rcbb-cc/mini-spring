# 手把手带你写一个 MiniSpring

![手把手带你写一个](https://rcbb-blog.oss-cn-guangzhou.aliyuncs.com/2023/04/20230403190429-0120f4.png?x-oss-process=style/yuantu_shuiyin)


# 01.原始IoC：如何通过BeanFactory实现原始版本的IoC容器？

## ioc-01 初步定义

- BeanDefinition：Bean的定义。
- ClassPathXmlApplicationContext：读取 XML 配置文件，解析配置文件，创建 BeanDefinition，并初始化实例放入容器中。

## ioc-02 优化

- 项目代码结构。beans、context、core。
- BeansException：Beans异常。
- BeanFactory：Bean工厂。
- Resource：外部的配置信息都当成 Resource(资源)来进行抽象。
- ClassPathXmlResource：读取 XML 文件配置。
- XmlBeanDefinitionReader：将解析的 XML 文件信息，封装到 BeanDefinition 中并放入 BeanFactory 中。
- SimpleBeanFactory：简单的 BeanFactory 的实现类。
- ClassPathXmlApplicationContext：实现了
  1. 读取 XML 配置文件。
  2. 解析配置文件，创建 BeanDefinition。
  3. 读取 BeanDefinition，实例化 Bean 并注入到 BeanFactory 容器中。

总结：
1. ClassPathXmlResource 读取到 XML 文件信息，成为了 Resource。资源的来源不同、资源的格式不同，抽象的 Resource 的接口，它的不同子类从不同的来源读取，但是最终都是以 Resource 接口的形式提供给外部访问的。
2. XmlBeanDefinitionReader 将读取出来的 XML 信息，转换成 BeanDefinition，并且放入 BeanFactory 中。但是 Resource 接口中被迭代的 Object 又是根据不同格式不同而不同的，Element 只是 XML 格式的，所以又定义了 BeanDefinitionReader 接口，它的不同子类可以读取不同格式的资源来形成 BeanDefinition 。
3. BeanFactory 定义了两个核心，接收 BeanDefinition 和 获取 Bean。
4. SimpleBeanFactory 实现了 BeanFactory 的两个核心，接收 BeanDefinition 和 获取 Bean。
5. ClassPathXmlApplicationContext 根据 XML 文件进行组装，所以使用 ClassPathXmlResource、XmlBeanDefinitionReader。

![原始IoC](https://rcbb-blog.oss-cn-guangzhou.aliyuncs.com/2025/03/20250313103353-cd43e4.png?x-oss-process=style/yuantu_shuiyin)

## ioc-03 优化

- 扩展 BeanDefinition，增加属性。
  * scope：单例模式、原型模式。
  * lazyInit：是否要在加载的时候初始化。
  * dependsOn：记录 Bean 之间的依赖关系。
  * initMethodName：初始化方法，当一个 Bean 构造好实例化后是否要调用初始化方法。
  * constructorArgumentValues：构造器参数。
  * propertyValues：参数列表。
- BeanDefinitionRegistry：存放 BeanDefinition 的仓库。
- SingletonBeanRegistry：单例 Bean 的仓库。
- DefaultSingletonBeanRegistry：单例 Bean 的仓库默认实现类。
- SimpleBeanFactory 继承 DefaultSingletonBeanRegistry 实现 BeanFactory，确保创建 Bean 默认就是单例的。
- ArgumentValue、ArgumentValues：构造参数、构造参数集合。
- PropertyValue、PropertyValues：参数、参数集合。
- ApplicationEvent、ApplicationEventPublisher：事件、事件发布器。

总结：
1. SimpleBeanFactory 继承 DefaultSingletonBeanRegistry 实现 BeanFactory。
> 接口和继承类的理解，如果一个类声明它实现了某个接口，那么它偏向于告诉外部它是那个接口，你可以把它当成那个接口来用。
> 如果一个类继承了某个实现类，这时候也可以把它当成这个实现类来用，但是我想它更偏向于获得该实现类的能力。
> 如果它既想获得能力又想对外提供能力，那么它可以同时声明实现接口和继承接口的某些实现类，再自己修改增强某些方法。

![扩展Bean](https://rcbb-blog.oss-cn-guangzhou.aliyuncs.com/2025/03/20250317143221-3f19e2.png?x-oss-process=style/yuantu_shuiyin)
