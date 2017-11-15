/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author XXie
 */
public class VisitXMLOperator {

    public static void updateStartTime(String startTime, String exprDir) {
        if (!checkFileExistence(exprDir)) {//create the file
            try {
                //create one
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//文件工厂初始化 
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.newDocument();//创建一个Document实例   
                Element root = doc.createElement("root");
                doc.appendChild(root);

                Element visitElem = createStartTimeElem(doc, startTime);
                root.appendChild(visitElem);

                TransformerFactory tff = TransformerFactory.newInstance();//解析器初始化
                Transformer tformer = tff.newTransformer();
                tformer.transform(new DOMSource(doc), new StreamResult(exprDir + "\\VisitLog.xml"));

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {//read in and add a new node
            try {
                File fXmlFile = new File(exprDir + "\\VisitLog.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                //optional, but recommended???
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                //doc.getDocumentElement().normalize();

                Element root = doc.getDocumentElement();

                Element visitElem = createStartTimeElem(doc, startTime);
                root.appendChild(visitElem);

                TransformerFactory tff = TransformerFactory.newInstance();//解析器初始化
                Transformer tformer = tff.newTransformer();
                tformer.transform(new DOMSource(doc), new StreamResult(exprDir + "\\VisitLog.xml"));

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

    public static void updateEndTime(String endTime, String exprDir) {
        //read in and update the end time of the last node
        try {
            File fXmlFile = new File(exprDir + "\\VisitLog.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);


            Element root = doc.getDocumentElement();

            NodeList nodeList = root.getElementsByTagName("visit");

            if (nodeList != null) {
                //update the last node
                Element newEndTimeElem = doc.createElement("endtime");//initial value is "-"
                newEndTimeElem.appendChild(doc.createTextNode(endTime));
                Element lastVisitElem = (Element) nodeList.item(nodeList.getLength() - 1);
                lastVisitElem.replaceChild(newEndTimeElem, lastVisitElem.getElementsByTagName("endtime").item(0));//start time remains unchanged 
            }
            TransformerFactory tff = TransformerFactory.newInstance();//解析器初始化
            Transformer tformer = tff.newTransformer();
            tformer.transform(new DOMSource(doc), new StreamResult(exprDir + "\\VisitLog.xml"));


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    

    private static Element createStartTimeElem(Document doc, String starTime) {
        Element newVisitElem = doc.createElement("visit");
        Element startTimeElem = doc.createElement("starttime");
        startTimeElem.appendChild(doc.createTextNode(starTime));
        Element endTimeElem = doc.createElement("endtime");
        endTimeElem.appendChild(doc.createTextNode("-"));
        newVisitElem.appendChild(startTimeElem);
        newVisitElem.appendChild(endTimeElem);

        return newVisitElem;
    }

    private static boolean checkFileExistence(String exprDir) {
        //if MR.xml exist
        File logFile = new File(exprDir + "\\VisitLog.xml");
        if (logFile.exists()) {
            return true;
        } else {
            return false;
        }
    }
}
