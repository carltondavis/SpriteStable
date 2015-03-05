package com.dasixes.spritestable;

/**
 * Created by cdavis on 2/15/2015.
 */

import android.app.Activity;
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

    MainActivity Main;
    public void loadFile(String FilePath, Activity activity){
        MainActivity Main = (MainActivity)activity;
        File fXmlFile = new File(FilePath);
        //PersistentValues data = new PersistentValues();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return;// data;
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            getAttributes(doc, activity);
            getQualities(doc, activity);
            getSkills(doc, activity);
            getSprites(doc, activity);
            getComplexForms(doc, activity);

           // return data;
        } catch (SAXException e) {
            Log.e("SAXException:", FilePath);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("IOException:", FilePath);
            e.printStackTrace();
        }

        return;// data;
    }
    public void getAttributes(Document doc, Activity activity) {
        MainActivity Main = (MainActivity)activity;
    try {
       System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("attributes");
        NodeList Attributes = nList.item(0).getChildNodes();
        for (int temp = 0; temp < Attributes.getLength(); temp++) {

                Node node = Attributes.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;

                    Main.data.setStatValueShort(eElement.getElementsByTagName("name").item(0).getTextContent(), Integer.valueOf(eElement.getElementsByTagName("totalvalue").item(0).getTextContent()));
                    System.out.println("Attribute : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Value : " + eElement.getElementsByTagName("totalvalue").item(0).getTextContent());

                }
            }

    } catch (Exception e) {
        e.printStackTrace();
    }
    //  return data;
}

    public void getSkills(Document doc, Activity activity) {
        MainActivity Main = (MainActivity)activity;
        try {

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("skills");
            NodeList Attributes = nList.item(0).getChildNodes();
            for (int temp = 0; temp < Attributes.getLength(); temp++) {

                Node node = Attributes.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    String SkillName=eElement.getElementsByTagName("name").item(0).getTextContent();
                    Main.data.setSkillValue(SkillName, Integer.valueOf(eElement.getElementsByTagName("rating").item(0).getTextContent()));
                    System.out.println("Skill : " + SkillName);
                    System.out.println("Rating : " + eElement.getElementsByTagName("rating").item(0).getTextContent());

                    if (node.hasChildNodes()) {
                       NodeList sList = ((Element) node).getElementsByTagName("skillspecializations");
                        if(sList.getLength()>0) {
                            NodeList Specializations = sList.item(0).getChildNodes();
                            for (int i = 0; i < Specializations.getLength(); i++) {
                                Node spec = Specializations.item(i);
                                if (spec.getNodeType() == Node.ELEMENT_NODE) {
                                    Element sElement = (Element) spec;
                                    System.out.println("Specialization : " + sElement.getElementsByTagName("name").item(0).getTextContent());
                                    Main.data.setSpecialization(SkillName, sElement.getElementsByTagName("name").item(0).getTextContent(), true);
                                }
                            }
                        }
                    }




                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
     //   return data;
    }

    public void getSprites(Document doc, Activity activity) {
        MainActivity Main = (MainActivity)activity;
        try {

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("spirits");
            NodeList Attributes = nList.item(0).getChildNodes();
            Sprite sprite = new Sprite();
            for (int temp = 0; temp < Attributes.getLength(); temp++) {

                Node node = Attributes.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    String SpriteType=eElement.getElementsByTagName("name").item(0).getTextContent();
                    SpriteType=SpriteType.substring(0, SpriteType.length()-7);
                    sprite.setServicesOwed(Integer.valueOf(eElement.getElementsByTagName("services").item(0).getTextContent()));
                    sprite.setRating(Integer.valueOf(eElement.getElementsByTagName("force").item(0).getTextContent()));
                    sprite.setSpriteType(SpriteType);
                    sprite.setRegistered(1);


                    Main.data.SaveNewSpriteToDB(sprite);
                    System.out.println("Sprite Type : " + SpriteType);
                    System.out.println("Force : " + eElement.getElementsByTagName("force").item(0).getTextContent());
                    System.out.println("Services Owed : " + eElement.getElementsByTagName("services").item(0).getTextContent());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //   return data;
    }

    public void getComplexForms(Document doc, Activity activity) {
        MainActivity Main = (MainActivity)activity;
        try {

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("complexforms");
            NodeList Attributes = nList.item(0).getChildNodes();
            ComplexForm cf = new ComplexForm();
            for (int temp = 0; temp < Attributes.getLength(); temp++) {

                Node node = Attributes.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    name = name.replace("[Matrix Attribute]", eElement.getElementsByTagName("extra").item(0).getTextContent());
                    cf.setName(name);

                    cf.setFading(eElement.getElementsByTagName("fv").item(0).getTextContent());
                    cf.setTarget(eElement.getElementsByTagName("target").item(0).getTextContent());
                    cf.setDuration(eElement.getElementsByTagName("duration").item(0).getTextContent());




                    Main.data.SaveNewComplexFormToDB(cf);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //   return data;
    }

    public void getQualities(Document doc, Activity activity) {
        MainActivity Main = (MainActivity)activity;
        try {
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("qualities");
            NodeList Attributes = nList.item(0).getChildNodes();
            for (int temp = 0; temp < Attributes.getLength(); temp++) {

                Node node = Attributes.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) node;
                    System.out.println("Quality : " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Specifics : " + eElement.getElementsByTagName("extra").item(0).getTextContent());
                    System.out.println("Build Points : " + eElement.getElementsByTagName("bp").item(0).getTextContent());
                    Main.data.setQualityValueWithBP(eElement.getElementsByTagName("name").item(0).getTextContent(), eElement.getElementsByTagName("extra").item(0).getTextContent(), Integer.valueOf(eElement.getElementsByTagName("bp").item(0).getTextContent()));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return data;
    }
}