package com.hazyfutures.spritestable;

/**
 * Created by cdavis on 1/17/2015.
 * Character stats
 */
public class Stat {
    private long id;
    private String Stat = "None";
    private Integer Value = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStat() {
        return Stat;
    }

    public void setStat(String Stat) {
        this.Stat = Stat;
    }

    public Integer getValue() {
        return Value;
    }

    public void setValue(Integer Value) {
        this.Value = Value;
    }


}
