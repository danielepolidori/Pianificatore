package com.example.scheduler;

public class VisualizeSet extends DataSet <Vis> {

    protected boolean is_msgNoTask;

    public VisualizeSet(){

        super();

        this.is_msgNoTask = true;
    }

    public boolean isMsgNoTask(){

        return is_msgNoTask;
    }
}