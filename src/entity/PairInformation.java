/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author xxie
 */
public class PairInformation {
    //System.out.println("pair,ka,kb,type,has MR,MRName,Changes Affect Result,MRDefinition,MRID,Created Time,Last Modified");

    private String pairName;
    //private int ka;
    //private int kb;
    private int type;
    private String groupNameOfTestFrame1;
    private String groupNameOfTestFrame2;
    
    private String hasMR;
    private String mrName;
    
    private String inputRelationDefinition;
    private String outputRelationDefinition;
    
    private String affectOrNot;
    private String mrDefinition;
    private int mrID;
    private String createdTime;
    private String lastModifiedTime;

    public PairInformation(String pair, int a, int b, int t, String hasMRStr, String mrNameStr, String affect, String mrDef, int id, String created, String mofified) {
        //generate an non-empty PairInformation
        this.pairName = pair;
        //this.ka = a;
        //this.kb = b;
        this.type = t;//typeA = 1; typeB = 2; typeC = 3;
        this.hasMR = hasMRStr;
        this.mrName = mrNameStr;
        this.affectOrNot = affect;
        //this.mrDefinition = mrDef;
        this.mrID = id;
        this.createdTime = created;
        this.lastModifiedTime = mofified;
    }
    
    public PairInformation(String name,int type,String groupnameoftf1,String groupnameoftf2, String hasMR, String mrName, String inputrelationdef, String outputrelationdef,String mrdefinition,String affect, int id, String created, String modified){
    	this.pairName = name;
    	this.type = type;
    	this.groupNameOfTestFrame1 = groupnameoftf1;
    	this.groupNameOfTestFrame2 = groupnameoftf2;
    	this.hasMR = hasMR;
    	this.mrName = mrName;
    	this.inputRelationDefinition = inputrelationdef;
    	this.outputRelationDefinition = outputrelationdef;
        this.mrDefinition = mrdefinition;
    	this.affectOrNot = affect;
    	this.mrID = id;
    	this.createdTime = created;
    	this.lastModifiedTime = modified;
    }

    public String getPairName() {
        return this.pairName;
    }

    public int getTf1Name() {
        String a[] = this.pairName.split("<->");
        return Integer.parseInt(a[0]);
    }

    public int getTf1ID() {//is Tf1Name-1
        String a[] = this.pairName.split("<->");
        String b[] = a[0].split(":");
        return Integer.parseInt(b[1]);
    }

    public int getTf2ID() {
        String a[] = this.pairName.split("<->");
        String b[] = a[1].split(":");
        return Integer.parseInt(b[1]);
    }

    public int getTf2Name() {
        String a[] = this.pairName.split("<->");
        return Integer.parseInt(a[1]);
    }

    public String getGroupNameOfTestFrame1(){
    	return this.groupNameOfTestFrame1;
    }
    
    public String getGroupNameOfTestFrame2(){
    	return this.groupNameOfTestFrame2;
    }
    
    
    /*
    public int getKaValue() {
        return this.ka;
    }

    public int getKbValue() {
        return this.kb;
    }*/

    public int getType() {
        return this.type;
    }
    
    

    public String getHasMRInfo() {
        return this.hasMR;
    }

    public String getMRName() {
        return this.mrName;
    }

    public String getAffectOrNot() {
        return this.affectOrNot;
    }
    
    public String getInputRelationDef(){
    	return this.inputRelationDefinition;
    }
    
    public String getOutputRelationDef(){
    	return this.outputRelationDefinition;
    }

    
    public String getMRDefinition() {
        return this.mrDefinition;
    }

    public int getMRID() {
        return this.mrID;
    }

    public String getCreatedTime() {
        return this.createdTime;
    }

    public String getModifiedTime() {
        return this.lastModifiedTime;
    }
    
    public String getGroupPairName(){
        return this.getGroupNameOfTestFrame1() + "<->" + this.getGroupNameOfTestFrame2();
    }
    

    public void setNewHasMRInfo(String newInfo) {
        this.hasMR = newInfo;
    }

    public void setNewMRName(String newInfo) {
        this.mrName = newInfo;
    }

    public void setNewAffectInfo(String newInfo) {
        this.affectOrNot = newInfo;
    }
    
    public void setNewInputRelationDef(String newInputRelationDef){
    	this.inputRelationDefinition = newInputRelationDef;
    }
    
    public void setNewOutputRelationDef(String newOutputRelationDef){
    	this.outputRelationDefinition = newOutputRelationDef;
    }

    
    
    public void setNewMRDefinition(String newInfo) {
        this.mrDefinition = newInfo;
    }

    public void setNewMRID(int newInfo) {
        this.mrID = newInfo;
    }

    public void setNewCreatedTime(String newInfo) {
        this.createdTime = newInfo;
    }

    public void setNewModifiedTime(String newInfo) {
        this.lastModifiedTime = newInfo;
    }
}
