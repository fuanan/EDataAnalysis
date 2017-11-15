/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataaccess.SourceDataReader;
import entity.CatWithChoices;
import entity.TestFrame;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author xxie
 */
public class InfoInitializer {

    //public String sourceFileName;
    private  File sourceDataFile;
    //private ArrayList<CatWithChoices> allCatWithChoices;//read-only
    private ArrayList<CatWithChoices> allInputCatWithChoices;
    private ArrayList<CatWithChoices> allOutputCatWithChoices;
    private ArrayList<TestFrame> allExtendedTestFrames;//read-only

    public InfoInitializer(File file) {
        
        this.sourceDataFile = file;
       
        //this.allCatWithChoices = new ArrayList<>();
        this.allExtendedTestFrames = new ArrayList<>();

        //this.allCatWithChoices = SourceDataReader.getAllCategories(this.sourceDataFile);
        this.allInputCatWithChoices = SourceDataReader.getAllInputCategories(this.sourceDataFile);
        this.allOutputCatWithChoices = SourceDataReader.getAllOutputCategories(this.sourceDataFile);
        
        //int expectedCatNum = this.allCatWithChoices.size(); //the number of all categories 
        int expectedInputCatNum = this.allInputCatWithChoices.size();//the number of all input categories 
        int expectedOutputCatNum = this.allOutputCatWithChoices.size();//the number of all output categories 
        

        ArrayList<TestFrame> allRawTestFrames = SourceDataReader.getAllRawTestFrames(this.sourceDataFile);
        //extend the rawTestFrames by using the information of all categories
        //
        for (int i = 0; i < allRawTestFrames.size(); i++) {
            TestFrame currRawTestFrame = allRawTestFrames.get(i);
            //int currTestID = currRawTestFrame.getTestFrameID();
            
            //ArrayList<String> currRawChoiceValues = currRawTestFrame.getAllChoiceValues();
            ArrayList<String> currRawInputChoiceValues = currRawTestFrame.getAllInputChoiceValues();
            ArrayList<String> currRawOutputChoiceValues = currRawTestFrame.getAllOutputChoiceValues();
            
            //int realCatNum = currRawChoiceValues.size();//category number of a complete test frame 
            int realInputCatNum = currRawInputChoiceValues.size();
            int realOutputCatNum = currRawOutputChoiceValues.size();
            
            //input choices
            ArrayList<String> currExtendedInputChoiceValues = new ArrayList<>();
            int k1 = 0;
            int j1 = 0; //k is the id for the real cat; while j is the id for the expected cat
            for (; j1 < expectedInputCatNum; j1 ++) {

                if (k1 == realInputCatNum) {//fill in the rest position with *
                    currExtendedInputChoiceValues.add("*");
                } else {
                    String expectedInputCatTag = this.allInputCatWithChoices.get(j1).getCatTag();  //I-1
                    String realInputChoice = currRawInputChoiceValues.get(k1);      //I-1a
                    
                    //String realCat = realInputChoice.substring(0, 1); 
                    String length = String.valueOf(expectedInputCatNum);                   
                    String realCat = realInputChoice.substring(0, 2 + length.length()); 
                    
                    if (realCat.equals(expectedInputCatTag)) {
                        currExtendedInputChoiceValues.add(realInputChoice);
                        k1 ++;
                    } else {
                        currExtendedInputChoiceValues.add("*");
                    }
                }
            }
            
            //output choices
            ArrayList<String> currExtendedOutputChoiceValues = new ArrayList<>();
            int k2 = 0;
            int j2 = 0; //k is the id for the real cat; while j is the id for the expected cat
            for (; j2 < expectedOutputCatNum; j2 ++) {

                if (k2 == realOutputCatNum) {//fill in the rest position with *
                    currExtendedOutputChoiceValues.add("*");
                } else {
                    String expectedOutputCatTag = this.allOutputCatWithChoices.get(j2).getCatTag();
                    String realOutputChoice = currRawOutputChoiceValues.get(k2);
                    //String realCat = realOutputChoice.substring(0, 1); //e.g. a1
                    String length = String.valueOf(expectedOutputCatNum);                   
                    String realCat = realOutputChoice.substring(0, 2 + length.length()); 
                    
                    if (realCat.equals(expectedOutputCatTag)) {
                        currExtendedOutputChoiceValues.add(realOutputChoice);
                        k2 ++;
                    } else {
                        currExtendedOutputChoiceValues.add("*");
                    }
                }
            }
            
            //TestFrame currExtendedTestFrame = new TestFrame(currExtendedChoiceValues);
            TestFrame currExtendedTestFrame = new TestFrame(currExtendedInputChoiceValues,currExtendedOutputChoiceValues);
            this.allExtendedTestFrames.add(currExtendedTestFrame);
                  
            /*
            ArrayList<String> currExtendedChoiceValues = new ArrayList<>();
            int k = 0;
            int j = 0; //k is the id for the real cat; while j is the id for the expected cat
            for (; j < expectedCatNum; j++) {

                if (k == realCatNum) {//fill in the rest position with *
                    currExtendedChoiceValues.add("*");
                } else {
                    String expectedCatTag = this.allCatWithChoices.get(j).getCatTag();
                    String realChoice = currRawChoiceValues.get(k);
                    String realCat = realChoice.substring(0, 1); //e.g. a1
                    if (realCat.equals(expectedCatTag)) {
                        currExtendedChoiceValues.add(realChoice);
                        k++;
                    } else {
                        currExtendedChoiceValues.add("*");
                    }
                }
            }
            TestFrame currExtendedTestFrame = new TestFrame(currExtendedChoiceValues);
            this.allExtendedTestFrames.add(currExtendedTestFrame);
            */
        }

    }

