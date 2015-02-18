package com.hazyfutures.spritestable;

/**
 * Created by cdavis on 1/17/2015.
 * Character stats
 */
public class Stats {
    private long id;
    private String StatName = "None";
    private Integer Value = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatName() {
        return StatName;
    }
    public String getStatNameShort() {
        return StatName.toUpperCase().substring(0,3);
    }

    public void setStatName(String StatName) {
        this.StatName = StatName;
    }

    public Integer getStatValue() {
        return Value;
    }

    public void setStatValue(Integer Value) {
        this.Value = Value;
    }


}
