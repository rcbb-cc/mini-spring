package cc.rcbb.mini.spring;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
/**
 * <p>
 * App
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/26
 */
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
