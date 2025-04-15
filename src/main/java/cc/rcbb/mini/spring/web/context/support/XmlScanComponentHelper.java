package cc.rcbb.mini.spring.web.context.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * XmlScanComponentHelper
 * </p>
 *
 * @author rcbb.cc
 * @date 2025/3/26
 */
public class XmlScanComponentHelper {

    public static List<String> getNodeValue(URL url) {
        List<String> packages = new ArrayList<>();
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(url);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        Element root = document.getRootElement();
        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            packages.add(element.attributeValue("base-package"));
        }
        return packages;
    }

}
