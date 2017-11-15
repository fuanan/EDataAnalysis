/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

//import entity.CatWithChoices;
import entity.PairInformation;
import entity.GroupInformation;
import java.io.File;
import java.util.ArrayList;

import javax.sql.rowset.spi.XmlWriter;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
//import org.w3c.dom.Text;

/**
 *
 * @author xxie
 */
public class MRXMLOperator {

    
    public static void createPairInfoFileNew(ArrayList<ArrayList<PairInformation>> initInfoOfPairsFromDifferentGroups, ArrayList<ArrayList<PairInformation>> initInfoOfPairsFromSameGroups, String exprDir) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();//
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();// 
            Element root = doc.createElement("root");
            
            Element diffGroupIdentificationElem = doc.createElement("pairsfromdiffgroups");
            //different groups
            for(int i = 0; i < initInfoOfPairsFromDifferentGroups.size(); i++){
            	
            	String nameOfTwoGroups = initInfoOfPairsFromDifferentGroups.get(i).get(0).getGroupNameOfTestFrame1() + "<->" +
            			initInfoOfPairsFromDifferentGroups.get(i).get(0).getGroupNameOfTestFrame2();
            	
            	Element groupElem = doc.createElement("group");
            	groupElem.setAttribute("name",nameOfTwoGroups);
          	
            	for(int j = 0; j < initInfoOfPairsFromDifferentGroups.get(i).size(); j++){

                    PairInformation currPair = initInfoOfPairsFromDifferentGroups.get(i).get(j);
                    Element pairElem = doc.createElement("pair");
                    pairElem.setAttribute("pairname", currPair.getPairName());
            		           		
                    Element pairNameElem = doc.createElement("pairname");
                    pairNameElem.appendChild(doc.createTextNode(currPair.getPairName()));
                    
                    Element typeElem = doc.createElement("type");
                    typeElem.appendChild(doc.createTextNode(Integer.toString(currPair.getType())));
                    
                    Element groupNameOfTF1Elem = doc.createElement("groupnameoftestframe1");
                    groupNameOfTF1Elem.appendChild(doc.createTextNode(currPair.getGroupNameOfTestFrame1()));
                    
                    Element groupNameOfTF2Elem = doc.createElement("groupnameoftestframe2");
                    groupNameOfTF2Elem.appendChild(doc.createTextNode(currPair.getGroupNameOfTestFrame2()));
                    
                    Element hasMRElem = doc.createElement("hasmr");//initial value is "-"
                    hasMRElem.appendChild(doc.createTextNode(currPair.getHasMRInfo()));
                   
                    Element mrNameElem = doc.createElement("mrname");//initial value is "-"
                    mrNameElem.appendChild(doc.createTextNode(currPair.getMRName()));
                    
                    Element affectElem = doc.createElement("affect");//initial value is "-"
                    affectElem.appendChild(doc.createTextNode(currPair.getAffectOrNot()));
                    
                    Element inputRelationElem = doc.createElement("inputrelationdefinition");
                    inputRelationElem.appendChild(doc.createTextNode(currPair.getInputRelationDef()));
                    
                    Element outputRelationElem = doc.createElement("outputrelationdefinition");
                    outputRelationElem.appendChild(doc.createTextNode(currPair.getOutputRelationDef()));
                    
                    Element mrDefinitionElem = doc.createElement("mrdefinition");
                    mrDefinitionElem.appendChild(doc.createTextNode(currPair.getMRDefinition()));
                    
                    Element mrIDElem = doc.createElement("mrid");//initial value is "-1"
                    mrIDElem.appendChild(doc.createTextNode(Integer.toString(currPair.getMRID())));
                    
                    Element createTimeElem = doc.createElement("createdtime");//initial value is "-"
                    createTimeElem.appendChild(doc.createTextNode(currPair.getCreatedTime()));
                    
                    Element modifiedTimeElem = doc.createElement("modifiedtime");//initial value is "-"
                    modifiedTimeElem.appendChild(doc.createTextNode(currPair.getModifiedTime()));

                    pairElem.appendChild(pairNameElem);                    
                    pairElem.appendChild(typeElem);
                    pairElem.appendChild(groupNameOfTF1Elem);
                    pairElem.appendChild(groupNameOfTF2Elem);
                    pairElem.appendChild(hasMRElem);
                    pairElem.appendChild(mrNameElem);
                    pairElem.appendChild(affectElem);
                    pairElem.appendChild(inputRelationElem);
                    pairElem.appendChild(outputRelationElem);
                    pairElem.appendChild(mrDefinitionElem);
                    pairElem.appendChild(mrIDElem);
                    pairElem.appendChild(createTimeElem);
                    pairElem.appendChild(modifiedTimeElem);
                    
                    groupElem.appendChild(pairElem);
            	}            	
            	diffGroupIdentificationElem.appendChild(groupElem);
            }
            
