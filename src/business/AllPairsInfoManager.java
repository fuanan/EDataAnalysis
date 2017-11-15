/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataaccess.MRXMLOperator;
import entity.CatWithChoices;
import entity.PairInformation;
import entity.TestFrame;
import entity.GroupInformation;
import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author xxie
 */
public class AllPairsInfoManager {

    //private ArrayList<PairInformation> allPairsInfo;
    private ArrayList<ArrayList<ArrayList<PairInformation>>> allRawPairsInfo;
    private InfoInitializer inforInitializer;
    
    //private ArrayList<PairInformation> infoOfPairsFromDiffenentGroups;//
    private ArrayList<ArrayList<PairInformation>> infoOfPairsFromDiffenentGroups;//
    private ArrayList<ArrayList<PairInformation>> infoOfPairsFromSameGroups;//
    private IOCTFGroupManager ioctfGroupManager;
    
    //private ArrayList<String> nameOfGroupPairsWithOutputRelation;
    //private ArrayList<String>

    
//    public AllPairsInfoManager(InfoInitializer initializer) {
//        this.inforInitializer = initializer;
//        initializeAllPairInfo();
//        //initializeAllPairInfoNew();
//    }
    
    public AllPairsInfoManager(InfoInitializer initializer, IOCTFGroupManager groupmanager) {
        this.inforInitializer = initializer;
        this.ioctfGroupManager = groupmanager;        
        initializeAllPairInfoNew();        
    }
    
    
    public ArrayList<ArrayList<PairInformation>> getInfoOfPairsFromDifferentGroups(){
        return this.infoOfPairsFromDiffenentGroups;
    }
    
    public ArrayList<ArrayList<PairInformation>> getInfoOfPairsFromSameGroups(){
        return this.infoOfPairsFromSameGroups;
    }
    //Get one grouppair's information
    //
    //
    //
    //
    public boolean judgeDiffGroupOrSameGroup(String pairname){
        String a[] = pairname.split("<->");
        if(a[0].equals(a[1])){
            return true;
        }else {
            return false;
        }     
    }
           
    public ArrayList<String> getNamesOfGroupPairsWithOutputRelation(){//得到有output relation definition的group pair的名字
        ArrayList<String> namesOfGroupPairsWithOutputRelation = new ArrayList<String>();
        for(int i = 0; i<this.infoOfPairsFromDiffenentGroups.size(); i++){
            if((!(this.infoOfPairsFromDiffenentGroups.get(i).get(0).getOutputRelationDef().equals("-")))&&(!(this.infoOfPairsFromDiffenentGroups.get(i).get(0).getOutputRelationDef().equals("")))){
                namesOfGroupPairsWithOutputRelation.add(this.infoOfPairsFromDiffenentGroups.get(i).get(0).getGroupPairName());
            }
        }
        for(int j = 0; j<this.infoOfPairsFromSameGroups.size(); j++){
            if((!(this.infoOfPairsFromSameGroups.get(j).get(0).getOutputRelationDef().equals("-")))&&(!(this.infoOfPairsFromSameGroups.get(j).get(0).getOutputRelationDef().equals("")))){
                namesOfGroupPairsWithOutputRelation.add(this.infoOfPairsFromSameGroups.get(j).get(0).getGroupPairName());
            }
        }
        return namesOfGroupPairsWithOutputRelation;
    }
    
    public ArrayList<String> getNamesOfAllGroupPairs(){
        ArrayList<String> namesOfAllGroupPairs = new ArrayList<String>();
        for(int i = 0; i<this.infoOfPairsFromDiffenentGroups.size(); i++){
            namesOfAllGroupPairs.add(this.infoOfPairsFromDiffenentGroups.get(i).get(0).getGroupPairName());
        }
        for(int j = 0; j<this.infoOfPairsFromSameGroups.size(); j++){
            namesOfAllGroupPairs.add(this.infoOfPairsFromSameGroups.get(j).get(0).getGroupPairName());
        }
        return namesOfAllGroupPairs;
    }
    
    public ArrayList<String> getPairNamesOfOneGroupPair(String grouppairname){
        ArrayList<String> pairname = new ArrayList<String> ();
        if(judgeDiffGroupOrSameGroup(grouppairname)){//same group
            for(int i = 0; i< this.infoOfPairsFromSameGroups.size(); i++){
                if(this.infoOfPairsFromSameGroups.get(i).get(0).getGroupPairName().equals(grouppairname)){
                    for(int j = 0; j< this.infoOfPairsFromSameGroups.get(i).size();j++){
                        pairname.add(this.infoOfPairsFromSameGroups.get(i).get(j).getPairName());
                    }
                    return pairname;
                }
            }
            return null;
         }else{
            for(int i = 0; i< this.infoOfPairsFromDiffenentGroups.size(); i++){
                if(this.infoOfPairsFromDiffenentGroups.get(i).get(0).getGroupPairName().equals(grouppairname)) {
                    for(int j =0; j< this.infoOfPairsFromDiffenentGroups.get(i).size();j++){
                        pairname.add(this.infoOfPairsFromDiffenentGroups.get(i).get(j).getPairName());
                    }
                    return pairname;
                }
            }
            return null;
        }      
    }
    
    public ArrayList<String> getNamesOfPairsWithOutputRelation(ArrayList<String> grouppairnames){
        ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
        ArrayList<String> namesOfPairsWithOutputRelation = new ArrayList<String>();
        for(int i = 0; i<grouppairnames.size(); i++){
            temp.add(getPairNamesOfOneGroupPair(grouppairnames.get(i)));
        }
        for(int j = 0; j<temp.size();j++){
            for(int k = 0; k<temp.get(j).size();k++){
                namesOfPairsWithOutputRelation.add(temp.get(j).get(k));
            }
        }
        
        return namesOfPairsWithOutputRelation;
    }
    
    /*
    public ArrayList<String> getNamesOfPairsWithInputRelation(ArrayList<String> grouppairnames){
        ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
    }*/
      
    public String getOutputRelationOfOneGroupPair(String grouppairname) {
        if(judgeDiffGroupOrSameGroup(grouppairname)){//same group
            for(int i = 0; i<this.infoOfPairsFromSameGroups.size();i++){
                if(this.infoOfPairsFromSameGroups.get(i).get(0).getGroupPairName().equals(grouppairname)){
                    return this.infoOfPairsFromSameGroups.get(i).get(0).getOutputRelationDef();
                }               
            }
            return "-";
        }else{//different group
            for(int i = 0; i<this.infoOfPairsFromDiffenentGroups.size(); i++){
                if(this.infoOfPairsFromDiffenentGroups.get(i).get(0).getGroupPairName().equals(grouppairname)){
                    return this.infoOfPairsFromDiffenentGroups.get(i).get(0).getOutputRelationDef();
                }
                
            }
            return "-";
        }       
    }
    
    public String getHasMRInfoOfOneGroupPair(String grouppairname){
        if(judgeDiffGroupOrSameGroup(grouppairname)){//same group
            for(int i = 0; i<this.infoOfPairsFromSameGroups.size(); i++){
                if(this.infoOfPairsFromSameGroups.get(i).get(0).getGroupPairName().equals(grouppairname)){
                    return this.infoOfPairsFromSameGroups.get(i).get(0).getHasMRInfo();
                }
            }
            return "-";
        }else{//different group
            for(int i = 0; i< this.infoOfPairsFromDiffenentGroups.size(); i++){
                if(this.infoOfPairsFromDiffenentGroups.get(i).get(0).getGroupPairName().equals(grouppairname)){
                    return this.infoOfPairsFromDiffenentGroups.get(i).get(0).getHasMRInfo();
                }
            }
            return "-";
        }
    }
    //
    //
    //
    //
    //Finish getting one group's information
    
    
    //***Get one pair's information by pair's index*********
    public PairInformation getPairByPairName(String name){
    	String a[] = name.split("<->");
    	String tf1name[] = a[0].split(":");
    	String tf2name[] = a[1].split(":");
    	if(tf1name[0].equals(tf2name[0])){
    		for(int i = 0; i<this.infoOfPairsFromSameGroups.size(); i++){
    			if(tf1name[0].equals(infoOfPairsFromSameGroups.get(i).get(0).getGroupNameOfTestFrame1())){
    				for(int j = 0; j< this.infoOfPairsFromSameGroups.get(i).size(); j++){
    					if(name.equals(infoOfPairsFromSameGroups.get(i).get(j).getPairName())){
    						return infoOfPairsFromSameGroups.get(i).get(j);
    					}   					
    				}
    			}
    		}
    		return null;
    	}else{  //���Բ�ͬ��
    		for(int i = 0; i < this.infoOfPairsFromDiffenentGroups.size(); i++){
    			if(tf1name[0].equals(infoOfPairsFromDiffenentGroups.get(i).get(0).getGroupNameOfTestFrame1())){
    				for(int j = 0 ; j < this.infoOfPairsFromDiffenentGroups.get(i).size() ; j++){
    					if(name.equals(infoOfPairsFromDiffenentGroups.get(i).get(j).getPairName())){
    						return infoOfPairsFromDiffenentGroups.get(i).get(j);
    					}   					
    				}
    			}
    		}
    		return null;
    	}
    }

