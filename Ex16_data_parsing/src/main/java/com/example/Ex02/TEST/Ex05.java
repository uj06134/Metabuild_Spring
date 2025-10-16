package com.example.Ex02.TEST;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Ex05 {
    public static void main(String[] args) {

        try {
            File xmlFile = new File("file\\info.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            NodeList nodeList = document.getElementsByTagName("person");
            for(int i=0; i< nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                // System.out.println("node: " + node);

                Element element = (Element) node;
                // System.out.println("element: " + element);
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                String age = element.getElementsByTagName("age").item(0).getTextContent();
                String city = element.getElementsByTagName("city").item(0).getTextContent();
                System.out.println(name + "/" + age + "/" + city);
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


    }
}