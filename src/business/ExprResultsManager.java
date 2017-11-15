/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataaccess.ReportPrinter;
import dataaccess.VisitXMLOperator;

/**
 *
 * @author XXie
 */
public class ExprResultsManager {
    
    private String exprDir;
    
    public ExprResultsManager(String expDirctory) {
        this.exprDir = expDirctory;
       
    }
   
    
    public void updateStartTimeLog(String startTime){//operation on VisitLog.xml
        VisitXMLOperator.updateStartTime(startTime, this.exprDir);
    }
    public void updateEndTimeLog(String endTime){//operation on VisitLog.xml
        VisitXMLOperator.updateEndTime(endTime, this.exprDir);
    }
    
    public void genMRReport(String reportDir){//generate html 
        ReportPrinter.createMRReport("MR", reportDir, this.exprDir);
    }
    public void genTraverseLogReport(String reportDir){//generate html 
        ReportPrinter.createMRReport("TraverseLog", reportDir, this.exprDir);
    }
    public void genVisitLogReport(String reportDir){//generate html 
        ReportPrinter.createVisitLogReport(reportDir, this.exprDir);
    }
   
}