    /*
    public int getPairIDByPairName(String pairName) {//e.g. "1<->2"
        int id = -1;
        String a[] = pairName.split("<->");
        String reversePairName = a[1] + "<->" + a[0];
        for (int i = 0; i < this.allPairsInfo.size(); i++) {
            if (this.allPairsInfo.get(i).getPairName().equals(pairName) || this.allPairsInfo.get(i).getPairName().equals(reversePairName)) {
                id = i;
                break;
            }
        }
        return id; //if returned value is -1, then no match
    }//note it is not suggested to get the pair's information directly by name, since it maybe invalid name, which lead to no searching result
    */

    
    /*
    public String getPairNameByPairIndex(int index) {
        PairInformation currPair = this.allPairsInfo.get(index);
        return currPair.getPairName();
    }*/
    
    public TestFrame getTestFrame1ByPairName(String name){
    	PairInformation info = getPairByPairName(name);
    	String tf1name = info.getGroupNameOfTestFrame1();
    	int tf1id = info.getTf1ID();
    	GroupInformation groupinfo = this.ioctfGroupManager.getOneGroupByGroupName(tf1name);
    	TestFrame expectedTestFrame = groupinfo.getTestFrameByID(tf1id);
    	return expectedTestFrame;
    }
    
    public ArrayList<String>getTf1InputChoicesByPairName(String name){
        PairInformation currPair = getPairByPairName(name);
        String tf1GroupName = currPair.getGroupNameOfTestFrame1();
        int tf1id = currPair.getTf1ID();
        GroupInformation groupinfo = this.ioctfGroupManager.getOneGroupByGroupName(tf1GroupName);
        TestFrame expectedTestFrame = groupinfo.getTestFrameByID(tf1id);
        return expectedTestFrame.getAllInputChoiceValues();
    }

    public TestFrame getTestFrame2ByPairName(String name){
    	PairInformation info = getPairByPairName(name);
    	String tf2name = info.getGroupNameOfTestFrame2();
    	int tf2id = info.getTf2ID();
    	GroupInformation groupinfo = this.ioctfGroupManager.getOneGroupByGroupName(tf2name);
    	TestFrame expectedTestFrame = groupinfo.getTestFrameByID(tf2id);
    	return expectedTestFrame;
    }
    
    public ArrayList<String>getTf2InputChoicesByPairName(String name){
        PairInformation currPair = getPairByPairName(name);
        String tf2GroupName = currPair.getGroupNameOfTestFrame2();
        int tf2id = currPair.getTf2ID();
        GroupInformation groupinfo = this.ioctfGroupManager.getOneGroupByGroupName(tf2GroupName);
        TestFrame expectedTestFrame = groupinfo.getTestFrameByID(tf2id);
        return expectedTestFrame.getAllInputChoiceValues();
    }
    
    public int getTypeByPairName(String name){
    	String a[] = name.split("<->");
    	String tf1[] = a[0].split(":");
    	String tf2[] = a[1].split(":");
    	if(tf1[0].equals(tf2[0])){
    		return 2;
    	}else{
    		return 1;
    	}
    }
    
    public String getHasMRInfoByPairName(String name){
    	PairInformation currPair = getPairByPairName(name);
    	return currPair.getHasMRInfo();
    }
    
    public String getMRNameInfoByPairName(String name){
    	PairInformation currPair = getPairByPairName(name);
    	return currPair.getMRName();
    }
    
    public String getAffectInfoByPairName(String name){
    	PairInformation currPair = getPairByPairName(name);
    	return currPair.getAffectOrNot();
    }
    
    public int getMRIDByPairName(String name){
    	PairInformation currPair = getPairByPairName(name);
    	return currPair.getMRID();
    }
    
    public String getMRDefByPairName(String name){
    	PairInformation currPair = getPairByPairName(name);
    	return currPair.getMRDefinition();
    }
    
    public String getCreatedTimeByPairName(String name){
    	PairInformation currPair = getPairByPairName(name);
    	return currPair.getCreatedTime();
    }
    
    public String getModifiedTimeByPairName(String name){
    	PairInformation currPair = getPairByPairName(name);
    	return currPair.getModifiedTime();
    }
    
    public String getInputRelationDefByPairName(String name){
        PairInformation currPair = getPairByPairName(name);
        return currPair.getInputRelationDef();
    }
    
    public String getOutputRelationDefByPairName(String name){
        PairInformation currPair = getPairByPairName(name);
        return currPair.getOutputRelationDef();
    }
    //***Finish of getting the pair's information 

    //***Get one pair's information by pair's index*********
    
    public void setHasMRInfoByPairName(String name,String newhasMRInfo){
    	PairInformation currPair = getPairByPairName(name);
    	currPair.setNewHasMRInfo(newhasMRInfo);  	
    }
    
    public void setMRNameByPairName(String name, String newMRName) {
    	PairInformation currPair = getPairByPairName(name);
    	currPair.setNewMRName(newMRName);
    }
    
    public void setAffectInfoByPairName(String name, String newAffectInfo) {
    	PairInformation currPair = getPairByPairName(name);
    	currPair.setNewAffectInfo(newAffectInfo);
    }
    
    public void setMRIDByPairName(String name,int newMRID) {
    	PairInformation currPair = getPairByPairName(name);
    	currPair.setNewMRID(newMRID);
    }

    public void setMRDefByPairName(String name,String newMRDef) {
    	PairInformation currPair = getPairByPairName(name);
    	currPair.setNewMRDefinition(newMRDef);
    }

    
    public void setCreatedTimeByPairName(String name,String newCreatedTime) {
    	PairInformation currPair = getPairByPairName(name);
    	currPair.setNewCreatedTime(newCreatedTime);
    }

    public void setModifiedTimeByPairName(String name, String newModifiedTime) {
    	PairInformation currPair = getPairByPairName(name);
    	currPair.setNewModifiedTime(newModifiedTime);
    }
    
    public void setOutputRelationDefByPairName(String name,String newOutputRelation) {
        PairInformation currPair = getPairByPairName(name);
        currPair.setNewOutputRelationDef(newOutputRelation);
    }
    
    public void setInputRelationDefByPairName(String name,String newInputRelation) {
        PairInformation currPair = getPairByPairName(name);
        currPair.setNewInputRelationDef(newInputRelation);
    }
    //***Finish of updating the information of a pair*******

    //***Get information from informInitializer**************  
    public int getInputCatNum() {
        return this.inforInitializer.getInputCatNum();
    }
    
    public int getOutputCatNum() {
        return this.inforInitializer.getOutputCatNum();
    }
    
    public String getExprDir() {
        return this.inforInitializer.getSourceDataDir();
    }
    
    public ArrayList<CatWithChoices> getAllInputCatWithChoices() {
    	return this.inforInitializer.getAllInputCatWithChoices();
    }
    
    public ArrayList<CatWithChoices> getAllOutputCatWithChoices() {
    	return this.inforInitializer.getAllOutputCatWithChoices();
    }

    public ArrayList<String> getAllInputCatsWithNoChoices() {
        return this.inforInitializer.getAllInputCatTagsAndNames();
    }
    
