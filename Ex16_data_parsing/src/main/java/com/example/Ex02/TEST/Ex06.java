package com.example.Ex02.TEST;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Ex06 {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        // http://openapi.seoul.go.kr:8088/(인증키)/xml/SeoulPublicLibraryInfo/1/5/
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" + URLEncoder.encode("59506c474d756a303835756b594a62", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("SeoulPublicLibraryInfo", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("20", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        StringBuilder xmlResponse = new StringBuilder();
        String line;
        StringBuilder xmlLine = new StringBuilder();

        while ((line = reader.readLine()) != null){
            System.out.println(line);
            xmlLine.append(line);
        }
        reader.close();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(xmlLine.toString().getBytes("UTF-8")));
        int rowLength = document.getElementsByTagName("row").getLength();

        NodeList libraryNameNodeList = document.getElementsByTagName("LBRRY_NAME");
        NodeList addressNodeList = document.getElementsByTagName("ADRES");

        for(int i=0; i<rowLength; i++){
            Node libraryNameNode = libraryNameNodeList.item(i);
            Node addressNode = addressNodeList.item(i);
            System.out.println("도서관명: " + libraryNameNode.getTextContent());
            System.out.println("주소: " + addressNode.getTextContent());
            System.out.println("----------------------------");
        }
    }
}
