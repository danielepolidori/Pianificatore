package com.example.scheduler;

import android.content.res.Resources;

public class TaskSet extends DataSet <Task> {

    public TaskSet(){

        super();
    }

    public boolean isMsg() {

        boolean ret = false;

        if (elements.size() == 1 && !elements.get(0).isTask())
            ret = true;

        return ret;
    }

    public void toVisualize(VisualizeSet v) {

        if (elements.size() > 0){

            if (!elements.get(0).is_task){

                v.deleteAll();
                v.add(Resources.getSystem().getString(R.string.msg_no_task));
            }
            else{

                // dai task ottieni le stringhe da mostrare
            }
        }

    }
}