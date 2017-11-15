/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.io.File;
import java.io.FileOutputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

/**
 *
 * @author XXie
 */
public class ReportPrinter {

    public static void createMRReport(String reportName, String reportDir, String exprDir) {
        try {
            
            File dir = new File(exprDir + "\\" + reportDir);
            if(!dir.exists()){
                dir.mkdir();
            }
            String styleFileName = new String();
            String reportFileName = new String();
            if(reportName.equals("TraverseLog")){
                styleFileName = "styles\\traverselogstyle.xsl";
                reportFileName = "TraverseLogReport.html";
            }else if (reportName.equals("MR")){
                styleFileName = "styles\\mrtablestyle.xsl";
                reportFileName = "MRReport.html";
            }
                      
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer =
                    tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(styleFileName));
            transformer.transform(new javax.xml.transform.stream.StreamSource(exprDir + "\\MR.xml"),
                    new javax.xml.transform.stream.StreamResult(new FileOutputStream(dir + "\\"+ reportFileName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void createVisitLogReport(String reportDir, String exprDir) {
        try {
            File dir = new File(exprDir + "\\" + reportDir);
            if(!dir.exists()){
                dir.mkdir();
            }
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer =
                    tFactory.newTransformer(new javax.xml.transform.stream.StreamSource("styles\\visitlogstyle.xsl"));
            transformer.transform(new javax.xml.transform.stream.StreamSource(exprDir + "\\VisitLog.xml"),
                    new javax.xml.transform.stream.StreamResult(new FileOutputStream(dir + "\\VisitLog.html")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
