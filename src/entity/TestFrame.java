/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author xxie
 */
public class TestFrame {
    //private int testFrameID;//the testID starts from 1

    //note TestFrame can be used for concrete testframe or template test frame;
    //for concrete and raw test frame, the lenghts of all test frames may be different, but there is no "*" in the test frame, all choices are assigned with a choice value (tag);
    //for concrete and extended test frame, the lenghts of all test frames are fixed, i.e. the number of categories. The N/A category is filled by "*";
    //for template test frames, the lengths of all test frames are fixed, i.e. the number of categories. The N/A category is filled by "*" and the default category (not assigned value) is filled by "-": this is used in ReVisitGUI
    //N/A is a concrete value, denoted as "*" in a TestFrame; while "-" is a virtual value
	
    //private ArrayList<String> choiceValues; //just the tags of the choices   
    private ArrayList<String> inputChoiceValues;
    private ArrayList<String> outputChoiceValues;
    
    /*
    public TestFrame(ArrayList<String> values) {
        this.choiceValues = new ArrayList<>(values);   //just the tags of the choices   
    }*/
    
    public TestFrame(ArrayList<String> inputValues, ArrayList<String> outputValues){
    	this.inputChoiceValues = new ArrayList<>(inputValues);
    	this.outputChoiceValues = new ArrayList<>(outputValues);
    }

    /*
    public int getChoicesNum() {
        return this.choiceValues.size();
    }*/
    
    public int getInputChoiceNum() {
    	return this.inputChoiceValues.size();
    }
    
    public int getOutputChoiceNum() {
    	return this.outputChoiceValues.size();
    }

    /*
    public ArrayList<String> getAllChoiceValues() {//just the tags of the choices   
        return this.choiceValues;
    }*/

    public ArrayList<String> getAllInputChoiceValues(){
    	return this.inputChoiceValues;
    }
    
    public ArrayList<String> getAllOutputChoiceValues(){
    	return this.outputChoiceValues;
    }
    
    /*
    public String getOneChoiceValue(int index) {//just the tags of the choices   
        return this.choiceValues.get(index);
    }*/
    
    public String getOneInputChoiceValue(int index) {
    	return this.inputChoiceValues.get(index);
    }
    
    public String getOneOutputChoiceValue(int index) {
    	return this.outputChoiceValues.get(index);
    }
}
