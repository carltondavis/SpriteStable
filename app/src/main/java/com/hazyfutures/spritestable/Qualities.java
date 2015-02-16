package com.hazyfutures.spritestable;

/**
 * Created by cdavis on 2/15/2015.
 */
public class Qualities {
    private long id;
    private String Quality = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuality() {
        return Quality;
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

    private Integer Value = 0;
}
