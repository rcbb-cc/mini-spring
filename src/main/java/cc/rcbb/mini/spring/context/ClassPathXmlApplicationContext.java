package cc.rcbb.mini.spring.context;

import cc.rcbb.mini.spring.beans.config.BeanDefinition;
import cc.rcbb.mini.spring.beans.factory.BeanFactory;
import cc.rcbb.mini.spring.beans.factory.SimpleBeanFactory;
import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.core.ClassPathXmlResource;
import cc.rcbb.mini.spring.core.Resource;
import cc.rcbb.mini.spring.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * <p>
 * ClassPathXmlApplicationContext
 * </p>
 *
 * @author rcbb.cc
 * @date 2023/4/3
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        // 解析XML文件中的内容
        Resource resource = new ClassPathXmlResource(fileName);
        BeanFactory beanFactory = new SimpleBeanFactory();
        // 加载解析内容，构建BeanDefinition
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 读取BeanDefinition的配置信息，实例化Bean，注入到BeanFactory
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
