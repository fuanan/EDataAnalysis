/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import entity.CatWithChoices;
import entity.TestFrame;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author xxie
 */
public class SourceDataReader {

    public static ArrayList<CatWithChoices> getAllCategories(File f) {

        //File f = new File(fileName);
        ArrayList<CatWithChoices> allCatWithChoices = new ArrayList<>();

        try {
            FileInputStream is = new FileInputStream(f);
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet catSheet = wb.getSheetAt(0); //the first sheet gives the definition of all categories and choices
            ArrayList<String> allCatLines = read(catSheet);

            int startLineID = 3;//alternatively, search for the [START], and the next line is the starting point
            for (int i = startLineID; i < allCatLines.size(); i++) {
                String currLineStr = allCatLines.get(i);
                //if (currLineStr.startsWith("[END]")) {
                    //break;
                //}
                //else, split this line with ","
                String currCellsArr[] = currLineStr.split(",");

                //j = 0: currCellsArr[0] is the tag and name of the category
                String currCatInfoArr[] = currCellsArr[0].split(":");
                String currCatTag = currCatInfoArr[0];
                String currCatName = currCatInfoArr[1];

                //j > 0: currCellsArr[j] is the tag and name of each choice under this category
                ArrayList<String> currChoiceTags = new ArrayList<>();
                ArrayList<String> currChoiceNames = new ArrayList<>();
                for (int j = 1; j < currCellsArr.length; j++) {
                    String tempChoiceInfoArr[] = currCellsArr[j].split(":");
                    currChoiceTags.add(tempChoiceInfoArr[0]);
                    currChoiceNames.add(tempChoiceInfoArr[1]);
                }

                CatWithChoices currCatWithChoice = new CatWithChoices(currCatTag, currCatName, currChoiceTags, currChoiceNames);
                allCatWithChoices.add(currCatWithChoice);
            }
        } catch (Exception e) {
        }

        return allCatWithChoices;


    }
    
    public static ArrayList<CatWithChoices> getAllInputCategories(File f) {

        //File f = new File(fileName);
        ArrayList<CatWithChoices> allInputCatWithChoices = new ArrayList<>();

        try {
            FileInputStream is = new FileInputStream(f);
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet catSheet = wb.getSheetAt(0); //the first sheet gives the definition of all categories and choices
            ArrayList<String> allCatLines = read(catSheet);

            int startLineID = 3;//alternatively, search for the [START], and the next line is the starting point
            for (int i = startLineID; i < allCatLines.size(); i++) {
                String currLineStr = allCatLines.get(i);
                //if (currLineStr.startsWith("[END]")) {
                    //break;
                //}
                //else, split this line with ","
                String currCellsArr[] = currLineStr.split(",");

                //j = 0: currCellsArr[0] is the tag and name of the category
                String currCatInfoArr[] = currCellsArr[0].split(":");
                String currCatTag = currCatInfoArr[0];
                String currCatName = currCatInfoArr[1];
                
                if(currCatTag.contains("I")){
                	//j > 0: currCellsArr[j] is the tag and name of each choice under this category
                    ArrayList<String> currChoiceTags = new ArrayList<>();
                    ArrayList<String> currChoiceNames = new ArrayList<>();
                    for (int j = 1; j < currCellsArr.length; j++) {
                        String tempChoiceInfoArr[] = currCellsArr[j].split(":");
                        currChoiceTags.add(tempChoiceInfoArr[0]);
                        currChoiceNames.add(tempChoiceInfoArr[1]);
                    }
                    CatWithChoices currCatWithChoice = new CatWithChoices(currCatTag, currCatName, currChoiceTags, currChoiceNames);
                    allInputCatWithChoices.add(currCatWithChoice);
                }               
            }
        } catch (Exception e) {
        }
        return allInputCatWithChoices;
    }
    
