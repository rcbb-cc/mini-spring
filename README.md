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

# 09｜分解Dispatcher：如何把专门的事情交给专门的部件去做？

## mvc-03

- 在 web 包下增加 servlet 包。
- 按照 Web 分层体系，结构上会有 Controller、Service两层。Controller 由 DispatcherServlet 负责启动，Service 由 Listener 负责启动。
- AnnotationConfigWebApplicationContext：配置类 Web 上下文，用于加载配置类。
- XmlWebApplicationContext：XML 配置 Web 上下文，用于加载 XML 配置文件。
- HandlerMethod：处理器方法，用于封装处理器方法。
- HandlerMapping：接口，映射器，用于根据 url 找到对应的处理器。
- HandlerAdapter：接口，适配器，用于根据处理器的类型，调用对应的处理器方法。
- MappingRegistry：映射注册表，用于记录处理器和 url 的映射关系。
- RequestMappingHandlerMapping：实现 HandlerMapping 接口，映射器，用于根据 url 找到对应的处理器，并记录映射关系。
- RequestMappingHandlerAdapter：实现 HandlerAdapter 接口，适配器，用于根据处理器的类型，调用对应的处理器方法。

总结：    
- Listener 初始化的时候将交给 Ioc 管理 Bean 初始化。
- Servlet 初始化的时候将 controller 相关的 Bean 初始化。
> 为什么 Spring 要搞出两个容器来呢？  
> 这样分开更清晰，Dispatcher 驱动的子容器专门用来处理 controller 组件，ContextLoaderListener 驱动的父容器专门用来处理业务逻辑组件以及持久化组件。  
> Spring 体系中 IoC 是核心层，MVC 只是外周的部分，理论上是可以不启用的可选部件。  


遗留的疑问：    
1. DispatcherServlet 中的 controller 相关 bean 的初始化已经交给 AnnotationConfigWebApplicationContext 管理了，它的 init 方法不用在调用 initController 了。
2. 如果在 HelloWorldBean 中以 @Autowired 注解注入 TestUserService，是无法注入成功的？那么 Spring 是怎么做的呢，自己当前的 factory 找不到，去父类的 factory 找？

# 10｜数据绑定: 如何自动转换传入的参数？

## mvc-04

- WebDataBinder：数据绑定器，用于自动转换传入的参数。
- PropertyEditor：接口，属性编辑器，用于转换属性。
- CustomNumberEditor：属性编辑器，用于转换数字类型。
- StringEditor：属性编辑器，用于转换字符串类型。
- PropertyEditorRegistrySupport：属性编辑器注册器，用于注册属性编辑器。
- BeanWrapperImpl：继承 PropertyEditorRegistrySupport，利用反射对 Bean 属性值进行读写。
- WebDataBinderFactory：接口，数据绑定工厂，用于创建数据绑定器。
- WebDataBinder：数据绑定器，用于自动转换传入的参数。
- CustomDateEditor：实现了 PropertyEditor 接口，自定义的日期格式处理器，来进行测试。
- WebBindingInitializer：接口，数据绑定初始化器，用于初始化数据绑定器。
- DateInitializer：实现了 WebBindingInitializer 接口，用于初始化 CustomDateEditor 处理器。

总结：    
目前的实现无法完成对基本类型的转换，处理的是复合类型的转换。     
举例：定义了 TestReq，里面有 key、value 属性值，在 /test2?key=1&value=2 请求中将参数值封装到 TestReq 中。

1. RequestMappingHandlerAdapter 中对参数进行处理，对于每个参数都有一个 WebDataBinder 进行处理。  
2. WebDataBinder 进行类型绑定的时候，主要是通过 BeanWrapperImpl 类来进行处理。  
3. 此时，每个 WebDataBinder 和 BeanWrapperImpl 内的 clazz 指向的都是这个参数的类。
4. 在 BeanWrapperImpl 的 setPropertyValue 方法中，主要是借助于由请求转换而来的 PropertyValue 类。  
5. PropertyValue 中主要由 name 和 value，对应的是请求中的请求名和参数，并调用 BeanPropertyHandler 以 PropertyValue 的 name 进行处理。   
6. BeanPropertyHandler 首先根据请求名找到这个请求参数的类里面对应名称的 Field，在根据 Field 获取对应的 Clazz。  
7. 然后使用 Editor 的 getValue 来进行类型转换，使用 set 方法进行赋值，然后使用对应属性的 get 方法进行取值操作。  

# 11｜ModelAndView ：如何将处理结果返回给前端？

## mvc-05

