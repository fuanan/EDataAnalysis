package entity;


import java.util.ArrayList;

public class GroupInformation {
	
	private String groupName;
	private int groupID;
	private ArrayList<TestFrame> testFramesBelongToTheGroup;
	
	public GroupInformation(String name, int id, ArrayList<TestFrame> tf){
		this.groupName = name;
		this.groupID = id;
		this.testFramesBelongToTheGroup = tf;
		
	}
	
	public String getGroupName(){
		return this.groupName;
	}
	
	public ArrayList<TestFrame> getTestFramesBelongToTheGroup()
	{
		return this.testFramesBelongToTheGroup;
	}
	
	public int getGroupID(){
		return this.groupID;
	}
	
	public int getTestFrameNum(){
		return this.testFramesBelongToTheGroup.size();
	}
	
	public TestFrame getTestFrameByID(int id){
		return this.testFramesBelongToTheGroup.get(id);
	}
        
        public ArrayList<String> getOutputChoiceValuesOfGroup(){
            return this.testFramesBelongToTheGroup.get(0).getAllOutputChoiceValues();
        }

}
