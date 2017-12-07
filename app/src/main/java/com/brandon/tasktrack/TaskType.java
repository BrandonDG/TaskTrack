package com.brandon.tasktrack;

/**
 * Created by Brandon on 2017-12-05.
 */

public class TaskType {
    private String name;
    private String colour;

    public TaskType(String name, String colour) {
        this.name = name;
        this.colour = colour;
    }

    public TaskType() { }

    public String getName() {
        return this.name;
    }

    public void setName(String newValue) {
        this.name = newValue;
    }

    public String getColour() {
        return this.colour;
    }

    public void setColour(String newValue) {
        this.colour = newValue;
    }
}
