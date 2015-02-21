package com.hazyfutures.spritestable;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdavis on 1/22/2015.
 * Values that should be set to persist between activities
 */
public class PersistentValues {

    public int pvActiveSpriteId = 0;

    public List<String> pvSpriteList = new ArrayList<>();
    List<Sprite> pvSprites = new ArrayList<>();
    //public List<String> pvSkillList = new ArrayList<>();
    List<Skills> pvSkills = new ArrayList<>();
    List<Stats> pvStats = new ArrayList<>();
    List<Qualities> pvQualities = new ArrayList<>();
    List<MatrixActions> pvMatrixActions = new ArrayList<>();

    List<Specializations> pvSpecializations = new ArrayList<>();
    private StatsDataSource datasource;



    public void SaveSpriteToDB() {
        SaveSpriteToDB(pvSprites.get(pvActiveSpriteId));
    }

    public void SaveAllSpritesToDB() {
        for(Sprite sprite : pvSprites) {
            SaveSpriteToDB(sprite);
        }
    }
    public void SaveSpriteToDB(Sprite SpriteToSave){datasource.updateSprite(SpriteToSave);}
    public void SaveNewSpriteToDB(Sprite SpriteToInsert){datasource.insertSprite(SpriteToInsert);}
    public void SaveSkillToDB(String SkillName){datasource.updateSkill(SkillName, getSkillValue(SkillName));}
    public void SaveSkillToDB(String name, Integer value){datasource.updateSkill(name, value);}

    public void SaveStatToDB(String name){datasource.updateStat(name, getStatValue(name));}
    public void SaveStatToDB(String name, Integer value){datasource.updateStat(name, value);}

    public void SaveQualityToDB(String name){datasource.updateQuality (name, getQualityValue(name));}
    public void SaveQualityToDB(String name, Integer value){datasource.updateQuality(name, value);}


    public void SaveAllSkillsToDB(){
        List<Specializations> tempspecs=pvSpecializations;
        Long ID;
        String skillName;
    //    List<Specializations> temp2specs=tempspecs;
        for(Skills skill: pvSkills){
                skillName=skill.getSkillName();
                ID = skill.getId();
                SaveSkillToDB(skillName, skill.getSkillValue());
  //              tempspecs=temp2specs;
                for(Specializations specialization: tempspecs){
                    if(ID.equals(specialization.getLinkedSkill())) {
                        datasource.updateSpecialization(specialization.getSpecializationName(), skillName, specialization.getExists() ? 1 : 0);
                    }
//                    temp2specs.remove(specialization);
                }
        }
    }
    public void SaveAllStatsToDB(){
        for(Stats stat: pvStats){
            SaveStatToDB(stat.getStatName(), stat.getStatValue());
        }
    }
    public void SaveAllQualitiesToDB(){
        for(Qualities quality: pvQualities){
            SaveQualityToDB(quality.getQuality(), quality.getValue());
        }
    }

    public void SaveAllToDB() {
        SaveAllSpritesToDB();
        SaveAllSkillsToDB();
        SaveAllQualitiesToDB();
        SaveAllStatsToDB();


    }


    public void RestoreFromDB(Context content) {


        datasource = new StatsDataSource(content);
        datasource.open();
        //List<Stats> values = datasource.getAllStats();
        pvSprites = datasource.getAllSprites();
        pvSkills = datasource.getAllSkills();
        pvQualities = datasource.getAllQualities();
        pvStats = datasource.getAllStats();
        pvSpecializations = datasource.getAllSpecializations();
        pvMatrixActions = datasource.getAllMatrixActions();
        pvActiveSpriteId = 0;


    }

    public Sprite getCurrentSprite() {
        if (pvSprites.isEmpty()) {
            Sprite newSprite = new Sprite();
            newSprite.setSpriteType(1);
            newSprite.setRating(1);
            newSprite.setRegistered(0);
            pvSprites.add(newSprite);
            pvActiveSpriteId = 0;
        }

        return pvSprites.get(pvActiveSpriteId);

    }
    public Sprite getSprite(long id) {

        return pvSprites.get(pvActiveSpriteId);

    }

    public void DeleteSprite(Sprite SpriteToDie){
        datasource.deleteSprite(SpriteToDie);
    }


    public Integer getSkillValue(String Name){
        return getSkillValue(Name, "");
    }

public Integer getSkillValue(String Name, String Specialization){
    Integer value=0;
    long ID=0;
    for(Skills skill: pvSkills){
        if(skill.getSkillName().equals(Name)) {
            ID=skill.getId();
            value= skill.getSkillValue();
            break;
        }
    }
    for(Specializations specialization: pvSpecializations){
        if((specialization.getLinkedSkill()== ID)&&(specialization.getSpecializationName().equals(Specialization))) {
            return (value+2);
        }
    }
    return value;
}

    public void setSkillValue(String Name, Integer Value){
        for(Skills skill: pvSkills){
            if(skill.getSkillName().equals(Name)) {
                skill.setSkillValue(Value);
                break;
            }
        }
    }

