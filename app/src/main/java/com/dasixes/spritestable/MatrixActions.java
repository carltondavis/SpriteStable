package com.dasixes.spritestable;

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

    public String getLinkedSkill() {
        return LinkedSkill;
    }

    public void setLinkedSkill(String linkedSkill) {
        LinkedSkill = linkedSkill;
    }

    public String getLinkedAttribute() {
        return LinkedAttribute;
    }

    public void setLinkedAttribute(String linkedAttribute) {
        LinkedAttribute = linkedAttribute;
    }

    public Integer getMarksRequired() {
        return MarksRequired;
    }

    public void setMarksRequired(Integer marksRequired) {
        MarksRequired = marksRequired;
    }

    public String getOpposedAttribute() {
        return OpposedAttribute;
    }

    public void setOpposedAttribute(String opposedAttribute) {
        OpposedAttribute = opposedAttribute;
    }

    public Integer getLimitType() {
        return LimitType;
    }

    public void setLimitType(Integer limitType) {
        LimitType = limitType;
    }


    public String getOpposedSkill() {
        return OpposedSkill;
    }

    public void setOpposedSkill(String opposedSkill) {
        OpposedSkill = opposedSkill;
    }

    private long id;
    private String ActionName = "";
    private String LinkedSkill = "";
    private String LinkedAttribute = "";
    private Integer MarksRequired = -1;
    private String OpposedAttribute = "";
    private String  OpposedSkill = "";
    private Integer  ActionType = 0;
    private Integer LimitType = -1;
    private Integer AssistDiceBonus=0;

    public Integer getAssistDiceBonus() {
        return AssistDiceBonus;
    }

    public void setAssistDiceBonus(Integer assistDice) {
        AssistDiceBonus = assistDice;
    }

    public Integer getAssistLimitBonus() {
        return AssistLimitBonus;
    }

    public void setAssistLimitBonus(Integer assistLimit) {
        AssistLimitBonus = assistLimit;
    }

    private Integer AssistLimitBonus=0;


    public Integer getActionType() {
        return ActionType;
    }

    public void setActionType(Integer actionType) {
        ActionType = actionType;
    }




    public static final Integer ATTACK = 1;
    public static final Integer SLEAZE= 2;
    public static final Integer DATA_PROCESSING= 3;
    public static final Integer FIREWALL = 4;

}
