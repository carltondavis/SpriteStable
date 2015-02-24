package com.dasixes.spritestable;

/**
 * Created by cdavis on 1/22/2015.
 * Sprite data structure
 */
public class Sprite {


    public static final Integer COURIER_SPRITE = 1;
    public static final Integer CRACK_SPRITE = 2;
    public static final Integer DATA_SPRITE = 3;
    public static final Integer FAULT_SPRITE = 4;
    public static final Integer MACHINE_SPRITE = 5;

    private long ID;
    private int Rating = 1;
    private int ServicesOwed = 0;
    private int SpriteType = 1;
    private int GODScore = 0;
    private int Condition = 0;
    private int Registered = 0;



    public String getType() {
        switch (this.SpriteType) {
            case 1:
                return "Courier";
            case 2:
                return "Crack";
            case 3:
                return "Data";
            case 4:
                return "Fault";
            case 5:
                return "Machine";
        }
        return "Undefined";
    }


    public long getId() {
        return ID;
    }

    public void setId(long id) {
        this.ID = id;
    }


    public int getRating() {
        return Rating;
    }

    public void setRating(Integer Rating) {
        if(null==Rating) {
            Rating = 0;
        }
        this.Rating = Rating;
    }

    public int getServicesOwed() {
        return ServicesOwed;
    }

    public void setServicesOwed(Integer ServicesOwed) {
        if(null==ServicesOwed) {
            ServicesOwed = 0;
        }
            this.ServicesOwed = ServicesOwed;
    }

    public int getSpriteType() {
        return SpriteType;
    }

    public void setSpriteType(Integer SpriteType) {
        if(null==SpriteType) {
            SpriteType = 1;
        }
        this.SpriteType = SpriteType;
    }

    public void setSpriteType(String SpriteType) {
        int spriteType=1;
        switch (SpriteType) {
            case "Courier":
                spriteType=1;
                break;
            case "Crack":
                spriteType=2;
                break;
            case "Data":
                spriteType=3;
                break;
            case "Fault":
                spriteType=4;
                break;
            case "Machine":
                spriteType=5;
                break;
        }
        this.SpriteType = spriteType;
    }

    public int getGODScore() {
        return GODScore;
    }

    public void setGODScore(Integer GODScore) {
        if(null==GODScore) {
            GODScore = 0;
        }
        this.GODScore = GODScore;
    }

    public int getRegistered() {
        return Registered;
    }

    public void setRegistered(Integer Reg) {
        if(null==Reg) {
            Registered = 0;
        }
        this.Registered = Reg;
    }

    public int getCondition() {
        return Condition;
    }

    public void setCondition(Integer Condition) {
        if(null==Condition) {
            Condition = 0;
        }
        this.Condition = Condition;
    }


}


