# 手把手带你写一个 MiniSpring

![手把手带你写一个](https://rcbb-blog.oss-cn-guangzhou.aliyuncs.com/2023/04/20230403190429-0120f4.png?x-oss-process=style/yuantu_shuiyin)


## 01.原始IoC：如何通过BeanFactory实现原始版本的IoC容器？

### ioc-01 初步定义

- BeanDefinition：Bean的定义。
- ClassPathXmlApplicationContext：读取 XML 配置文件，解析配置文件，创建 BeanDefinition，并初始化实例放入容器中。

### ioc-02 优化

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

![原始IoC](https://rcbb-blog.oss-cn-guangzhou.aliyuncs.com/2025/03/20250313103353-cd43e4.png?x-oss-process=style/yuantu_shuiyin)