    public static ArrayList<CatWithChoices> getAllOutputCategories(File f) {

        //File f = new File(fileName);
        ArrayList<CatWithChoices> allOutputCatWithChoices = new ArrayList<>();

        try {
            FileInputStream is = new FileInputStream(f);
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet catSheet = wb.getSheetAt(0); //the first sheet gives the definition of all categories and choices
            ArrayList<String> allCatLines = read(catSheet);

            int startLineID = 3;//alternatively, search for the [START], and the next line is the starting point
            for (int i = startLineID; i < allCatLines.size(); i++) {
                String currLineStr = allCatLines.get(i);
                //if (currLineStr.startsWith("[END]")) {
                    //break;
                //}
                //else, split this line with ","
                String currCellsArr[] = currLineStr.split(",");

                //j = 0: currCellsArr[0] is the tag and name of the category
                String currCatInfoArr[] = currCellsArr[0].split(":");
                String currCatTag = currCatInfoArr[0];
                String currCatName = currCatInfoArr[1];
                
                if(currCatTag.contains("O")){
                	//j > 0: currCellsArr[j] is the tag and name of each choice under this category
                    ArrayList<String> currChoiceTags = new ArrayList<>();
                    ArrayList<String> currChoiceNames = new ArrayList<>();
                    for (int j = 1; j < currCellsArr.length; j++) {
                        String tempChoiceInfoArr[] = currCellsArr[j].split(":");
                        currChoiceTags.add(tempChoiceInfoArr[0]);
                        currChoiceNames.add(tempChoiceInfoArr[1]);
                    }
                    CatWithChoices currCatWithChoice = new CatWithChoices(currCatTag, currCatName, currChoiceTags, currChoiceNames);
                    allOutputCatWithChoices.add(currCatWithChoice);
                }               
            }
        } catch (Exception e) {
        }
        return allOutputCatWithChoices;
    }
    

    //get all raw test frames, i.e. exactly the test frame from the .xlsx file, without extended with "*" for N/A categories
    public static ArrayList<TestFrame> getAllRawTestFrames(File f) {
        //File f = new File(fileName);
        ArrayList<TestFrame> allRawTestFrames = new ArrayList<>();

        try {
            FileInputStream is = new FileInputStream(f);
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet testSheet = wb.getSheetAt(2); //the third sheet gives all complete test frames
            ArrayList<String> allTestLines = read(testSheet);

            //String a = new String();

            int startLineID = 2;//alternatively, search for the [START], and the next line is the starting point
            for (int i = startLineID; i < allTestLines.size(); i++) {
                String currLineStr = allTestLines.get(i);
                //if (currLineStr.startsWith("[END]")) {
                //    break;
                //}

                String currCellsArr[] = currLineStr.split(",");
                //j = 0: currCellsArr[0] is the test frame ID
                //int currTestID = Integer.parseInt(currCellsArr[0]);  
                //int currTestID = i-1;  
                //j > 0: currCellsArr[i] is the value of each choice in this test frame
                
                ArrayList<String> currInputChoiceValues = new ArrayList<>();
                ArrayList<String> currOutputChoiceValues = new ArrayList<>();
                
                //ArrayList<String> currChoiceValues = new ArrayList<>();
                
                for (int j = 1; j < currCellsArr.length; j++) {
                	if(currCellsArr[j].contains("I")){
                		currInputChoiceValues.add(currCellsArr[j]);                		
                	}
                	else if(currCellsArr[j].contains("O")){
                		currOutputChoiceValues.add(currCellsArr[j]);
                	}
                	else{
                		JOptionPane.showMessageDialog(null, "Choice Value Invalid! Line: "+String.valueOf(i)+". Choice Number: "+String.valueOf(j)+". ", null, JOptionPane.INFORMATION_MESSAGE);
                	}
                    //currChoiceValues.add(currCellsArr[j]);
                }
                TestFrame currTestFrame = new TestFrame(currInputChoiceValues,currOutputChoiceValues);
                allRawTestFrames.add(currTestFrame);
            }
        } catch (Exception e) {
        }
        return allRawTestFrames;

    }

    //reading stops at the first blank line
    private static ArrayList<String> read(XSSFSheet sheet) throws Exception {

        ArrayList<String> allLines = new ArrayList<>();
        try {

            int rowID = 0; //start to read in rows
            XSSFRow currRow = sheet.getRow(rowID);
            while (currRow != null) {
                int cellID = 0;
                XSSFCell currRowCell = currRow.getCell(cellID); //Read in all cells in this row. Note the type of this cell is String
                String currRowStr = currRowCell.toString();
                if(currRowStr.equals("[END]")){
                    break;
                }
                currRowCell = currRow.getCell(++cellID);
                while (currRowCell != null) {
                    currRowStr = currRowStr + "," + currRowCell.toString();
                    currRowCell = currRow.getCell(++cellID);
                }
                allLines.add(currRowStr);
                currRow = sheet.getRow(++rowID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allLines;

    }
}
