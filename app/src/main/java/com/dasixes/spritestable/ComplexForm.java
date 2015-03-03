package com.dasixes.spritestable;

/**
 * Created by carlt_000 on 3/3/2015.
 */
public class ComplexForm {
    private long id;
    private String Name = "";
    private String Target = "";
    private String Duration = "";
    private String Fading = "";

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getFading() {
        return Fading;
    }

    public void setFading(String fading) {
        Fading = fading;
    }
}
