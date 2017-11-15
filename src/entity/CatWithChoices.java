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
//this is object for one category associated with a series of choices.
public class CatWithChoices {

    private String catTag;
    private String catName;
    private ArrayList<String> choiceTags;
    private ArrayList<String> choiceNames;

    public CatWithChoices(String catTagString, String catNameString, ArrayList<String> chTagVec, ArrayList<String> chNameVec) {
        this.catTag = catTagString;
        this.catName = catNameString;
        this.choiceTags = new ArrayList<>(chTagVec);
        this.choiceNames = new ArrayList<>(chNameVec);
    }

    public String getCatTag() {
        return this.catTag;
    }

    public String getCatTagLower() {
        return this.catTag.toLowerCase();
    }
    

    public String getCatName() {
        return this.catName;
    }

    public ArrayList<String> getAllChoiceTagsAndNames() {
        ArrayList<String> tagsAndNamesForAllChoices = new ArrayList<>();
        for (int i = 0; i < this.choiceTags.size(); i++) {
            tagsAndNamesForAllChoices.add(this.choiceTags.get(i) + ": " + this.choiceNames.get(i));
        }
        return tagsAndNamesForAllChoices;
    }

    public String getChoiceNameByTag(String chTag) {
        String chName = "NULL";
        for (int i = 0; i < this.choiceTags.size(); i++) {
            if (chTag.equals(this.choiceTags.get(i))) {
                chName = this.choiceNames.get(i);
                break;
            }
        }
        return chName;
    }
}
