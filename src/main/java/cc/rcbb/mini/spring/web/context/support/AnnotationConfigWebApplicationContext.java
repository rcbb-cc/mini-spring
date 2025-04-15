package cc.rcbb.mini.spring.web.context.support;

import cc.rcbb.mini.spring.beans.BeanDefinition;
import cc.rcbb.mini.spring.beans.BeansException;
import cc.rcbb.mini.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import cc.rcbb.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import cc.rcbb.mini.spring.beans.factory.config.ConfigurableListableBeanFactory;
import cc.rcbb.mini.spring.beans.factory.support.DefaultListableBeanFactory;
import cc.rcbb.mini.spring.context.*;
import cc.rcbb.mini.spring.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * AnnotationConfigWebApplicationContext
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/27
 */
public class AnnotationConfigWebApplicationContext extends AbstractApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;
    private WebApplicationContext parentApplicationContext;
    DefaultListableBeanFactory beanFactory;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public AnnotationConfigWebApplicationContext(String fileName) {
        this(fileName, null);
    }

    public AnnotationConfigWebApplicationContext(String fileName, WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        this.servletContext = this.parentApplicationContext.getServletContext();
        URL url = null;
        try {
            url = this.getServletContext().getResource(fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        List<String> packageNames = XmlScanComponentHelper.getNodeValue(url);
        List<String> controllerNames = this.scanPackages(packageNames);
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        this.beanFactory = beanFactory;
        this.beanFactory.setParent(this.parentApplicationContext.getBeanFactory());
        this.loadBeanDefinitions(controllerNames);

        if (true) {
            try {
                refresh();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory bf) {

    }

    @Override
    public void registerBeanPostProcessors(ConfigurableListableBeanFactory bf) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    public void initApplicationEventPublisher() {
        ApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    public void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }

    @Override
    public void finishRefresh() {
        // TODO Auto-generated method stub

    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packageNames) {
            tempControllerNames.addAll(this.scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        System.out.println("packageName = " + packageName);
        // 将以.分隔的包名换成以/分隔的uri
        String packagePath = packageName.replaceAll("\\.", "/");
        System.out.println("packagePath = " + packagePath);

        List<String> tempControllerNames = new ArrayList<>();
        URL url = this.getClass().getClassLoader().getResource(packagePath);
        System.out.println("url = " + url);

        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }
        return tempControllerNames;
    }

    public void loadBeanDefinitions(List<String> controllerNames) {
        for (String controller : controllerNames) {
            String beanId = controller;
            String beanClassName = controller;
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            this.beanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }

    public void setParent(WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        this.beanFactory.setParent(this.parentApplicationContext.getBeanFactory());
    }

}
