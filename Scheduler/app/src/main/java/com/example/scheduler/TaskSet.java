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

    public void toVisualize(Task t, VisualizeSet v) {

        if (v.getNumberOfElements() < 2){   // c'è solo il msg_no_task

            String MeseAnno = t.getMonth() + " " + t.getYear();
            String GiornoNum = t.getDay() + " " + t.getNumDay();
            String att = "- " + t.getDescription();

            v.deleteAll();
            v.add(MeseAnno);
            v.add(GiornoNum);
            v.add(att);
        }
        else{   // c'è già almeno un task mostrato

            // cerca il punto in cui metterlo cronologicamente
            // ...
        }
    }
}