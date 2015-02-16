package com.hazyfutures.spritestable;

/**
 * Created by cdavis on 2/15/2015.
 */

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ReadXMLFile {

    public static PersistentValues loadFile(String FilePath){
        File fXmlFile = new File(FilePath);
        PersistentValues data = new PersistentValues();


        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return data;
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            data = getAttributes(doc, data);
            data = getQualities(doc, data);
            data = getSkills(doc, data);

            return data;
        } catch (SAXException e) {
            Log.e("SAXException:", FilePath);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("IOException:", FilePath);
            e.printStackTrace();
        }

        return data;
    }
    public static PersistentValues getAttributes(Document doc, PersistentValues data) {

    try {
       System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("attributes");
        NodeList Attributes = nList.item(0).getChildNodes();
        for (int temp = 0; temp < Attributes.getLength(); temp++) {

                Node node = Attributes.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;

                    data.setStatValue(eElement.getElementsByTagName("name").item(0).getTextContent(), Integer.valueOf(eElement.getElementsByTagName("totalvalue").item(0).getTextContent()));
                    System.out.println("Attribute : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Value : " + eElement.getElementsByTagName("totalvalue").item(0).getTextContent());
                    //TODO: After qualities are their own object, use SetQuality to handle this.
                }
            }

    } catch (Exception e) {
        e.printStackTrace();
    }
        return data;
}

    public static PersistentValues getSkills(Document doc, PersistentValues data) {

        try {

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("skills");
            NodeList Attributes = nList.item(0).getChildNodes();
            for (int temp = 0; temp < Attributes.getLength(); temp++) {

                Node node = Attributes.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    data.setSkillValue(eElement.getElementsByTagName("name").item(0).getTextContent(), Integer.valueOf(eElement.getElementsByTagName("rating").item(0).getTextContent()));
                    System.out.println("Skill : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Rating : " + eElement.getElementsByTagName("rating").item(0).getTextContent());
                    data.setSkillValue(eElement.getElementsByTagName("name").item(0).getTextContent(),Integer.valueOf(eElement.getElementsByTagName("rating").item(0).getTextContent()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static PersistentValues getQualities(Document doc, PersistentValues data) {

        try {
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("qualities");
            NodeList Attributes = nList.item(0).getChildNodes();
            for (int temp = 0; temp < Attributes.getLength(); temp++) {

                Node node = Attributes.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    data.setQualityValue(eElement.getElementsByTagName("name").item(0).getTextContent(),eElement.getElementsByTagName("extra").item(0).getTextContent(), Integer.valueOf(eElement.getElementsByTagName("bp").item(0).getTextContent()));
                    System.out.println("Quality : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Specifics : " + eElement.getElementsByTagName("extra").item(0).getTextContent());
                    System.out.println("Build Points : " + eElement.getElementsByTagName("bp").item(0).getTextContent());
                    //TODO: After qualities are their own object, use SetQuality to handle this.
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}