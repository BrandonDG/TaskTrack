package com.brandon.tasktrack;

/**
 * Created by Brandon on 2017-12-05.
 */

public class Task {
    private String   name;
    //TaskType type;
    private String   type;
    private String   startTime;
    private String   endTime;
    private String   completed;

    public Task(String name, String type, String startTime, String endTime) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.completed = "false";
    }

    public Task() { }

    public String getName() {
        return this.name;
    }

    public void setName(String newValue) {
        this.name = newValue;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String newValue) {
        this.type = newValue;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String newValue) {
        this.startTime = newValue;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String newValue) {
        this.endTime = newValue;
    }

    public String getCompleted() {
        return this.completed;
    }

    public void setCompleted(String newValue) {
        this.completed = newValue;
    }
}