    public ArrayList<String> getAllOutputCatsWithNoChoices() {
        return this.inforInitializer.getAllOutputCatTagsAndNames();
    }

    public ArrayList<String> getAllChNameByInputCatIndex(int catIn) {
        return this.inforInitializer.getAllChNameOfOneInputCat(catIn);
    }
    
    public ArrayList<String> getAllChNameByOutputCatIndex(int catIn) {
        return this.inforInitializer.getAllChNameOfOneOutputCat(catIn);
    }
    
    public String getOneChNameByInputCatIndex(int catIn, String chTag) {//return the name of the choice with tag "chTag", under category "catIn"
        return this.inforInitializer.getOneChNameOfOneInputCat(catIn, chTag);
    }
    
    public String getOneChNameByOutputCatIndex(int catIn, String chTag) {//return the name of the choice with tag "chTag", under category "catIn"
        return this.inforInitializer.getOneChNameOfOneOutputCat(catIn, chTag);
    }
   

    public int getTestFrameNum() {
        return this.inforInitializer.getTestFrameNum();
    }
    //***Finsih of getting information from informInitializer

    //***Get eligible pairs***********
    /*
    public int getEligibleByType(int type, int ka, int kb, ArrayList<Integer> allEligible, ArrayList<Integer> swap, ArrayList<Integer> selCats) {//return the the eigible pair's index in allPairsInfo  
        //When initialize this frame, read in the most updated "MR.csv"
        //Filter out the required pairs, i.e. typeA, typeB or typeC
        //e.g. 1<->2,2,0,1,Yes,MR1,o1=o2, i.e. pair,ka,kb,type,HasMR?,MRName,Definition
        ArrayList<Integer> eligibleIndexOriOrder = new ArrayList<>();

        if (type != -1) {//user has specified a type and a number for the difference
            for (int i = 0; i < this.allPairsInfo.size(); i++) {
                if ((this.allPairsInfo.get(i).getType() == type) && (this.allPairsInfo.get(i).getKaValue() == ka) && (this.allPairsInfo.get(i).getKbValue() == kb)) {
                    eligibleIndexOriOrder.add(i);
                    swap.add(0);//never swap in this case
                }
            }
        } else if (type == -1) {//user did not specify the type, system will display all pairs
            for (int i = 0; i < this.allPairsInfo.size(); i++) {
                eligibleIndexOriOrder.add(i);
                swap.add(0);//never swap in this case
            }
        }
        
        
        //filter the eligibleIndexOriOrder according to the selected categories
        int selSum = 0;
        for (int i = 0; i<selCats.size(); i++){
            selSum += selCats.get(i);
        }
        if (selSum > 0) {//selSum >0 means user has selected interested categories, then do filtering
            for (int i = 0; i < eligibleIndexOriOrder.size(); i++) {
                int currPairId = eligibleIndexOriOrder.get(i);
                ArrayList<String> tf1Choices = this.getTf1ChoicesByPairIndex(currPairId);
                ArrayList<String> tf2Choices = this.getTf2ChoicesByPairIndex(currPairId);
                boolean toBeDeleted = false;
                for (int j = 0; j < selCats.size(); j++) {
                    if (selCats.get(j) > 0) {
                        if (tf1Choices.get(j).equals("*") && tf2Choices.get(j).equals("*")) {//which means that this category is missing in this pair
                            toBeDeleted = true;
                            break;
                        } else {
                            if (selCats.get(j) == 2) {//need to further check whether these two choices from tf1 and tf2 are the same
                                if (tf1Choices.get(j).equals(tf2Choices.get(j))) {
                                    toBeDeleted = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (toBeDeleted) {
                    eligibleIndexOriOrder.remove(i);
                    i--;
                }
            }
        }

        //separate eligibleIndexOriOrder into two part, the first part is the previously investigated pairs, the second part is the non-investigate pairs
        ArrayList<Integer> doneOriOrder = new ArrayList<>();
        ArrayList<Integer> notDoneOriOrder = new ArrayList<>();

        separateEligibleIndexList(eligibleIndexOriOrder, doneOriOrder, notDoneOriOrder);

        ArrayList<Integer> notDoneRanOrder = new ArrayList<>();
        sortIndexRandomly(notDoneOriOrder, notDoneRanOrder);
        //merge doneOriOrder and notDoneRanOrder into allEligible
        allEligible.addAll(doneOriOrder);
        allEligible.addAll(notDoneRanOrder);

        //return the starting pos: note not the pairIndex
        if (doneOriOrder.size() < eligibleIndexOriOrder.size()) {//not finished yet, start from the first non-investigated one
            return doneOriOrder.size();
        } else {//doneOriOrder.size()==eligibleIndexOriOrder.size(), finished alreay, then stay at tha last one; or no eligible pair, then return -1
            return doneOriOrder.size() - 1;
        }

    }
    */

    
    /*
    //this is for adjust feature, target at both investigated and non-investigated pairs
    public int getEligibleByCondition(TestFrame reqTf1, TestFrame reqTf2, ArrayList<Integer> allEligible, ArrayList<Integer> swap) {
        ArrayList<Integer> eligibleIndexOriOrder = new ArrayList<>();
        ArrayList<Integer> doneOriOrder = new ArrayList<>();
        ArrayList<Integer> notDoneOriOrder = new ArrayList<>();
        ArrayList<Integer> notDoneRanOrder = new ArrayList<>();

        for (int i = 0; i < this.allPairsInfo.size(); i++) {
            PairInformation tempPair = this.allPairsInfo.get(i);
            TestFrame tempTf1 = this.inforInitializer.getOneTestFrame(tempPair.getTf1ID());
            TestFrame tempTf2 = this.inforInitializer.getOneTestFrame(tempPair.getTf2ID());
            if (isPairMatchingReq(tempTf1, reqTf1, tempTf2, reqTf2, "Contain")) {
                eligibleIndexOriOrder.add(i);
                swap.add(0);
            } else if (isPairMatchingReq(tempTf2, reqTf1, tempTf1, reqTf2, "Contain")) {
                eligibleIndexOriOrder.add(i);
                //swap.add(1);
                swap.add(0);//do not swap
            }
        }
        //separate eligibleIndexOriOrder into two part, the first part is the previously investigated pairs, the second part is the non-investigate pairs
        separateEligibleIndexList(eligibleIndexOriOrder, doneOriOrder, notDoneOriOrder);
        sortIndexRandomly(notDoneOriOrder, notDoneRanOrder);
        //merge doneOriOrder and notDoneRanOrder into allEligible
        allEligible.addAll(doneOriOrder);
        allEligible.addAll(notDoneRanOrder);
        
        if (allEligible.size() > 0) {
            if (doneOriOrder.size() < eligibleIndexOriOrder.size()) {//not finished yet, start from the first non-investigated one
                return doneOriOrder.size();
            } else {//doneOriOrder.size()==eligibleIndexOriOrder.size(): finished alreay, then stay at the last one
                return doneOriOrder.size() - 1;
            }
        } else {
            return - 1;//means no suitable pair
        }

    }
    */

//    
//    //this is for revisit feature, target at investigated pairs
//    public int getEligibleByCondition(String hasMRConditionStr, String diffConditionStr, TestFrame reqTf1, TestFrame reqTf2, ArrayList<Integer> allEligible, ArrayList<Integer> swap) {
//        //composition of the two conditions
//        int conditionHasMR;
//        int conditionDiff;
//        int combination;
//        if (!hasMRConditionStr.equals("-")) {
//            conditionHasMR = 10;
//        } else {
//            conditionHasMR = 0;
//        }
//        //if (!tf1.equals(tf2)) {
//        if (!diffConditionStr.equals("-")) {
//            conditionDiff = 1;
//        } else {
//            conditionDiff = 0;
//        }
//        combination = conditionHasMR + conditionDiff;
//
//
//        //ArrayList<Integer> eligibleIndexOriOrder = new ArrayList<>();//for case 0 and case 1
//        //ArrayList<Integer> doneOriOrder = new ArrayList<>();//for case 0 and case 1
//        //ArrayList<Integer> notDoneOriOrder = new ArrayList<>();//for case 0 and case 1
//        //ArrayList<Integer> notDoneRanOrder = new ArrayList<>();//for case 0 and case 1
//
//        //start to search for the eligible pairs
//        //ArrayList<Integer> eligibleIndexOriOrder = new ArrayList<Integer>();
//        //there are four cases in the condition: 00, 01, 10, 11
//        switch (combination) {
//            case 0: // hasMRConditionStr = "-"; diffConditionStr = "-", then display all previously investigated pairs 
//                for (int i = 0; i < this.allPairsInfo.size(); i++) {
//                    if (!this.allPairsInfo.get(i).getHasMRInfo().equals("-")) {
//                        allEligible.add(i);
//                        swap.add(0);
//                    }
//                }
//                /*This is to display all pairs, no matter whether it has been investigated or not
//                 for (int i = 0; i < this.allPairsInfo.size(); i++) {
//                 eligibleIndexOriOrder.add(i);
//                 swap.add(0);
//                 }
//                 //separate eligibleIndexOriOrder into two part, the first part is the previously investigated pairs, the second part is the non-investigate pairs
//                 separateEligibleIndexList(eligibleIndexOriOrder, doneOriOrder, notDoneOriOrder);
//          
//                 sortIndexRandomly(notDoneOriOrder, notDoneRanOrder);
//                 //merge doneOriOrder and notDoneRanOrder into allEligible
//                 allEligible.addAll(doneOriOrder);
//                 allEligible.addAll(notDoneRanOrder);
//                 */
//                break;
//
//            case 1: // hasMRConditionStr = "-"; diffConditionStr != "-", then, display pairs with the specified difference in specified "level" (exact or contain),                            
//                for (int i = 0; i < this.allPairsInfo.size(); i++) {
//                    PairInformation tempPair = this.allPairsInfo.get(i);
//                    TestFrame tempTf1 = this.inforInitializer.getOneTestFrame(tempPair.getTf1ID());
//                    TestFrame tempTf2 = this.inforInitializer.getOneTestFrame(tempPair.getTf2ID());
//
//                    if (!this.allPairsInfo.get(i).getHasMRInfo().equals("-")) {
//                        if (isPairMatchingReq(tempTf1, reqTf1, tempTf2, reqTf2, diffConditionStr)) {
//                            allEligible.add(i);
//                            swap.add(0);
//                        } else if (isPairMatchingReq(tempTf2, reqTf1, tempTf1, reqTf2, diffConditionStr)) {
//                            allEligible.add(i);
//                            //swap.add(1);
//                            swap.add(0);
//                        }
//                    }
//                }
//                /*This is to display all pairs, no matter whether it has been investigated or not
//                 for (int i = 0; i < this.allPairsInfo.size(); i++) {
//                 PairInformation tempPair = this.allPairsInfo.get(i);
//                    TestFrame tempTf1 = this.inforInitializer.getOneTestFrame(tempPair.getTf1ID());
//                    TestFrame tempTf2 = this.inforInitializer.getOneTestFrame(tempPair.getTf2ID());
//                    if (isPairMatchingReq(tempTf1, reqTf1, tempTf2, reqTf2, diffConditionStr)) {
//                        eligibleIndexOriOrder.add(i);
//                        swap.add(0);
//                    } else if (isPairMatchingReq(tempTf2, reqTf1, tempTf1, reqTf2, diffConditionStr)) {
//                        eligibleIndexOriOrder.add(i);
//                        swap.add(1);
//                    }
//                }
//                //separate eligibleIndexOriOrder into two part, the first part is the previously investigated pairs, the second part is the non-investigate pairs
//                separateEligibleIndexList(eligibleIndexOriOrder, doneOriOrder, notDoneOriOrder);
//                sortIndexRandomly(notDoneOriOrder, notDoneRanOrder);
//                //merge doneOriOrder and notDoneRanOrder into allEligible
//                allEligible.addAll(doneOriOrder);
//                allEligible.addAll(notDoneRanOrder);
//                */
//                break;
//
//            case 10:  // hasMRConditionStr != "-"; level = "-", then no need to consider the differenc between the pairs, only display pairs with the specified "HasMR" (Yes/No/To be decided later)
//                for (int i = 0; i < this.allPairsInfo.size(); i++) {
//                    if (this.allPairsInfo.get(i).getHasMRInfo().equals(hasMRConditionStr)) {
//                        allEligible.add(i);
//                        swap.add(0);
//                    }
//                }
//                break;
//
//            case 11:// hasMRConditionStr != "-"; diffConditionStr != "-", then display pairs with the specified "HasMR" (Yes/No/To be decided later)
//                for (int i = 0; i < this.allPairsInfo.size(); i++) {
//                    PairInformation tempPair = this.allPairsInfo.get(i);
//                    if (this.allPairsInfo.get(i).getHasMRInfo().equals(hasMRConditionStr)) {
//                        TestFrame tempTf1 = this.inforInitializer.getOneTestFrame(tempPair.getTf1ID());
//                        TestFrame tempTf2 = this.inforInitializer.getOneTestFrame(tempPair.getTf2ID());
//                        if (isPairMatchingReq(tempTf1, reqTf1, tempTf2, reqTf2, diffConditionStr)) {
//                            allEligible.add(i);
//                            swap.add(0);
//                        } else if (isPairMatchingReq(tempTf2, reqTf1, tempTf1, reqTf2, diffConditionStr)) {
//                            allEligible.add(i);
//                            //swap.add(1);
//                            swap.add(0);
//                        }
//                    }
//                }
//                break;
//
//            default:
//                break;
//
//        }
//
//        if (allEligible.size() > 0) {
//            return 0;//return the starting pos: 0 
//            /*
//            if ((combination == 0) || (combination == 1)) {
//                if (doneOriOrder.size() < eligibleIndexOriOrder.size()) {//not finished yet, start from the first non-investigated one
//                    return doneOriOrder.size();
//                } else {//doneOriOrder.size()==eligibleIndexOriOrder.size(): finished alreay, then stay at tha last one; or no eligible pair, then return -1
//                    return doneOriOrder.size() - 1;
//                }
//            } else {// 10 or 11
//                return 0;//return the starting pos: 0 
//            }
//            */
//        } else {
//            return - 1;//means no suitable pair
//        }
//    }

//    public int getEligibleByHasMRs(ArrayList<Integer> allEligible, String hasMRStr, ArrayList<Integer> swap) {
//        for (int i = 0; i < this.allPairsInfo.size(); i++) {
//            if (this.allPairsInfo.get(i).getHasMRInfo().equals(hasMRStr)) {
//                allEligible.add(i);
//                swap.add(0);
//            }
//        }
//        //return the starting pos: 0 or -1(means no suitable pair)
//        if (allEligible.size() > 0) {
//            return 0;
//        } else {
//            return - 1;
//        }
//    }
//
//    public int getEligibleByHasMRs(ArrayList<Integer> allEligible, String hasMRStr) {//for the case that no need to consider the swapping
//        for (int i = 0; i < this.allPairsInfo.size(); i++) {
//            if (this.allPairsInfo.get(i).getHasMRInfo().equals(hasMRStr)) {
//                allEligible.add(i);
//            }
//        }
//        //return the starting pos: 0 or -1(means no suitable pair)
//        if (allEligible.size() > 0) {
//            return 0;
//        } else {
//            return - 1;
//        }
//    }
//
//    public int getEligibleByID(ArrayList<Integer> allEligible, int mrID, ArrayList<Integer> swap) {
//        for (int i = 0; i < this.allPairsInfo.size(); i++) {
//            if (this.allPairsInfo.get(i).getMRID() == mrID) {
//                allEligible.add(i);
//                swap.add(0);
//                break; //note, only one in allEligible
//            }
//        }
//        //return the starting pos: 0 or -1(means no suitable pair)
//        if (allEligible.size() > 0) {//==1
//            return 0;
//        } else {
//            return - 1;
//        }
//    }
//
//    public int getEligibleBySimilarChanges(ArrayList<Integer> allEligible, int refPairID) {
//        //get the different categories of the referred pair
//        ArrayList<Integer> refPairDiffCatIDs = getDiffCat(refPairID);
//
//        ArrayList<Integer> unsortedAllEligible = new ArrayList<>();
//        ArrayList<Integer> diffCatsNum = new ArrayList<>();//same size as unsortedAllEligible, records the corresponding number of different categories
//        for (int i = 0; i < this.allPairsInfo.size(); i++) {
//            if (i == refPairID) {//do not check the current pair
//                continue;
//            }
//            String tempHasMRInfo = this.getHasMRInfoByPairIndex(i);
//            if (!tempHasMRInfo.equals("-")) {//note must be previously investigated pairs
//                //get the different categoris of the temp pair
//                ArrayList<Integer> tempPairDiffCatIDs = getDiffCat(i);
//                int tempDiffCatsNum = tempPairDiffCatIDs.size();
//                if (firstCoverSecond(refPairDiffCatIDs, tempPairDiffCatIDs)) {//
//                    unsortedAllEligible.add(i);
//                    diffCatsNum.add(tempDiffCatsNum);
//                }
//            }
//        }
//
//        //sort according the relevant
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int i = 0; i < unsortedAllEligible.size(); i++) {
//            map.put(unsortedAllEligible.get(i), diffCatsNum.get(i));
//        }
//        ArrayList<Map.Entry<Integer, Integer>> infoIds = new ArrayList<>(map.entrySet());
//        Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Integer>>() {
//            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
//                return (o2.getValue() - o1.getValue());
//                //return (o1.getKey()).toString().compareTo(o2.getKey());
//            }
//        });
//        for (int i = 0; i < infoIds.size(); i++) {
//            allEligible.add(infoIds.get(i).getKey());
//        }
//
//        //return the starting pos: 0 or -1(means no suitable pair)
//        if (allEligible.size() > 0) {
//            return 0;
//        } else {
//            return - 1;
//        }
//    }
//
//    public int removeRemainEligiblePairs(ArrayList<Integer> allEligible, ArrayList<Integer> removedPairs) {//delete all elements in current eligible pairs from the next position of the given position, i.e. the given position becomes the last pair.
//        //find the first non-investigated pair
//        int firstNonInvPairID = -1;
//        for(int i = 0; i<allEligible.size(); i++){
//            int pairID = allEligible.get(i);
//            String currHasMRInfo = getHasMRInfoByPairIndex(pairID);
//            if(currHasMRInfo.equals("-")){
//                firstNonInvPairID = i;
//                break;
//            }
//        }
//        
//        if(firstNonInvPairID != -1){
//             int numToBeRemoved = allEligible.size() - firstNonInvPairID;
//        int indexToBeRemoved = firstNonInvPairID;
//        for (int i = 0; i < numToBeRemoved; i++) {
//            removedPairs.add(allEligible.get(indexToBeRemoved));
//            allEligible.remove(indexToBeRemoved);//note since the array is keeping updated, we need to fix the indexToBeRemoved
//        }
//        }else{//all pairs have been investigated
//            //remove nothing
//        }
//        return allEligible.size()-1; //will be -1 if all pairs have been removed
//    }
//    
//    public void recoverRemovedPairs(ArrayList<Integer> allEligible, ArrayList<Integer> removedPairs){
//        allEligible.addAll(removedPairs);
//        removedPairs.clear();
//    }
//
//    //***Finish of getting eligible pairs
//    //***Getting summary of current MR information for all pairs***
//    /*
//    public int getMRNum() {
//        int currMRNum = 0;
//        for (int i = 0; i < this.allPairsInfo.size(); i++) {
//            if (this.allPairsInfo.get(i).getHasMRInfo().equals("Yes")) {
//                currMRNum++;
//            }
//        }
//        return currMRNum;
//    }//���������ʾgetAllMRNum()
//    */
//    
    public int getMRNumOfPairsFromDifferentGroups() {
    	int currMRNum = 0;
    	for(int i = 0; i < this.infoOfPairsFromDiffenentGroups.size(); i++) {
    		ArrayList<PairInformation> currGroupsPairInfo = this.infoOfPairsFromDiffenentGroups.get(i);
    		for(int j = 0; j < currGroupsPairInfo.size(); j++){
    			if(currGroupsPairInfo.get(j).getHasMRInfo().equals("Yes")){
    				currMRNum ++;
    			}
    		}
    	}
    	return currMRNum; 	
    }
    
    public int getMRNumOfParisFromSameGroups() {
    	int currMRNum = 0;
    	for(int i = 0; i < this.infoOfPairsFromSameGroups.size(); i++) {
    		ArrayList<PairInformation> currGroupsPairInfo = this.infoOfPairsFromSameGroups.get(i);
    		for(int j = 0; j < currGroupsPairInfo.size(); j++){
    			if(currGroupsPairInfo.get(j).getHasMRInfo().equals("Yes")) {
    				currMRNum ++;
    			}
    		}
    	}
    	return currMRNum;
    }
    
    public int getAllMRNum() {
    	int mrNumOfPairsFromDifferentGroups = this.getMRNumOfPairsFromDifferentGroups();
    	int mrNumOfPairsFromSameGroups = this.getMRNumOfParisFromSameGroups();
    	return mrNumOfPairsFromDifferentGroups + mrNumOfPairsFromSameGroups;
    }
    
    
    public int getNumOfPairsWithInputRelationDefined(){
        int numOfPairsWithInputRelationDefined = 0;
    	for(int i = 0; i < this.infoOfPairsFromDiffenentGroups.size(); i++) {
    		ArrayList<PairInformation> currGroupsPairInfo = this.infoOfPairsFromDiffenentGroups.get(i);
    		for(int j = 0; j < currGroupsPairInfo.size(); j++){
    			if(currGroupsPairInfo.get(j).getHasMRInfo().equals("Yes")&&(!currGroupsPairInfo.get(j).getInputRelationDef().equals("-"))){
    				numOfPairsWithInputRelationDefined ++;
    			}
    		}
    	}
        for(int i = 0; i < this.infoOfPairsFromSameGroups.size(); i++) {
    		ArrayList<PairInformation> currGroupsPairInfo = this.infoOfPairsFromSameGroups.get(i);
    		for(int j = 0; j < currGroupsPairInfo.size(); j++){
    			if(currGroupsPairInfo.get(j).getHasMRInfo().equals("Yes")&&(!currGroupsPairInfo.get(j).getInputRelationDef().equals("-"))) {
    				numOfPairsWithInputRelationDefined ++;
    			}
    		}
    	}
        return numOfPairsWithInputRelationDefined;
    }
    
        
    public ArrayList<ArrayList<String>> getMRsDefined(){
        
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
        
        for(int i = 0; i < this.infoOfPairsFromDiffenentGroups.size(); i ++){
            ArrayList<PairInformation> currGroupsPairInfo = this.infoOfPairsFromDiffenentGroups.get(i);
            for(int j = 0; j < currGroupsPairInfo.size(); j ++){
                if(currGroupsPairInfo.get(j).getHasMRInfo().equals("Yes")&&(!currGroupsPairInfo.get(j).getInputRelationDef().equals("-"))){
                    //获得输入，获得输出，获得mr，获得Modified Time,获得 Created Time,获得 Output 与Input
                    String groupPairName = currGroupsPairInfo.get(j).getGroupPairName();
                    String inputRelation = currGroupsPairInfo.get(j).getInputRelationDef();
                    String outputRelation = currGroupsPairInfo.get(j).getOutputRelationDef();
                    String mrDef = currGroupsPairInfo.get(j).getMRDefinition();
                    String modifiedTime = currGroupsPairInfo.get(j).getModifiedTime();
                    String createdTime = currGroupsPairInfo.get(j).getCreatedTime();
                    
                    String a[] = groupPairName.split("<->");
                    String group1name = a[0];
                    String group2name = a[1];
                    
                    //output categories and choices
                    ArrayList<String> allOutputCat = getAllOutputCatsWithNoChoices();
                    int outputCatNum = allOutputCat.size();
                    ArrayList<String> group1OutputChoices = this.ioctfGroupManager.getOneGroupByGroupName(group1name).getOutputChoiceValuesOfGroup();
                    ArrayList<String> group2OutputChoices = this.ioctfGroupManager.getOneGroupByGroupName(group2name).getOutputChoiceValuesOfGroup();
                    
                    ArrayList<String> g1OutChoiceNames = new ArrayList<String>();
                    ArrayList<String> g2OutChoiceNames = new ArrayList<String>();
                    
                    for(int k = 0; k < outputCatNum; k ++){
                        String group1Choicei = group1OutputChoices.get(k);
                        if(group1Choicei.equals("*")){
                            g1OutChoiceNames.add(group1Choicei);
                        }else{
                            String content1 = group1Choicei + ":" + getOneChNameByOutputCatIndex(k,group1Choicei);
                            g1OutChoiceNames.add(content1);
                        }

                        String group2Choicei = group2OutputChoices.get(k);
                        if(group2Choicei.equals("*")){
                            g2OutChoiceNames.add(group2Choicei);
                        }else{
                            String content2 = group2Choicei + ":" + getOneChNameByOutputCatIndex(k,group2Choicei);
                            g2OutChoiceNames.add(content2);
                        }
                    }
                    
                    //input categories and choices 
                    ArrayList<String> allInputCat = getAllInputCatsWithNoChoices();
                    int inputCatNum = getInputCatNum();
                    
                    ArrayList<String> tf1Choices = getTf1InputChoicesByPairName(groupPairName);
                    ArrayList<String> tf2Choices = getTf2InputChoicesByPairName(groupPairName);
                    
                    ArrayList<String> g1InChoiceNames = new ArrayList<String>();
                    ArrayList<String> g2InChoiceNames = new ArrayList<String>();
                    
                    for(int k = 0; k <inputCatNum; k ++){
                        String tf1Choicei = tf1Choices.get(k);
                        if(tf1Choicei.equals("*")){
                            g1InChoiceNames.add(tf1Choicei);
                        }else{
                            String content1 = tf1Choicei + ":" + getOneChNameByInputCatIndex(k,tf1Choicei);
                            g1InChoiceNames.add(content1);
                        }  
                        
                        String tf2Choicei = tf2Choices.get(k);
                        if(tf2Choicei.equals("*")){
                            g2InChoiceNames.add(tf2Choicei);
                        }else{
                            String content2 = tf2Choicei + ":" + getOneChNameByInputCatIndex(k,tf2Choicei);
                            g2InChoiceNames.add(content2);
                        }
                    }
                    
                    
                    ArrayList<String> temp = new ArrayList<String>();
                    temp.add(groupPairName);
                    temp.add(inputRelation);
                    temp.add(outputRelation);
                    temp.add(mrDef);
                    temp.add(modifiedTime);
                    temp.add(createdTime);
                    for(int m = 0; m < g1OutChoiceNames.size(); m ++){
                        temp.add(g1OutChoiceNames.get(m) + " -> " + g2OutChoiceNames.get(m));
                    }
                    
                    for(int m = 0; m <g1InChoiceNames.size(); m ++){
                        temp.add(g1InChoiceNames.get(m) + " -> " + g2InChoiceNames.get(m));
                    }
                    results.add(temp);
                }
            }
        }
        
        
        for(int i = 0; i <this.infoOfPairsFromSameGroups.size(); i ++){
            ArrayList<PairInformation> currGroupsPairInfo = this.infoOfPairsFromSameGroups.get(i);
            for(int j = 0; j < currGroupsPairInfo.size(); j ++){
                if(currGroupsPairInfo.get(j).getHasMRInfo().equals("Yes")&&(!currGroupsPairInfo.get(j).getInputRelationDef().equals("-"))){
                    //获得输入，获得输出，获得mr，获得Modified Time,获得 Created Time,获得 Output 与Input
                    String groupPairName = currGroupsPairInfo.get(j).getGroupPairName();
                    String inputRelation = currGroupsPairInfo.get(j).getInputRelationDef();
                    String outputRelation = currGroupsPairInfo.get(j).getOutputRelationDef();
                    String mrDef = currGroupsPairInfo.get(j).getMRDefinition();
                    String modifiedTime = currGroupsPairInfo.get(j).getModifiedTime();
                    String createdTime = currGroupsPairInfo.get(j).getCreatedTime();
                    
                    String a[] = groupPairName.split("<->");
                    String group1name = a[0];
                    String group2name = a[1];
                    
                    //output categories and choices
                    ArrayList<String> allOutputCat = getAllOutputCatsWithNoChoices();
                    int outputCatNum = allOutputCat.size();
                    ArrayList<String> group1OutputChoices = this.ioctfGroupManager.getOneGroupByGroupName(group1name).getOutputChoiceValuesOfGroup();
                    ArrayList<String> group2OutputChoices = this.ioctfGroupManager.getOneGroupByGroupName(group2name).getOutputChoiceValuesOfGroup();
                    
                    ArrayList<String> g1OutChoiceNames = new ArrayList<String>();
                    ArrayList<String> g2OutChoiceNames = new ArrayList<String>();
                    
                    for(int k = 0; k < outputCatNum; k ++){
                        String group1Choicei = group1OutputChoices.get(k);
                        if(group1Choicei.equals("*")){
                            g1OutChoiceNames.add(group1Choicei);
                        }else{
                            String content1 = group1Choicei + ":" + getOneChNameByOutputCatIndex(k,group1Choicei);
                            g1OutChoiceNames.add(content1);
                        }

                        String group2Choicei = group2OutputChoices.get(k);
                        if(group2Choicei.equals("*")){
                            g2OutChoiceNames.add(group2Choicei);
                        }else{
                            String content2 = group2Choicei + ":" + getOneChNameByOutputCatIndex(k,group2Choicei);
                            g2OutChoiceNames.add(content2);
                        }
                    }
                    
                    //input categories and choices 
                    ArrayList<String> allInputCat = getAllInputCatsWithNoChoices();
                    int inputCatNum = getInputCatNum();
                    
                    ArrayList<String> tf1Choices = getTf1InputChoicesByPairName(groupPairName);
                    ArrayList<String> tf2Choices = getTf2InputChoicesByPairName(groupPairName);
                    
                    ArrayList<String> g1InChoiceNames = new ArrayList<String>();
                    ArrayList<String> g2InChoiceNames = new ArrayList<String>();
                    
                    for(int k = 0; k <inputCatNum; k ++){
                        String tf1Choicei = tf1Choices.get(k);
                        if(tf1Choicei.equals("*")){
                            g1InChoiceNames.add(tf1Choicei);
                        }else{
                            String content1 = tf1Choicei + ":" + getOneChNameByInputCatIndex(k,tf1Choicei);
                            g1InChoiceNames.add(content1);
                        }  
                        
                        String tf2Choicei = tf2Choices.get(k);
                        if(tf2Choicei.equals("*")){
                            g2InChoiceNames.add(tf2Choicei);
                        }else{
                            String content2 = tf2Choicei + ":" + getOneChNameByInputCatIndex(k,tf2Choicei);
                            g2InChoiceNames.add(content2);
                        }
                    }
                    
                    
                    ArrayList<String> temp = new ArrayList<String>();
                    temp.add(groupPairName);
                    temp.add(inputRelation);
                    temp.add(outputRelation);
                    temp.add(mrDef);
                    temp.add(modifiedTime);
                    temp.add(createdTime);
                    for(int m = 0; m < g1OutChoiceNames.size(); m ++){
                        temp.add(g1OutChoiceNames.get(m) + " -> " + g2OutChoiceNames.get(m));
                    }
                    
                    for(int m = 0; m <g1InChoiceNames.size(); m ++){
                        temp.add(g1InChoiceNames.get(m) + " -> " + g2InChoiceNames.get(m));
                    }
                    results.add(temp);
                }
            }    
        }
        return results;
    }
    
    
    
    
    
    public int getMaxMRIDNew() {
    	int maxID = -1;
    	for (int i = 0; i<this.infoOfPairsFromDiffenentGroups.size();i++){
    		for(int j = 0; j<this.infoOfPairsFromDiffenentGroups.get(i).size(); j++){
    			if(maxID <this.infoOfPairsFromDiffenentGroups.get(i).get(j).getMRID()) {
    				maxID = this.infoOfPairsFromDiffenentGroups.get(i).get(j).getMRID();
    			}
    		}
    	}
    	
    	for (int i = 0; i<this.infoOfPairsFromSameGroups.size();i++){
    		for(int j = 0; j<this.infoOfPairsFromSameGroups.get(i).size(); j++){
    			if(maxID <this.infoOfPairsFromSameGroups.get(i).get(j).getMRID()) {
    				maxID = this.infoOfPairsFromSameGroups.get(i).get(j).getMRID();
    			}
    		}
    	}
    	return maxID;
    }
    //***Finish of getting summary MRs information********

    //***Synchronize the MR.xml file
    
    public void SynchronizePairInfoFileByPairName(String changedPairName) {
    	PairInformation changedPair = this.getPairByPairName(changedPairName);
    	//MRXMLOperator.updatePairInfoFile(, changedPair, this.inforInitializer);
        MRXMLOperator.updatePairInfoFileNew(changedPair, this.inforInitializer.getSourceDataDir());
    }
    
    public void SynchronizePairInfoFileNew(ArrayList<ArrayList<PairInformation>> newInfoOfPairsFromDifferentGroups,ArrayList<ArrayList<PairInformation>> newInfoOfPairsFromSameGroups){
        MRXMLOperator.createPairInfoFileNew(newInfoOfPairsFromDifferentGroups, newInfoOfPairsFromSameGroups, this.inforInitializer.getSourceDataDir());
    }
    //***Finish the synchronizing

    
    
//    //private methods
//    private boolean isPairMatchingReq(TestFrame tempTf1, TestFrame reqTf1, TestFrame tempTf2, TestFrame reqTf2, String diffConditionStr) {
//        int count = 0;
//        //check whether: tempTf1 matches reqTf1 and tempTf2 matches reqTf2
//        //we need count == reqTf1.getChoicesNum() to make sure the above conditions are satisfied
//        for (int j = 0; j < reqTf1.getChoicesNum(); j++) {
//            if (reqTf1.getOneChoiceValue(j).equals("-") && reqTf2.getOneChoiceValue(j).equals("-")) {
//                if (diffConditionStr.equals("Exact")) {
//                    if (!tempTf1.getOneChoiceValue(j).equals(tempTf2.getOneChoiceValue(j))) {
//                        break;
//                    }
//                } else if (diffConditionStr.equals("Contain")) {
//                    //do no checking, this choice can be any value in tempTf1 and tempTf2
//                }
//            } else if (reqTf1.getOneChoiceValue(j).equals("-") && !reqTf2.getOneChoiceValue(j).equals("-")) {
//                //check tempTf2.getOneChoiceValue(j) against reqTf2.getOneChoiceValue(j) only
//                if (!tempTf2.getOneChoiceValue(j).equals(reqTf2.getOneChoiceValue(j))) {
//                    break;
//                }
//            } else if (!reqTf1.getOneChoiceValue(j).equals("-") && reqTf2.getOneChoiceValue(j).equals("-")) {
//                //check tempTf1.getOneChoiceValue(j) against reqTf1.getOneChoiceValue(j) only
//                if (!tempTf1.getOneChoiceValue(j).equals(reqTf1.getOneChoiceValue(j))) {
//                    break;
//                }
//            } else if (!reqTf1.getOneChoiceValue(j).equals("-") && !reqTf2.getOneChoiceValue(j).equals("-")) {
//                //check tempTf1.getOneChoiceValue(j) against reqTf1.getOneChoiceValue(j) and tempTf2.getOneChoiceValue(j) against reqTf2.getOneChoiceValue(j)
//                if (!tempTf1.getOneChoiceValue(j).equals(reqTf1.getOneChoiceValue(j)) || !tempTf2.getOneChoiceValue(j).equals(reqTf2.getOneChoiceValue(j))) {
//                    break;
//                }
//            }
//            count++;
//        }
//        if (count == reqTf1.getChoicesNum()) {
//            return true;
//        } else {
//            return false;
//        }
//
//    }

//    private void separateEligibleIndexList(ArrayList<Integer> wholeList, ArrayList<Integer> doneList, ArrayList<Integer> notDoneList) {
//        for (int i = 0; i < wholeList.size(); i++) {
//            int currID = wholeList.get(i);
//            if (this.allPairsInfo.get(currID).getHasMRInfo().equals("-")) {//not done yet
//                notDoneList.add(currID);
//            } else {
//                doneList.add(currID);
//            }
//        }
//    }

    private void sortIndexRandomly(ArrayList<Integer> unSortedIndexList, ArrayList<Integer> sortedIndexList) {
        //get a random sequence for the non-investigated part
        int limit = unSortedIndexList.size();

        int[] randomIndex = new int[limit];
        for (int i = 0; i < limit; i++) {
            randomIndex[i] = i;
        }
        int w;

        //Random rand = new Random();//real random
        Random rand = new Random(1);//pseudo random
        for (int i = limit - 1; i > 0; i--) {
            w = rand.nextInt(i);
            int t = randomIndex[i];
            randomIndex[i] = randomIndex[w];
            randomIndex[w] = t;
        }

        //randomize for the investigated part
        for (int i = 0; i < limit; i++) {
            int temp = randomIndex[i];
            sortedIndexList.add(unSortedIndexList.get(temp));
        }
    }

//    private void initializeAllPairInfo() {
//        if (MRXMLOperator.checkFileExistence(this.inforInitializer.getSourceDataDir())) {//MR.xml exist, fill in the allPairsInfo from the existing MR.xml file
//            this.allPairsInfo = MRXMLOperator.readInAllPairsInfo(this.inforInitializer.getSourceDataDir());
//        } else {//MR.xml file does not exist, then fill in the allPairsInfo by creating all pairs and create MR.xml with allPairsInfo
//            int pairsNum = this.inforInitializer.getTestFrameNum();
//            this.allPairsInfo = new ArrayList<>();
//            for (int i = 0; i < pairsNum - 1; i++) {
//                for (int j = i + 1; j < pairsNum; j++) {
//                    TestFrame ti = this.inforInitializer.getAlslTestFrames().get(i);
//                    TestFrame tj = this.inforInitializer.getAllTestFrames().get(j);
//                    this.allPairsInfo.add(createPairInformation(ti, i + 1, tj, j + 1));
//                }
//            }
//            MRXMLOperator.createPairInfoFile(this.allPairsInfo, this.inforInitializer.getSourceDataDir());
//        }
//    }
    
    
    private void initializeAllPairInfoNew() {
    	//int m = 1;
    	this.infoOfPairsFromDiffenentGroups = new ArrayList<ArrayList<PairInformation>> ();
    	this.infoOfPairsFromSameGroups = new ArrayList<ArrayList<PairInformation>> ();
    	if(MRXMLOperator.checkFileExistence(this.inforInitializer.getSourceDataDir())) {
    		this.allRawPairsInfo = MRXMLOperator.readInAllPairsInfoNew(this.inforInitializer.getSourceDataDir());
    		this.infoOfPairsFromDiffenentGroups = this.allRawPairsInfo.get(0);
    		this.infoOfPairsFromSameGroups = this.allRawPairsInfo.get(1);   		
    		//MRXMLOperator.createPairInfoFileNew(this.infoOfPairsFromDiffenentGroups, this.infoOfPairsFromSameGroups, "D:\\");   		
    	}else {
    		int groupNumber = this.ioctfGroupManager.getGroupNumber();
    		for (int i = 0; i < groupNumber - 1; i++) {
    			for (int j = i+1; j < groupNumber ;j++) {
    				GroupInformation ti = this.ioctfGroupManager.getOneGroupByID(i);
               	 	GroupInformation tj = this.ioctfGroupManager.getOneGroupByID(j);
               	 	this.infoOfPairsFromDiffenentGroups.add(this.createInformationOfPairsFromTwoDifferentGroups(ti, tj));   
    			}
    		}
    		
    		for (int i = 0; i < groupNumber ; i++) {
              	 GroupInformation ti = this.ioctfGroupManager.getOneGroupByID(i);
                 if(ti.getTestFrameNum() >= 2){//如果少于两个测试帧，则无法进行组内信息生成，原因是一个测试帧无法进行组内比较
                    this.infoOfPairsFromSameGroups.add(this.createInformationOfPairsFromTheSameGroup(ti));
                 }
            }
    		MRXMLOperator.createPairInfoFileNew(this.infoOfPairsFromDiffenentGroups, this.infoOfPairsFromSameGroups, this.inforInitializer.getSourceDataDir());
        }           
    }
    
   
    
    
    
    
//    private void initializePairInfoOfDiffernetGroups(){
//    	 if (MRXMLOperator.checkFileExistence(this.inforInitializer.getSourceDataDir())) {//MR.xml exist, fill in the allPairsInfo from the existing MR.xml file
//             this.allPairsInfo = MRXMLOperator.readInAllPairsInfo(this.inforInitializer.getSourceDataDir());
//         } else {
//        	 int groupNumber = this.ioctfGroupManager.getGroupNumber();
//             //int pairsNum = this.inforInitializer.getTestFrameNum();
//             this.allPairsInfo = new ArrayList<>();
//             for (int i = 0; i < groupNumber - 1; i++) {
//                 for (int j = i + 1; j < groupNumber; j++) {
//                	 GroupInformation ti = this.ioctfGroupManager.getOneGroupByID(i);
//                	 GroupInformation tj = this.ioctfGroupManager.getOneGroupByID(j);
//                	 this.infoOfPairsFromDiffenentGroups.add(this.createInformationOfPairsFromTwoDifferentGroups(ti, tj));                     
//                 }
//             }
//             MRXMLOperator.createPairInfoFile(this.allPairsInfo, this.inforInitializer.getSourceDataDir());
//         }
//    }
//    
//    private void initializePairInfoOfSameGroups(){
//   	 	if (MRXMLOperator.checkFileExistence(this.inforInitializer.getSourceDataDir())) {//MR.xml exist, fill in the allPairsInfo from the existing MR.xml file
//            this.allPairsInfo = MRXMLOperator.readInAllPairsInfo(this.inforInitializer.getSourceDataDir());
//        } else {
//       	 int groupNumber = this.ioctfGroupManager.getGroupNumber();
//            this.allPairsInfo = new ArrayList<>();
//            for (int i = 0; i < groupNumber - 1; i++) {
//               	 GroupInformation ti = this.ioctfGroupManager.getOneGroupByID(i);
//               	 this.infoOfPairsFromSameGroups.add(this.createInformationOfPairsFromTheSameGroup(ti));                
//                }
//            }
//            MRXMLOperator.createPairInfoFile(this.allPairsInfo, this.inforInitializer.getSourceDataDir());
//    }
   

//    private PairInformation createPairInformation(TestFrame t1, int t1Name, TestFrame t2, int t2Name) {
//    	/*
//        int catNum = this.inforInitializer.getCatNum();
//        */
//        
//        int inputCatNum = this.inforInitializer.getInputCatNum();
//        int outputCatNum = this.inforInitializer.getOutputCatNum();
//        
//        
//        int type; //typeA = 1; typeB = 2; typeC = 3;
//        int ka = 0;
//        int kb = 0;
//
//        for (int i = 0; i < inputCatNum; i++) {
//            if (!t1.getOneChoiceValue(i).equals(t2.getOneChoiceValue(i))) {
//                //case 1: t1.getOneChoiceValue(i) or t2.getOneChoiceValue(i) is "*"
//                if (t1.getOneChoiceValue(i).equals("*") || t2.getOneChoiceValue(i).equals("*")) {
//                    kb++;
//                } //case 2: t1.getOneChoiceValue(i) and t2.getOneChoiceValue(i) are different choices
//                else {
//                    ka++;
//                }
//                //edit_dist++;
//            }
//        }
//        if ((ka == 0) && (kb > 0)) {
//            type = 2;
//        } else if ((kb == 0) && (ka > 0)) {
//            type = 1;
//        } else {// kb>0 && ka>0
//            type = 3;
//        }
//
//        String pairName = Integer.toString(t1Name) + "<->" + Integer.toString(t2Name); //e.g. 1<->2
//
//        PairInformation newPairInfo = new PairInformation(pairName, ka, kb, type, "-", "-", "-", "-", -1, "-", "-");
//
//        return newPairInfo;
//
//    }
    

    private ArrayList<PairInformation> createInformationOfPairsFromTwoDifferentGroups(GroupInformation group1, GroupInformation group2) {
    	
    	int testFrameNumOfGroup1 = group1.getTestFrameNum();
    	int testFrameNumOfGroup2 = group2.getTestFrameNum();
    	String groupNameOfTestFrame1 = new String();
    	String groupNameOfTestFrame2 = new String();
    	ArrayList<PairInformation> pairInfoOfCurrTwoGroups = new ArrayList<PairInformation>();
    	int pairType = 1;//
    	
    	for(int i = 0; i < testFrameNumOfGroup1; i++){
    		for(int j = 0; j < testFrameNumOfGroup2; j++){
    			groupNameOfTestFrame1 = group1.getGroupName();
    			groupNameOfTestFrame2 = group2.getGroupName();
    			String pairName = group1.getGroupName()+":"+Integer.toString(i) + "<->" + group2.getGroupName() +":"+ Integer.toString(j);
    			PairInformation newPairInfo = new PairInformation(pairName,pairType,groupNameOfTestFrame1,groupNameOfTestFrame2,"-","-","-","-","-","-",-1,"-","-");
    			pairInfoOfCurrTwoGroups.add(newPairInfo);
    		}
    	}
    	
    	return pairInfoOfCurrTwoGroups;
    }
    
 
    private ArrayList<PairInformation> createInformationOfPairsFromTheSameGroup(GroupInformation group1) {
    	
    	int testFrameNumOfGroup1 = group1.getTestFrameNum();
    	
    	String groupNameOfTestFrame1 = new String();
    	
    	ArrayList<PairInformation> pairInfoOfCurrGroup = new ArrayList<PairInformation>();
    	int pairType = 2;//
    	
    	for(int i = 0; i < testFrameNumOfGroup1; i++){
    		for(int j = i + 1; j < testFrameNumOfGroup1; j++){
    			groupNameOfTestFrame1 = group1.getGroupName();
    			String pairName = group1.getGroupName()+":"+Integer.toString(i) + "<->" + group1.getGroupName() +":"+ Integer.toString(j);
    			PairInformation newPairInfo = new PairInformation(pairName,pairType,groupNameOfTestFrame1,groupNameOfTestFrame1,"-","-","-","-","-","-",-1,"-","-");
    			pairInfoOfCurrGroup.add(newPairInfo);
    		}
    	} 	
    	return pairInfoOfCurrGroup;
    }
    
 
//    private ArrayList<Integer> getDiffCat(int pairID) {
//        ArrayList<Integer> diffCatIDs = new ArrayList<>();
//        ArrayList<String> tf1Choices = this.getTf1ChoicesByPairIndex(pairID);
//        ArrayList<String> tf2Choices = this.getTf2ChoicesByPairIndex(pairID);
//
//        for (int i = 0; i < tf1Choices.size(); i++) {
//            if (!tf1Choices.get(i).equals(tf2Choices.get(i))) {
//                diffCatIDs.add(i);
//            }
//        }
//        return diffCatIDs;
//    }

    private boolean firstCoverSecond(ArrayList<Integer> refDiffCatIDs, ArrayList<Integer> tempDiffCatIDs) {
        if (refDiffCatIDs.size() < tempDiffCatIDs.size()) {
            return false;
        } else {
            int countFound = 0;
            for (int i = 0; i < tempDiffCatIDs.size(); i++) {
                int lastPos = -1;
                //check: already larger, no need to search in refDiffCatIDs
                if (refDiffCatIDs.get(lastPos + 1) > tempDiffCatIDs.get(i)) {
                    break;
                }

                //else, go to search for tempDiffCatIDs.get(i) in refDiffCatIDs
                int foundTempi = 0;
                for (int j = lastPos + 1; j < refDiffCatIDs.size(); j++) {
                    if (refDiffCatIDs.get(j) == tempDiffCatIDs.get(i)) {//found, then search tempDiffCatIDs.get(i+1) from refDiffCatIDs.get(j+1)
                        countFound++;
                        lastPos = j;
                        foundTempi = 1;
                        break;
                    }
                }
                if (foundTempi == 0) {//tempDiffCatIDs.get(i) is not found, then no need to search for tempDiffCatIDs.get(i+1)
                    break;
                }
            }
            if (countFound == tempDiffCatIDs.size()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
