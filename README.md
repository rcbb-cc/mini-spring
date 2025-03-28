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

# 02｜扩展Bean：如何配置constructor、property和init-method？

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

# 03｜依赖注入：如何给Bean注入值并解决循环依赖问题？

## ioc-04 优化

- BeanDefinition 中 dependsOn 完善。
- 解决循环依赖。SimpleBeanFactory 中增加 earlySingletonObjects。
- PropertyValue 中增加 isRef(是否是引用) 字段。

总结：   
当前这里解决循环依赖问题，使用了两个缓存。Spring 源码实现中还有一个 bean 实例工厂缓存。    
Spring 三级缓存机制：   
- singletonObjects：用于存储完全创建好的单例 bean 实例。
- earlySingletonObjects：用于存储早期创建但未完成初始化的单例 bean 实例。
- singletonFactories：用于存储单例 bean 的工厂。

当 Spring 发现两个或更多个 bean 之间存在循环依赖关系时，它会将其中一个 bean 创建的过程中尚未完成的实例放入 earlySingletonObjects 缓存中，然后将创建该 bean 的工厂对象放入 singletonFactories 缓存中。
接着，Spring 会暂停当前 bean 的创建过程，去创建它所依赖的 bean。
当依赖的 bean 创建完成后，Spring 会将其放入 singletonObjects 缓存中，并使用它来完成当前 bean 的创建过程。
在创建当前bean的过程中，如果发现它还依赖其他的 bean，Spring 会重复上述过程，直到所有 bean 的创建过程都完成为止。

需要注意的是，当使用构造函数注入方式时，循环依赖是无法解决的。
因为在创建 bean 时，必须先创建它所依赖的 bean 实例，而构造函数注入方式需要在创建 bean 实例时就将依赖的 bean 实例传入构造函数中。
如果依赖的 bean 实例尚未创建完成，就无法将其传入构造函数中，从而导致循环依赖无法解决。
此时，可以考虑使用 setter 注入方式来解决循环依赖问题。

Spring 对于循环依赖的支持，反而导致了程序员写出了坏味道代码而不自知。
所以 Spring 官方也建议大家使用构造器注入，一个是避免写出这种层级依赖不清晰的糟糕代码，二是也方便了后续单元测试的编写。
从 Spring 6 开始，默认情况下，Spring 不再支持构造器注入场景下的循环依赖，同时也不再鼓励使用 setter 或字段注入来解决循环依赖。

# 04｜增强IoC容器：如何让我们的Spring支持注解？

## ioc-05 优化

- 项目代码结构。beans 目录下新增 factory 目录，factory 目录中新增 xml、support、config 和 annotation。
- ArgumentValue、ArgumentValues 修改为 ConstructorArgumentValue、ConstructorArgumentValues。
- Autowired：注解，修饰成员变量，并且在运行时生效。
- BeanPostProcess：Bean 处理器。
- AutowiredAnnotationBeanPostProcessor：Autowired 注解处理器，用于处理 @Autowired 注解，完成自动注入。
- AbstractBeanFactory：抽象 BeanFactory，继承 DefaultSingletonBeanRegistry，实现 BeanFactory、BeanDefinitionRegistry 接口，提供 BeanFactory 的通用功能。完善了 对 Bean 初始化前、初始化和初始化后的处理。
- AutowireCapableBeanFactory：继承 AbstractBeanFactory，核心在于 postProcessBeforeInitialization、postProcessAfterInitialization 的实现。

总结：  
解耦分为两种：设计上的解耦、实现类上的解耦。    
通过抽取 AbstractBeanFactory，把 BeanPostProcessor 的设计与 BeanFactory 本身解耦。
AutowireCapableBeanFactory 再通过定义 BeanPostProcessor 接口类型的属性，向外提供属性设置的方法，做到了和 BeanPostProcessor 实现类的解耦。
最后在 ClassPathXmlApplicationContext 中统一注册 BeanPostProcessor，再抽取成一个启动方法，非常优雅。