            root.appendChild(diffGroupIdentificationElem);
            
            
            Element sameGroupIdentificationElem = doc.createElement("pairsfromsamegroups");
            
            
            
            //same groups
            for(int i = 0; i < initInfoOfPairsFromSameGroups.size(); i++){

            	String nameOfGroup = initInfoOfPairsFromSameGroups.get(i).get(0).getGroupNameOfTestFrame1() + "<->" + 
                                       initInfoOfPairsFromSameGroups.get(i).get(0).getGroupNameOfTestFrame1();
            	Element groupElem = doc.createElement("group");
            	groupElem.setAttribute("name", nameOfGroup);
            	            	
            	for(int j = 0; j < initInfoOfPairsFromSameGroups.get(i).size(); j++){

                    PairInformation currPair = initInfoOfPairsFromSameGroups.get(i).get(j);
                    Element pairElem = doc.createElement("pair");
                    pairElem.setAttribute("pairname", currPair.getPairName());
            		           		
                    Element pairNameElem = doc.createElement("pairname");
                    pairNameElem.appendChild(doc.createTextNode(currPair.getPairName()));
                    
                    Element typeElem = doc.createElement("type");
                    typeElem.appendChild(doc.createTextNode(Integer.toString(currPair.getType())));
                    
                    Element groupNameOfTF1Elem = doc.createElement("groupnameoftestframe1");
                    groupNameOfTF1Elem.appendChild(doc.createTextNode(currPair.getGroupNameOfTestFrame1()));
                    
                    Element groupNameOfTF2Elem = doc.createElement("groupnameoftestframe2");
                    groupNameOfTF2Elem.appendChild(doc.createTextNode(currPair.getGroupNameOfTestFrame2()));
                    
                    Element hasMRElem = doc.createElement("hasmr");//initial value is "-"
                    hasMRElem.appendChild(doc.createTextNode(currPair.getHasMRInfo()));
                   
                    Element mrNameElem = doc.createElement("mrname");//initial value is "-"
                    mrNameElem.appendChild(doc.createTextNode(currPair.getMRName()));
                    
                    Element affectElem = doc.createElement("affect");//initial value is "-"
                    affectElem.appendChild(doc.createTextNode(currPair.getAffectOrNot()));
                    
                    Element inputRelationElem = doc.createElement("inputrelationdefinition");
                    inputRelationElem.appendChild(doc.createTextNode(currPair.getInputRelationDef()));
                    
                    Element outputRelationElem = doc.createElement("outputrelationdefinition");
                    outputRelationElem.appendChild(doc.createTextNode(currPair.getOutputRelationDef()));
                    
                    Element mrDefinitionElem = doc.createElement("mrdefinition");
                    mrDefinitionElem.appendChild(doc.createTextNode(currPair.getMRDefinition()));
                    
                    Element mrIDElem = doc.createElement("mrid");//initial value is "-1"
                    mrIDElem.appendChild(doc.createTextNode(Integer.toString(currPair.getMRID())));
                    
                    Element createTimeElem = doc.createElement("createdtime");//initial value is "-"
                    createTimeElem.appendChild(doc.createTextNode(currPair.getCreatedTime()));
                    
                    Element modifiedTimeElem = doc.createElement("modifiedtime");//initial value is "-"
                    modifiedTimeElem.appendChild(doc.createTextNode(currPair.getModifiedTime()));

                    pairElem.appendChild(pairNameElem);                    
                    pairElem.appendChild(typeElem);
                    pairElem.appendChild(groupNameOfTF1Elem);
                    pairElem.appendChild(groupNameOfTF2Elem);
                    pairElem.appendChild(hasMRElem);
                    pairElem.appendChild(mrNameElem);
                    pairElem.appendChild(affectElem);
                    pairElem.appendChild(inputRelationElem);
                    pairElem.appendChild(outputRelationElem);
                    pairElem.appendChild(mrDefinitionElem);
                    pairElem.appendChild(mrIDElem);
                    pairElem.appendChild(createTimeElem);
                    pairElem.appendChild(modifiedTimeElem);
                    
                    groupElem.appendChild(pairElem);

            	}
            	sameGroupIdentificationElem.appendChild(groupElem);
            }
            root.appendChild(sameGroupIdentificationElem);        
            
