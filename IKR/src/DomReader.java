import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomReader {
    public static List <AbstractLamp> read(File file) throws IOException, SAXException, ParserConfigurationException, DataException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        List<AbstractLamp> lamps = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("lamp");
        NamedNodeMap attr;

        String producer;
        String capacity;
        String temp;

        if (document.getDocumentElement().getNodeName().equals("incLamp")) {
            for (int i = 0; i < nodeList.getLength(); ++i) {
                attr = nodeList.item(i).getAttributes();

                producer = attr.getNamedItem("producer").getNodeValue();
                capacity = attr.getNamedItem("capacity").getNodeValue();

                temp = nodeList.item(i).getTextContent().replace("\n", "").trim();

                lamps.add((AbstractLamp) new IncandescentLamp(producer, Integer.valueOf(capacity), Integer.valueOf(temp)));
            }
        } else {
            for (int i = 0; i < nodeList.getLength(); ++i) {
                attr = nodeList.item(i).getAttributes();

                producer = attr.getNamedItem("producer").getNodeValue();
                capacity = attr.getNamedItem("capacity").getNodeValue();

                temp = nodeList.item(i).getTextContent().replace("\n", "").trim();

                lamps.add((AbstractLamp) new DiodeLamp(producer, Integer.valueOf(capacity), Integer.valueOf(temp)));
            }
        }

        return lamps;
    }
}