    public int getTestFrameNum() {
        return this.allExtendedTestFrames.size();
    }

    /*
    public int getCatNum() {
        return this.allCatWithChoices.size();
    }
    */
    
    public int getInputCatNum() {
    	return this.allInputCatWithChoices.size();
    }
    
    public int getOutputCatNum() {
    	return this.allOutputCatWithChoices.size();
    }

    /*
    public ArrayList<String> getAllChNameOfOneCat(int catIn) {
        return this.allCatWithChoices.get(catIn).getAllChoiceTagsAndNames();
    }
    */
    
    public ArrayList<String> getAllChNameOfOneInputCat(int catIn) {
        return this.allInputCatWithChoices.get(catIn).getAllChoiceTagsAndNames();
    }
    
    public ArrayList<String> getAllChNameOfOneOutputCat(int catIn) {
        return this.allOutputCatWithChoices.get(catIn).getAllChoiceTagsAndNames();
    }

    /*
    public String getOneChNameOfOneCat(int catIn, String chTag) {//get the choice name by choice tag, for one category        
        return this.allCatWithChoices.get(catIn).getChoiceNameByTag(chTag);

    }*/
    
    public String getOneChNameOfOneInputCat(int catIn, String chTag) {//get the choice name by choice tag, for one category        
        return this.allInputCatWithChoices.get(catIn).getChoiceNameByTag(chTag);

    }
    
    public String getOneChNameOfOneOutputCat(int catIn, String chTag) {//get the choice name by choice tag, for one category        
        return this.allOutputCatWithChoices.get(catIn).getChoiceNameByTag(chTag);
    }

    public ArrayList<TestFrame> getAllTestFrames() {
        return this.allExtendedTestFrames;
    }

    public TestFrame getOneTestFrame(int testID) {
        return this.allExtendedTestFrames.get(testID);
    }

    /*
    public ArrayList<CatWithChoices> getAllCatWithChoices() {
        return this.allCatWithChoices;
    }
    */
    
    public ArrayList<CatWithChoices> getAllInputCatWithChoices() {
    	return this.allInputCatWithChoices;
    }
    
    public ArrayList<CatWithChoices> getAllOutputCatWithChoices() {
    	return this.allOutputCatWithChoices;
    	
    }

    /*
    public ArrayList<String> getAllCatTagsAndNames() {
        ArrayList<String> allCats = new ArrayList<>();
        for (int i = 0; i < this.allCatWithChoices.size(); i++) {
            String currCatStr = this.allCatWithChoices.get(i).getCatTag() + " :" + this.allCatWithChoices.get(i).getCatName();
            allCats.add(currCatStr);
        }

        return allCats;
    }
    */
    
    public ArrayList<String> getAllInputCatTagsAndNames() {
        ArrayList<String> allInputCats = new ArrayList<>();
        for (int i = 0; i < this.allInputCatWithChoices.size(); i++) {
            String currInputCatStr = this.allInputCatWithChoices.get(i).getCatTag() + " :" + this.allInputCatWithChoices.get(i).getCatName();
            allInputCats.add(currInputCatStr);
        }

        return allInputCats;
    }
    
    public ArrayList<String> getAllOutputCatTagsAndNames() {
        ArrayList<String> allOutputCats = new ArrayList<>();
        for (int i = 0; i < this.allOutputCatWithChoices.size(); i++) {
            String currOutputCatStr = this.allOutputCatWithChoices.get(i).getCatTag() + " :" + this.allOutputCatWithChoices.get(i).getCatName();
            allOutputCats.add(currOutputCatStr);
        }

        return allOutputCats;
    }
    
    
    public String getSourceDataDir(){
        return this.sourceDataFile.getParent();
    }
    
    public File getSourceDataFile(){
        return this.sourceDataFile;
    }
}
