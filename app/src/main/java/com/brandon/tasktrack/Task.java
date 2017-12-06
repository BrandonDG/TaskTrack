package com.brandon.tasktrack;

/**
 * Created by Brandon on 2017-12-05.
 */

public class Task {
    String name;
    TaskType type;
    String startTime;
    String endTime;

    public Task(String name, TaskType type, String startTime, String endTime) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
