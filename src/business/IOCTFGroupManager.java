package business;

import entity.GroupInformation;
import entity.TestFrame;
import java.util.ArrayList;


public class IOCTFGroupManager {
	
	private InfoInitializer infoInitializer;
	private ArrayList<GroupInformation> allGroups;
	
	public IOCTFGroupManager (InfoInitializer init){
		
		
		this.infoInitializer = init;
		this.allGroups = new ArrayList<>();
		this.allGroups = DivideTestFramesIntoGroups(infoInitializer);		
	}
	
	private ArrayList<GroupInformation> DivideTestFramesIntoGroups (InfoInitializer infoInit) {
		
		//int testFrameNum = infoInit.getTestFrameNum();
		ArrayList<GroupInformation> group = new ArrayList<>();
		ArrayList<TestFrame> testFrames = infoInit.getAllTestFrames();
		ArrayList<TestFrame> tempTestFrames = testFrames;
		int i = 0;
		for(; !testFrames.isEmpty(); i++ ){
			
			TestFrame expectedExtendedTestFrame = testFrames.get(0);
			ArrayList<String> outputChoices = expectedExtendedTestFrame.getAllOutputChoiceValues();
			ArrayList<TestFrame> currTestFrameGroup = new ArrayList<>();
			String groupName = "{";
			for(int j = 0;j < outputChoices.size();j++){
				groupName = groupName + outputChoices.get(j);
				if(j != (outputChoices.size() -1) )
				{
					groupName = groupName +",";
				}
			}
			groupName = groupName + "}";
			
			for(int k = 0; k < testFrames.size(); ){
				if(testFrames.get(k).getAllOutputChoiceValues().equals(outputChoices)){
					currTestFrameGroup.add(testFrames.get(k));
					tempTestFrames.remove(k);
				}
				else{
					k++;
				}
			}
			
			/*
			for(TestFrame tf : testFrames) {
					
				if(tf.getAllOutputChoiceValues().equals(outputChoices))	{
					currTestFrameGroup.add(tf);
					tempTestFrames.remove(tf);
				}
			}*/
			//i++;
			GroupInformation currGroup = new GroupInformation(groupName,i,currTestFrameGroup);
			group.add(currGroup);
			//testFrames = tempTestFrames;
		}
		return group;		
	}
	
	public ArrayList<GroupInformation> getAllGroupInformation() {
		return this.allGroups;
	}
	
	public GroupInformation getOneGroupByID(int id){
		
		for(GroupInformation g : allGroups){
			if(g.getGroupID() == id){
				return g;
			}
		}
		return null;
	}
	
	public int getGroupNumber(){
		return this.allGroups.size();
	}
	
	public GroupInformation getOneGroupByGroupName(String name){
		for(int i = 0; i< allGroups.size();i++){
			if(allGroups.get(i).getGroupName().equals(name)){
				return allGroups.get(i);
			}
		}
		return null;
	}
}
