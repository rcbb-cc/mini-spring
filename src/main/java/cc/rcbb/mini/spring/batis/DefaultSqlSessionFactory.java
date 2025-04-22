package cc.rcbb.mini.spring.batis;

import cc.rcbb.mini.spring.beans.factory.annotation.Autowired;
import cc.rcbb.mini.spring.jdbc.core.JdbcTemplate;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * DefaultSqlSessionFactory
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/4/21
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    String mapperLocations;

    Map<String, MapperNode> mapperNodeMap = new HashMap<>();

    public DefaultSqlSessionFactory() {
    }

    public void init() {
        scanLocation(this.mapperLocations);
        for (Map.Entry<String, MapperNode> entry : this.mapperNodeMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private void scanLocation(String location) {
        String path = this.getClass().getClassLoader().getResource("").getPath() + location;
        System.out.println("mapper location = " + path);
        File dir = new File(path);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanLocation(location + "/" + file.getName());
            } else {
                buildMapperNodes(location + "/" + file.getName());
            }
        }
    }

    private Map<String, MapperNode> buildMapperNodes(String filePath) {
        SAXReader reader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(filePath);
        try {
            Document document = reader.read(xmlPath);
            Element rootElement = document.getRootElement();
            String namespace = rootElement.attributeValue("namespace");
            Iterator<Element> iterator = rootElement.elementIterator();
            while (iterator.hasNext()) {
                Element element = iterator.next();
                String id = element.attributeValue("id");
                String parameterType = element.attributeValue("parameterType");
                String resultType = element.attributeValue("resultType");
                String sql = element.getText();

                MapperNode mapperNode = new MapperNode();
                mapperNode.setNamespace(namespace);
                mapperNode.setId(id);
                mapperNode.setParameterType(parameterType);
                mapperNode.setResultType(resultType);
                mapperNode.setSql(sql);
                mapperNode.setParameter("");
                mapperNodeMap.put(namespace + "." + id, mapperNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.mapperNodeMap;
    }

    @Override
    public SqlSession openSession() {
        SqlSession sqlSession = new DefaultSqlSession();
        sqlSession.setJdbcTemplate(jdbcTemplate);
        sqlSession.setSqlSessionFactory(this);
        return sqlSession;
    }

    @Override
    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }


    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }
}
