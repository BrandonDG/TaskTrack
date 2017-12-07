package com.brandon.tasktrack;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Brandon on 2017-12-06.
 */

public class TaskTrackUnitTest {

    @Test
    public void taskNameGetterSetter() throws Exception {
        Task task = new Task();
        task.setName("Gym");
        assertEquals("Gym", task.getName());
    }

    @Test
    public void taskTypeGetterSetter() throws Exception {
        Task task = new Task();
        task.setType("Exercise");
        assertEquals("Exercise", task.getType());
    }

    @Test
    public void taskStartTimeGetterSetter() throws Exception {
        Task task = new Task();
        task.setStartTime("1");
        assertEquals("1", task.getStartTime());
    }

    @Test
    public void taskEndGetterSetter() throws Exception {
        Task task = new Task();
        task.setEndTime("2");
        assertEquals("2", task.getEndTime());
    }

    @Test
    public void taskTypeNameGetterSetter() throws Exception {
        TaskType tasktype = new TaskType();
        tasktype.setName("Exercise");
        assertEquals("Exercise", tasktype.getName());
    }

    @Test
    public void taskTypeColourGetterSetter() throws Exception {
        TaskType tasktype = new TaskType();
        tasktype.setColour("Blue");
        assertEquals("Blue", tasktype.getColour());
    }
}
