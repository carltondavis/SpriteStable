package com.hazyfutures.spritestable;

/**
 * Created by cdavis on 2/15/2015.
 */
public class MatrixActions {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String actionName) {
        ActionName = actionName;
    }

    public Integer getLinkedSkill() {
        return LinkedSkill;
    }

    public void setLinkedSkill(Integer linkedSkill) {
        LinkedSkill = linkedSkill;
    }

    public Integer getLinkedAttribute() {
        return LinkedAttribute;
    }

    public void setLinkedAttribute(Integer linkedAttribute) {
        LinkedAttribute = linkedAttribute;
    }

    public Integer getMarksRequired() {
        return MarksRequired;
    }

    public void setMarksRequired(Integer marksRequired) {
        MarksRequired = marksRequired;
    }

    public Integer getOpposedAttribute() {
        return OpposedAttribute;
    }

    public void setOpposedAttribute(Integer opposedAttribute) {
        OpposedAttribute = opposedAttribute;
    }

    public Integer getLimitType() {
        return LimitType;
    }

    public void setLimitType(Integer limitType) {
        LimitType = limitType;
    }

    public Boolean getExists() {
        return Exists;
    }

    public void setExists(Boolean exists) {
        Exists = exists;
    }

    public Integer getOpposedSkill() {
        return OpposedSkill;
    }

    public void setOpposedSkill(Integer opposedSkill) {
        OpposedSkill = opposedSkill;
    }

    private long id;
    private String ActionName = "";
    private Integer LinkedSkill = -1;
    private Integer LinkedAttribute = -1;
    private Integer MarksRequired = -1;
    private Integer OpposedAttribute = -1;
    private Integer OpposedSkill = -1;
    private Integer LimitType = -1;
    private Boolean Exists = false;

    public static final Integer ATTACK = 1;
    public static final Integer SLEAZE= 2;
    public static final Integer DATA_PROCESSING= 3;
    public static final Integer FIREWALL = 4;

}