            doc.appendChild(root);
            
            //doc.appendChild(root);
            //OutputFormat format = new OutputFormat();
            //XMLWriter xmlWriter =new XMLWriter(System.out, format);
            //xmlWriter.write(doc);
            
            TransformerFactory tff = TransformerFactory.newInstance();//
            Transformer tformer = tff.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(exprDir + "\\MR.xml");
            tformer.transform(domSource, streamResult);
           
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    
    public static void updatePairInfoFileNew(PairInformation newPairInfo, String exprDir) {
        try {
            //in the ArrayList<PairInformation>allPairsInfo of the pairManager, the pair's information under the given index has already been given.
            //then, update the information in the xml tree, by updating the corresponding node (element)
            File fXmlFile = new File(exprDir + "\\MR.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            Element root = doc.getDocumentElement();
            
            Node specifiedGroupNode;
    		Node specifiedPairNode;
            
            if(newPairInfo.getType() == 1){
            	
            	NodeList diffGroupIdentification = root.getElementsByTagName("pairsfromdiffgroups");
            	if(diffGroupIdentification != null) {
            		NodeList groupNodes = ((Element)(diffGroupIdentification.item(0))).getChildNodes();
            		
            		for(int i = 0; i< groupNodes.getLength();i++){
            			String s = groupNodes.item(i).getAttributes().getNamedItem("name").getNodeValue();
            			if(s.equals(newPairInfo.getGroupNameOfTestFrame1()+"<->"+newPairInfo.getGroupNameOfTestFrame2())){
            				specifiedGroupNode = groupNodes.item(i);           				
            				NodeList pairNodes = ((Element)specifiedGroupNode).getChildNodes();                    		
                                        for(int j=0;j < pairNodes.getLength(); j++){
                    			
                    			if(pairNodes.item(j).getAttributes().getNamedItem("pairname").getNodeValue().equals(newPairInfo.getPairName())){
                    				specifiedPairNode = pairNodes.item(j); 
                    				Element newHasMRElem = doc.createElement("hasmr");//initial value is "-"
                                                newHasMRElem.appendChild(doc.createTextNode(newPairInfo.getHasMRInfo()));                  
                                                Element newMRNameElem = doc.createElement("mrname");//initial value is "-"
                                                newMRNameElem.appendChild(doc.createTextNode(newPairInfo.getMRName()));                    
                                                Element newAffectElem = doc.createElement("affect");//initial value is "-"
                                                newAffectElem.appendChild(doc.createTextNode(newPairInfo.getAffectOrNot()));                    
                                                Element newInputRelationElem = doc.createElement("inputrelationdefinition");
                                                newInputRelationElem.appendChild(doc.createTextNode(newPairInfo.getInputRelationDef()));                    
                                                Element newOutputRelationElem = doc.createElement("outputrelationdefinition");
                                                newOutputRelationElem.appendChild(doc.createTextNode(newPairInfo.getOutputRelationDef()));  
                                                Element newMRDefinitionElem = doc.createElement("mrdefinition");
                                                newMRDefinitionElem.appendChild((doc.createTextNode(newPairInfo.getMRDefinition())));
                                                Element newMRIDElem = doc.createElement("mrid");//initial value is "-1"
                                                newMRIDElem.appendChild(doc.createTextNode(Integer.toString(newPairInfo.getMRID())));                    
                                                Element newCreateTimeElem = doc.createElement("createdtime");//initial value is "-"
                                                newCreateTimeElem.appendChild(doc.createTextNode(newPairInfo.getCreatedTime()));                   
                                                Element newModifiedTimeElem = doc.createElement("modifiedtime");//initial value is "-"
                                                newModifiedTimeElem.appendChild(doc.createTextNode(newPairInfo.getModifiedTime()));

                                                if (specifiedPairNode.getNodeType() == Node.ELEMENT_NODE) {
                                                    //update this element
                                                    Element specifiedPairElement = (Element) specifiedPairNode;
                                                    specifiedPairElement.replaceChild(newHasMRElem, specifiedPairElement.getElementsByTagName("hasmr").item(0));
                                                    specifiedPairElement.replaceChild(newMRNameElem, specifiedPairElement.getElementsByTagName("mrname").item(0));
                                                    specifiedPairElement.replaceChild(newAffectElem, specifiedPairElement.getElementsByTagName("affect").item(0));
                                                    specifiedPairElement.replaceChild(newInputRelationElem, specifiedPairElement.getElementsByTagName("inputrelationdefinition").item(0));
                                                    specifiedPairElement.replaceChild(newOutputRelationElem, specifiedPairElement.getElementsByTagName("outputrelationdefinition").item(0));
                                                    specifiedPairElement.replaceChild(newMRDefinitionElem,specifiedPairElement.getElementsByTagName("mrdefinition").item(0));
                                                    specifiedPairElement.replaceChild(newMRIDElem, specifiedPairElement.getElementsByTagName("mrid").item(0));
                                                    specifiedPairElement.replaceChild(newCreateTimeElem, specifiedPairElement.getElementsByTagName("createdtime").item(0));
                                                    specifiedPairElement.replaceChild(newModifiedTimeElem, specifiedPairElement.getElementsByTagName("modifiedtime").item(0));
                                                }
                                            }
                                    }
            			}
            		}            		          	            		
            	}            	
            }else if(newPairInfo.getType() == 2){
            	NodeList sameGroupIdentification = root.getElementsByTagName("pairsfromsamegroups");
            	if(sameGroupIdentification != null) {
            		NodeList groupNodes = ((Element)(sameGroupIdentification.item(0))).getChildNodes();           		
            		for(int i = 0; i< groupNodes.getLength();i++){
            			
                            if(groupNodes.item(i).getAttributes().getNamedItem("name").getNodeValue().equals(newPairInfo.getGroupNameOfTestFrame1() + "<->" + newPairInfo.getGroupNameOfTestFrame1())){
            			specifiedGroupNode = groupNodes.item(i);           				
            			NodeList pairNodes = ((Element)specifiedGroupNode).getChildNodes();                    		
                    		for(int j=0;j < pairNodes.getLength(); j++){
                    			
                                    if(pairNodes.item(j).getAttributes().getNamedItem("pairname").getNodeValue().equals(newPairInfo.getPairName())){
                                        specifiedPairNode = pairNodes.item(j); 
                    			Element newHasMRElem = doc.createElement("hasmr");//initial value is "-"
                                        newHasMRElem.appendChild(doc.createTextNode(newPairInfo.getHasMRInfo()));                  
                                        Element newMRNameElem = doc.createElement("mrname");//initial value is "-"
                                        newMRNameElem.appendChild(doc.createTextNode(newPairInfo.getMRName()));                    
                                        Element newAffectElem = doc.createElement("affect");//initial value is "-"
                                        newAffectElem.appendChild(doc.createTextNode(newPairInfo.getAffectOrNot()));                    
                                        Element newInputRelationElem = doc.createElement("inputrelationdefinition");
                                        newInputRelationElem.appendChild(doc.createTextNode(newPairInfo.getInputRelationDef()));                    
                                        Element newOutputRelationElem = doc.createElement("outputrelationdefinition");
                                        newOutputRelationElem.appendChild(doc.createTextNode(newPairInfo.getOutputRelationDef()));
                                        Element newMRDefinitionElem = doc.createElement("mrdefinition");
                                        newMRDefinitionElem.appendChild(doc.createTextNode(newPairInfo.getMRDefinition()));
                                        Element newMRIDElem = doc.createElement("mrid");//initial value is "-1"
                                        newMRIDElem.appendChild(doc.createTextNode(Integer.toString(newPairInfo.getMRID())));                    
                                        Element newCreateTimeElem = doc.createElement("createdtime");//initial value is "-"
                                        newCreateTimeElem.appendChild(doc.createTextNode(newPairInfo.getCreatedTime()));                   
                                        Element newModifiedTimeElem = doc.createElement("modifiedtime");//initial value is "-"
                                        newModifiedTimeElem.appendChild(doc.createTextNode(newPairInfo.getModifiedTime()));
                    	            
                                        if (specifiedPairNode.getNodeType() == Node.ELEMENT_NODE) {
                    	                //update this element
                                            Element specifiedPairElement = (Element) specifiedPairNode;
                                            specifiedPairElement.replaceChild(newHasMRElem, specifiedPairElement.getElementsByTagName("hasmr").item(0));
                                            specifiedPairElement.replaceChild(newMRNameElem, specifiedPairElement.getElementsByTagName("mrname").item(0));
                                            specifiedPairElement.replaceChild(newAffectElem, specifiedPairElement.getElementsByTagName("affect").item(0));
                                            specifiedPairElement.replaceChild(newInputRelationElem, specifiedPairElement.getElementsByTagName("inputrelationdefinition").item(0));
                                            specifiedPairElement.replaceChild(newOutputRelationElem, specifiedPairElement.getElementsByTagName("outputrelationdefinition").item(0));
                                            specifiedPairElement.replaceChild(newMRDefinitionElem,specifiedPairElement.getElementsByTagName("mrdefinition").item(0));
                                            specifiedPairElement.replaceChild(newMRIDElem, specifiedPairElement.getElementsByTagName("mrid").item(0));
                                            specifiedPairElement.replaceChild(newCreateTimeElem, specifiedPairElement.getElementsByTagName("createdtime").item(0));
                                            specifiedPairElement.replaceChild(newModifiedTimeElem, specifiedPairElement.getElementsByTagName("modifiedtime").item(0));
                                            }
                    			}
                                    }
            			}
            		}            		          	            		
            	}
            	
            }else{
            	JOptionPane.showMessageDialog(null, "Can Not Find This Pair In MR.xml", null, JOptionPane.INFORMATION_MESSAGE);
            }
            
            
            //then update the MR.xml file
            TransformerFactory tff = TransformerFactory.newInstance();//解析器初始化
            Transformer tformer = tff.newTransformer();
            tformer.transform(new DOMSource(doc), new StreamResult(exprDir + "\\MR.xml"));
            
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<ArrayList<ArrayList<PairInformation>>> readInAllPairsInfoNew(String exprDir) {
    	
        ArrayList<ArrayList<ArrayList<PairInformation>>> allPairInfo = new ArrayList<ArrayList<ArrayList<PairInformation>>>();
        
        
        try {

            File fXmlFile = new File(exprDir + "\\MR.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            

//            TransformerFactory tff = TransformerFactory.newInstance();//
//            Transformer tformer = tff.newTransformer();
//            DOMSource domSource = new DOMSource(doc);
//            StreamResult streamResult = new StreamResult(exprDir + "\\MR1.xml");
//            tformer.transform(domSource, streamResult);

            //optional, but recommended???
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            //doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
            //different group
            NodeList diffGroupIdentification = root.getElementsByTagName("pairsfromdiffgroups");          
            ArrayList<ArrayList<PairInformation>> infoOfPairsFromDiffenentGroups = new ArrayList<ArrayList<PairInformation>>();         
            if(diffGroupIdentification != null) {
            	NodeList diffGroups = diffGroupIdentification.item(0).getChildNodes();
            	for(int i = 0; i < diffGroups.getLength(); i++) {
            		Node currGroupNode = diffGroups.item(i);
            		NodeList pairsOfCurrGroup = currGroupNode.getChildNodes();
            		
            		ArrayList<PairInformation> currGroup = new ArrayList<PairInformation>() ;
            		
            		for(int j = 0; j < pairsOfCurrGroup.getLength(); j++){
            			Node currPairNode = pairsOfCurrGroup.item(j);
            			if(currPairNode.getNodeType() == Node.ELEMENT_NODE) {
            				Element currPairElement = (Element) currPairNode;
            				
            				String pairName = currPairElement.getElementsByTagName("pairname").item(0).getFirstChild().getNodeValue();
            				int type = Integer.parseInt(currPairElement.getElementsByTagName("type").item(0).getTextContent());
            				String groupNameOfTF1 = currPairElement.getElementsByTagName("groupnameoftestframe1").item(0).getTextContent();
            				String groupNameOfTF2 = currPairElement.getElementsByTagName("groupnameoftestframe2").item(0).getTextContent();
            				String hasMR = currPairElement.getElementsByTagName("hasmr").item(0).getTextContent();           				
            				String mrName = currPairElement.getElementsByTagName("mrname").item(0).getTextContent();
            				
            				String inputRelationDefinition = currPairElement.getElementsByTagName("inputrelationdefinition").item(0).getTextContent();
            				String outputRelationDefinition = currPairElement.getElementsByTagName("outputrelationdefinition").item(0).getTextContent(); 
                                        String mrDefinition = currPairElement.getElementsByTagName("mrdefinition").item(0).getTextContent();
                                        String affectOrNot = currPairElement.getElementsByTagName("affect").item(0).getTextContent();
                                        int mrID = Integer.parseInt(currPairElement.getElementsByTagName("mrid").item(0).getTextContent());
                                        String createdTime = currPairElement.getElementsByTagName("createdtime").item(0).getTextContent();
                                        String lastModifiedTime = currPairElement.getElementsByTagName("modifiedtime").item(0).getTextContent();
            				
            				PairInformation tempPair = new PairInformation(pairName,type,groupNameOfTF1,groupNameOfTF2,hasMR,mrName,inputRelationDefinition,outputRelationDefinition,mrDefinition,affectOrNot,mrID,createdTime,lastModifiedTime);
            				currGroup.add(tempPair);                                           
            			}
            		}
            		infoOfPairsFromDiffenentGroups.add(currGroup);
            	}           	
            }
            
            //same group
            NodeList sameGroupIdentification = root.getElementsByTagName("pairsfromsamegroups");
            ArrayList<ArrayList<PairInformation>> infoOfPairsFromSameGroups = new ArrayList<ArrayList<PairInformation>>();
            if(sameGroupIdentification != null){
            	NodeList sameGroups = sameGroupIdentification.item(0).getChildNodes();
            	for(int i = 0; i < sameGroups.getLength(); i++) {
            		Node currGroupNode = sameGroups.item(i);
            		NodeList pairsOfCurrGroup = currGroupNode.getChildNodes();
            		
            		ArrayList<PairInformation> currGroup = new ArrayList<PairInformation>();
            		
            		for(int j = 0; j < pairsOfCurrGroup.getLength(); j++){
            			Node currPairNode = pairsOfCurrGroup.item(j);
            			if(currPairNode.getNodeType() == Node.ELEMENT_NODE) {
            				Element currPairElement = (Element) currPairNode;
            				
            				String pairName = currPairElement.getElementsByTagName("pairname").item(0).getFirstChild().getNodeValue();
            				int type = Integer.parseInt(currPairElement.getElementsByTagName("type").item(0).getTextContent());
            				String groupNameOfTF1 = currPairElement.getElementsByTagName("groupnameoftestframe1").item(0).getTextContent();
            				String groupNameOfTF2 = currPairElement.getElementsByTagName("groupnameoftestframe2").item(0).getTextContent();
            				String hasMR = currPairElement.getElementsByTagName("hasmr").item(0).getTextContent();           				
            				String mrName = currPairElement.getElementsByTagName("mrname").item(0).getTextContent();
            				
            				String inputRelationDefinition = currPairElement.getElementsByTagName("inputrelationdefinition").item(0).getTextContent();
            				String outputRelationDefinition = currPairElement.getElementsByTagName("outputrelationdefinition").item(0).getTextContent();   
                                        String mrDefinition = currPairElement.getElementsByTagName("mrdefinition").item(0).getTextContent();
                                        String affectOrNot = currPairElement.getElementsByTagName("affect").item(0).getTextContent();
                                        int mrID = Integer.parseInt(currPairElement.getElementsByTagName("mrid").item(0).getTextContent());
                                        String createdTime = currPairElement.getElementsByTagName("createdtime").item(0).getTextContent();
                                        String lastModifiedTime = currPairElement.getElementsByTagName("modifiedtime").item(0).getTextContent();
            				
            				PairInformation tempPair = new PairInformation(pairName,type,groupNameOfTF1,groupNameOfTF2,hasMR,mrName,inputRelationDefinition,outputRelationDefinition,mrDefinition,affectOrNot,mrID,createdTime,lastModifiedTime);
            				currGroup.add(tempPair);            				
            			}
            		}
            		infoOfPairsFromSameGroups.add(currGroup);
            	}
            }
            
            
            allPairInfo.add(infoOfPairsFromDiffenentGroups);
            allPairInfo.add(infoOfPairsFromSameGroups);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return allPairInfo;
    }

    
    public static boolean checkFileExistence(String exprDir) {
        //if MR.xml exist
        File mrFile = new File(exprDir + "\\MR.xml");
        if (mrFile.exists()) {
            return true;
        } else {
            return false;
        }
    }
}