- 增加 http 包。web 包下新增 bind、context、method 包。
- @ResponseBody：响应体注解，用于将处理结果按照某种字符串格式返回给前端。
- HttpMessageConverter：接口，消息转换器，用于将返回给前端的字符流数据可以进行格式转换。
- DefaultHttpMessageConverter：实现了 HttpMessageConverter 接口，默认消息转换器，把 Object 转成 JSON 串。
- ObjectMapper：接口，对象映射器，用于将 Java 对象转成字符串。
- DefaultObjectMapper：实现了 ObjectMapper 接口，默认对象映射器，用于将 Java 对象映射成 JSON 串。
- ModelAndView：模型视图，用于封装处理结果，返回给前端。
- View：接口，视图，把数据按照一定格式显示并输出到前端界面上。
- ViewResolver：接口，视图解析器，用于根据视图名称找到对应的视图。
- InternalResourceViewResolver：实现 ViewResolver 接口，视图解析器，作为启动 JSP 的默认实现。
- JstlView：实现 View 接口，视图，用于将数据按照一定格式显示并输出到前端界面上。

总结：   
虽然已经不流行 JSP，但是部件的解耦的框架思想，值得学习。    
1. 如何自动转换数据？MessageConverter
2. 如何找到指定的 View？ViewResolver
3. 如何去渲染页面？View

遗留的疑问：   
1. handlerAdapter 配置在 applicationContext.xml 中，在 DispatchServlet 中使用 `this.webApplicationContext` 应该是获取不到 handlerAdapter 的。
> applicationContext.xml 中配置的 bean 不是应该在 ContextLoaderListener 中扫描加载，是 DispatchServlet 的 parentApplicationContext 吗？

# 13｜JDBC访问框架：如何抽取JDBC模板并隔离数据库？

## jdbc-01

- 加载驱动，获取数据库连接，创建 Statement，执行 SQL 语句，获取结果集，关闭资源。
- 抽取 JdbcTemplate。动静分离，将固定的套路作为模板定下来，变化的部分让子类重写。
- JdbcTemplate：模板类，用于执行 JDBC 操作，对 JDBC 操作进行封装，简化 JDBC 操作。
- StatementCallback：接口，Statement 回调，用于执行 Statement 的回调方法。
- PreparedStatementCallback：接口，PreparedStatement 回调，用于执行 PreparedStatement 的回调方法。
- SingleConnectionDataSource：实现 DataSource 接口，单连接数据源，用于获取单连接的数据源。

# 14｜增强模板：如何抽取专门的部件完成专门的任务？

## jdbc-02

> SQL 语句参数的传入还是一个个写进去的，没有抽取出一个独立的部件进行统一处理。
> 返回的记录是单行的，不支持多行的数据集。
> 每次执行 SQL 语句都会建立连接、关闭连接，性能会受到很大影响。

- ArgumentPreparedStatementSetter：用于将参数传入 PreparedStatement 中。
- RowMapper：接口，行映射器，用于将结果集的每一行映射成一个对象。
- ResultSetExtractor：接口，结果集提取器，用于将结果集提取成一个集合对象。
- RowMapperResultSetExtractor：实现 ResultSetExtractor 接口，结果集提取器，用于将结果集提取成一个对象。
- PooledConnection：实现 Connection 接口，池化连接，用于获取连接池中的连接。
- PooledDataSource：实现 DataSource 接口，池化数据源，用于获取连接池中的连接。 

# 15｜mBatis：如何将SQL语句配置化？

## jdbc-03

- SqlSessionFactory：接口，SQL 会话工厂，用于创建 SQL 会话。
- DefaultSqlSessionFactory：实现 SQL 会话工厂接口，默认 SQL 会话工厂，用于创建默认 SQL 会话。
- SqlSession：接口，SQL 会话，用于执行 SQL 语句。
- DefaultSqlSession：实现 SQL 会话接口，默认 SQL 会话，用于执行 SQL 语句。
- MapperNode：节点，用于封装 SQL 语句。


# 问题记录

## 为啥不直接给成员变量赋值呢？

```java
DefaultListableBeanFactory beanFactory;
DefaultListableBeanFactory bf = new DefaultListableBeanFactory();        
this.beanFactory = bf;
```
为啥不直接给成员变量赋值呢？
> this.beanFactory = new DefaultListableBeanFactory();

习惯问题吧，Spring框架本身也是经常这样写。   
有一个潜在原因，但是我不是很确定，就是方法中尽量使用 local 变量，只在尽量少的场合使用实例变量和方法参数，这样提高性能减少开销。  
编译器优化是这么说的，但是我也不确定一定就是这样。  
另外，对源码，我还是建议最后去读 Spring 框架的源代码，那是世界顶级程序员的力作（推广到别的也是一样的，Apache Tomcat，JDK，Apache Dubbo）。
我自己也是在跟着他们的时候一旁偷学了几招，MiniSpring 的目的是剖析 Spring 框架内部结构，作为一个简明地图引导大家理解 Spring。