    public Integer getStatValue(String Name){
        Integer value=0;
        for(Stats stat: pvStats){
            if(stat.getStatName().equals(Name)) {
                value= stat.getStatValue();
                break;
            }
        }
        return value;
    }

    public void setStatValueShort(String Name, Integer Value){
        for(Stats stat: pvStats){
            if(stat.getStatNameShort().equals(Name)) {
                stat.setStatValue(Value);
                break;
            }
        }
    }

    public void setStatValue(String Name, Integer Value){
        for(Stats stat: pvStats){
            if(stat.getStatName().equals(Name)) {
                stat.setStatValue(Value);
                break;
            }
        }
    }
    public void addStatValue(String Name, Integer Value){
        for(Stats stat: pvStats){
            if(stat.getStatName().equals(Name)) {
                stat.setStatValue(stat.getStatValue()+Value);
                break;
            }
        }
    }
    public Integer getQualityValue(String Name){
        Integer value=0;
        for(Qualities quality: pvQualities){
            if(quality.getQuality().equals(Name)) {
                value= quality.getValue();
                break;
            }
        }
        return value;
    }

    public void setQualityValue(String Name, Integer Value){
        setQualityValue(Name, null, -1, Value);
    }
    public void setQualityValue(String Name, String Extra, Integer Value) {
        setQualityValue(Name, Extra, -1, Value);
    }
    public void setQualityValue(String Name, Integer LinkedSkill, Integer Value) {
        setQualityValue(Name, null, LinkedSkill, Value);
    }
    public void setQualityValue(String Name, String Extra, Integer LinkedSkill, Integer Value) {
        for (Qualities quality : pvQualities) {
            if (quality.getQuality().equals(Name)) {
                quality.setValue(Value);
                quality.setExtra(Extra);
                quality.setLinkedSkill(LinkedSkill);
                break;
            }
        }
    }
    public void setQualityValueWithBP(String Name, String Extra, Integer BuildPoints) {
        setQualityValueWithBP(Name, Extra, -1, BuildPoints);
    }
    public void setQualityValueWithBP(String Name, Integer LinkedSkill, Integer BuildPoints) {
        setQualityValueWithBP(Name, "", LinkedSkill, BuildPoints);
    }
    public void setQualityValueWithBP(String Name,  Integer BuildPoints) {
        setQualityValueWithBP(Name, "", -1, BuildPoints);
    }
    public void setQualityValueWithBP(String Name, String Extra, Integer LinkedSkill, Integer BuildPoints){
        Name = Name.toUpperCase().trim();
        for(Qualities quality: pvQualities){
            Boolean gQ = quality.doesQualityChummerExist(Name);
            if(gQ) {
                quality.setExtra(Extra);
                quality.setLinkedSkill(LinkedSkill);
                switch (quality.getQuality().toUpperCase()){
                    case "CODESLINGER"://TODO: Add action that is benefited to DB, and a picker for the Quality page
                        if(BuildPoints>0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "FOCUSED CONCENTRATION":
                        if(BuildPoints>0) {
                            quality.setValue(BuildPoints/4);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "INSOMNIA":
                        quality.setValue(0);
                        if(BuildPoints==-10) {
                            quality.setValue(1);
                        }
                        if(BuildPoints==-15){
                            quality.setValue(2);
                        }
                        break;
                    case "HIGH PAIN TOLERANCE":
                            quality.setValue(BuildPoints/7);
                        break;
                    case "HOME GROUND":
                        if(BuildPoints>0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "NATURAL HARDENING":
                        if(BuildPoints>0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "QUICK HEALER":
                        if(BuildPoints>0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "TOUGHNESS":
                        if(BuildPoints>0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "WILL TO LIVE":
                            quality.setValue(BuildPoints/3);

                        break;
                    case "BAD LUCK":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "CODEBLOCK":  //TODO: Add action that is blocked to DB, and a picker for the Quality page
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "LOSS OF CONFIDENCE"://TODO: Add action that is penalized to DB, and a picker for the Quality page
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "LOW PAIN TOLERANCE":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "SENSITIVE SYSTEM":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "SIMSENSE VERTIGO":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "SLOW HEALER":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "PERCEPTIVE":
                            quality.setValue(BuildPoints/5);

                        break;
                    case "TOUGH AS NAILS PHYSICAL":
                        quality.setValue(BuildPoints/5);

                        break;
                    case "TOUGH AS NAILS STUN":
                        quality.setValue(BuildPoints/5);
                        break;
                    case "ASTHMA":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "AsthmaFatigueDamage":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "BiPolar":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "BiPolarCurrent":
                            quality.setValue(0);
                        break;
                    case "Blind":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "Computer Illiterate":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "Deaf":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "Dimmer Bulb":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "Illiterate":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    case "Oblivious":
                        quality.setValue(0);
                        if(BuildPoints==-6) {
                            quality.setValue(1);
                        }else {
                            if (BuildPoints == -10) {
                                quality.setValue(2);
                            }
                        }
                        break;
                    case "Pie Iesu Domine":
                        if(BuildPoints<0) {
                            quality.setValue(1);
                        }else{
                            quality.setValue(0);
                        }
                        break;
                    default:
                        Log.i("Quality", "Didn't find: " + Name);
                }
                break;
            }
        }

    }

    public void setSpecialization(String SkillName, String Specialization, Boolean Exists){
        Integer value=0;
        long ID=0;
        for(Skills skill: pvSkills){
            if(skill.getSkillName().equals(SkillName)) {
                ID=skill.getId();
                break;
            }
        }
        for(Specializations specialization: pvSpecializations){
            if((specialization.getLinkedSkill()== ID)&&(specialization.getSpecializationName().equals(Specialization))) {
                specialization.setExists(Exists);
            }
        }
    }

    public List<String> getSpecializationList(String SkillName){
        long ID=0;
        List<String> SpecList = new ArrayList<>();
        for(Skills skill: pvSkills){
            if(skill.getSkillName().equals(SkillName)) {
                ID=skill.getId();
                break;
            }
        }
        for(Specializations specialization: pvSpecializations){
            if((specialization.getLinkedSkill()== ID)) {
                SpecList.add(specialization.getSpecializationName());
            }
        }
        return SpecList;
    }
    public List<String> getSpecializationListExists(String SkillName){
        long ID=0;
        List<String> SpecList = new ArrayList<>();
        for(Skills skill: pvSkills){
            if(skill.getSkillName().equals(SkillName)) {
                ID=skill.getId();
                break;
            }
        }

        for(Specializations specialization: pvSpecializations){
            if((specialization.getLinkedSkill()== ID)&&specialization.getExists()) {
                SpecList.add(specialization.getSpecializationName());
            }
        }
        return SpecList;
    }

    public void UpdateSpriteList() {
        boolean unregisteredExists = false;
        pvSpriteList.clear();
        List<Sprite> Unnecessary = new ArrayList<>();
        for (Sprite sprite : pvSprites) {
            //    Log.i("Sprite", "Process: ID:" + sprite.getId() + " Force " + sprite.getRating() + " " + sprite.getType() + " with " + sprite.getServicesOwed()+ " services " + sprite.getRegistered());
            //Keep the first unregistered sprite
            //Delete any other unregistered sprites
            //If no unregistered sprites found, create a NEW SPRITE
            //If sprite is Registered and Services==0 delete it
            //Add it to the list if it's registered and services>0 or if it's the first unregistered services>0

            if ((sprite.getRegistered() == 0 && unregisteredExists) || (sprite.getRegistered() == 1 && sprite.getServicesOwed() == 0)) { //Second or later unregistered item, or all services used
                Unnecessary.add(sprite);        //Set up to remove it from the list
                //Log.i("Sprite", "Delete: ID:" + sprite.getId() + " Force " + sprite.getRating() + " " + sprite.getType() + " with " + sprite.getServicesOwed()+ " services " + sprite.getRegistered());
                if (pvSprites.get(pvActiveSpriteId) == sprite) {      //If we're deleting the active sprite then aim the sprite pointer at an existing sprite.
                    if (pvActiveSpriteId > 0) {
                        pvActiveSpriteId--;
                    }
                }
            } else {
                String title = String.valueOf("Force " + sprite.getRating() + " " + sprite.getType()) + " with " + sprite.getServicesOwed() + " services";

                if (sprite.getRegistered() == 1) {
                    title = title + " Registered";
                } else {
                    unregisteredExists = true;
                }
                pvSpriteList.add(title);
            }
        }
        for (Sprite deleteSprite : Unnecessary) {
            datasource.deleteSprite(deleteSprite);
        }
        pvSprites.removeAll(Unnecessary);

        if (!unregisteredExists) {//Creating a new Sprite is an option
            Sprite newSprite = new Sprite();
            newSprite.setRating(getCurrentSprite().getRating());
            newSprite.setSpriteType(getCurrentSprite().getType());

            //Populate Sprite List with NEW option
            String title = String.valueOf("Force " + newSprite.getRating() + " " + newSprite.getType()) + " with " + newSprite.getServicesOwed() + " services";
            pvSpriteList.add(title);
            newSprite.setId(datasource.insertSprite(newSprite));  //Save the new sprite to the DB
            pvSprites.add(newSprite);
        }

    }

    public Boolean DoIHaveBadLuck(){
        if(getQualityValue("BadLuck")==1) {
            Dice die = new Dice();
            die.rollDice(1, false);
            if (die.isCriticalGlitch) {
                return true;
            }
        }
        return false;
    }



    public void ResetDB(Context context){

        datasource = new StatsDataSource(context);
        datasource.open();
        datasource.ResetDB();

    }
    public void LoadQualities(Context context){
        //Load from the Database
    }

}



