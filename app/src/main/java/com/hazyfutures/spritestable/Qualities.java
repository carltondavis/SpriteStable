package com.hazyfutures.spritestable;

/**
 * Created by cdavis on 2/15/2015.
 */
public class Qualities {
    private long id;
    private String Quality = "";
    private Integer Value = 0;
    private String Extra = "";

    public String getExtra() {
        return Extra;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }



    public Integer getLinkedSkill() {
        return LinkedSkill;
    }

    public void setLinkedSkill(Integer linkedSkill) {
        LinkedSkill = linkedSkill;
    }

    private Integer LinkedSkill=-1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuality() {
        return Quality;
    }
    public Boolean doesQualityChummerExist(String Name) {
        if(Quality.length()< Name.length()){
            String tempName = Name.substring(0, Quality.length()).toUpperCase();
            return tempName.equals(Quality.toUpperCase());
        }else{
            String tempQuality=Quality.substring(0, Name.length()).toUpperCase();
            return tempQuality.equals(Name.toUpperCase());
        }
    }

    public void setQuality(String quality) {
        Quality = quality;
    }

    public Integer getValue() {
        return Value;
    }

    public void setValue(Integer value) {
        Value = value;
    }


    //TODO: Badluck can only occur once per session. Add a new stat for Bad Luck has Struck!


}