![增强IoC容器](https://rcbb-blog.oss-cn-guangzhou.aliyuncs.com/2025/03/20250320163939-63270a.png?x-oss-process=style/yuantu_shuiyin)

# 05｜实现完整的IoC容器：构建工厂体系并添加容器事件

## ioc-06 优化

- ListableBeanFactory：接口，继承 BeanFactory，BeanDefinition 的一些操作。
- ConfigurableBeanFactory：接口，继承 BeanFactory，BeanPostProcessor 的一些操作。
- ConfigurableListableBeanFactory：接口，把AutowireCapableBeanFactory、ListableBeanFactory 和 ConfigurableBeanFactory 合并在一起。
- 在 core 目录下新建 env 目录。
- PropertyResolver：接口，用于获取属性值。
- Environment：接口，继承 PropertyResolver，用于获取环境变量。
- EnvironmentCapable：接口，主要用于获取 Environment 实例。
- DefaultListableBeanFactory：继承 AbstractAutowireCapableBeanFactory，实现 ConfigurableListableBeanFactory 接口，成为了 IoC 引擎。
- 完善事件的发布与监听：ApplicationEvent、ApplicationListener、ApplicationEventPublisher 以及 ContextRefreshEvent。

DefaultListableBeanFactory 的继承体系图：
![DefaultListableBeanFactory](https://rcbb-blog.oss-cn-guangzhou.aliyuncs.com/2025/03/0827FB38-09E5-4925-8560-6558FB5357CB-67d548.png?x-oss-process=style/yuantu_shuiyin)


总结：     
接口：     
- BeanFactory：Bean 工厂。
- SingletonBeanRegistry：单例 Bean 仓库。
> DefaultSingletonBeanRegistry：默认的单例 Bean 仓库实现。提供了注册列表、单例容器、依赖注入管理信息(两个Map，依赖和被依赖)。
- BeanDefinitionRegistry：强调对 BeanDefinition 进行操作。
- ListableBeanFactory：对 BeanDefinition 的 List 进行操作。
- ConfigurableBeanFactory：Bean 处理器(只有 add 和 get，没有 apply)，以及管理依赖信息。
- AutowireCapableBeanFactory：提供自动装配选项，并在初始化前后应用(apply) Bean 处理器。
- ConfigurableListableBeanFactory：集成接口。

抽象类：
- AbstractBeanFactory：主要是 refresh、invokeInitMethod、createBean、构造器注入和属性注入。
- AbstractAutowireCapableBeanFactory：提供成员变量 List<BeanPostProcessor> 可通过该成员进行更多的 bean 出来操作，add、get、apply 具体实现。

![实现完整的IoC容器](https://rcbb-blog.oss-cn-guangzhou.aliyuncs.com/2025/03/20250325113451-cbd90f.png?x-oss-process=style/yuantu_shuiyin)

# 07｜原始MVC：如何通过单一的Servlet拦截请求分派任务？

## mvc-01

- 新增一个与 src 目录同级的 WebContent 目录，用于存放静态资源和 xml 配置文件。
- 新增一个与 core 包同级的 web 包。
- minisMVC-servlet.xml：用于配置 url 对应处理的类和方法。
- web.xml：用于配置 servlet，还有 minisMVC-servlet.xml 的路径配置。
- DispatcherServlet：mvc 核心启动类，完成 url 映射机制。
- @RequestMapping：注解，修饰方法时，表示该方法被映射到该 url。

![原始MVC](https://rcbb-blog.oss-cn-guangzhou.aliyuncs.com/2025/03/20250326153522-21b93a.png?x-oss-process=style/yuantu_shuiyin)

关于启动，解决方式如下：   
1. IDEA 配置 Tomcat 运行项目。
- [IntelliJ IDEA中配置Tomcat](https://blog.csdn.net/Wxy971122/article/details/123508532)
- [IntelliJ IDEA部署web项目到Tomcat](https://blog.csdn.net/fannyoona/article/details/113933113)

2. 可加入 embeded tomcat 依赖，然后使用下面代码运行。
```
<!-- 嵌入式 Tomcat -->
<dependency>
    <groupId>org.apache.tomcat</groupId>
    <artifactId>tomcat-catalina</artifactId>
    <version>9.0.73</version>
</dependency>
```
```java
public class App {
    public static void main(String[] args) throws LifecycleException {
        System.out.println("Hello World!");
        Tomcat tomcat = new Tomcat();
        String webappDirLocation = "WebContent";
        StandardContext context = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);
        tomcat.start();
        tomcat.getServer().await();
    }
}
```

# 08｜整合IoC和MVC：如何在Web环境中启动IoC容器？

## mvc-02

- MVC 的实现需要符合 Web 规范。
- web.xml 文件中定义的元素加载过程：先获取全局的参数 context-param 来创建上下文，之后如果配置文件里定义了 Listener，那服务器会先启动它们，之后是 Filter，最后是 Servlet。因此可以利用这个时序，把容器的启动放到 Web 应用的 Listener 中。
- ContextLoaderListener：监听器，用于启动 IoC 容器。
- WebApplicationContext：上下文接口，应用在 Web 项目中。
- AnnotationConfigWebApplicationContext：简称 WAC，配置类 Web 上下文，用于加载配置类。

总结：    
- 当 Servlet 服务器启动时，Listener 会优先启动，读取配置文件路径，启动过程中初始化上下文，然后启动 IoC 容器，这个容器会通过 refresh() 方法加载所管理的 Bean 对象。这样就实现了 Tomcat 启动的时候同时启动 IoC 容器。
- Servlet 规范中规定的时序，从 listener 到 filter 再到 servlet，每一个环节都预留了接口让我们有机会干预，写入我们需要的代码。

