package com.example.scheduler;

public class TaskSet extends DataSet <Task> {

    public TaskSet(){

        super();
    }

    public boolean isMsg() {

        boolean ret = false;

        if(elements.size() == 1 && !elements.get(0).isTask())
            ret = true;

        return ret;
    }
}