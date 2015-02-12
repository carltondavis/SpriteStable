package com.hazyfutures.spritestable;

/**
 * Created by cdavis on 2/11/2015.
 */
public class Skills {
    private long id;
    private String SkillName = "None";
    private Integer SkillValue = -1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSkillName(String Name) {
        SkillName=Name;
    }

    public String getSkillName() {
        return SkillName;
    }


    public void setSkillValue(Integer Value) {
        this.SkillValue= Value;
    }

    public Integer getSkillValue() {

        return this.SkillValue;
    }


}
