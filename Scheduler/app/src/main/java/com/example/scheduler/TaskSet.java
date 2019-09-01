package com.example.scheduler;

import java.util.Iterator;

public class TaskSet extends DataSet <Task> {

    public TaskSet(){

        super();
    }

    public void toVisualize(Task t, VisualizeSet vs) {

        if (vs.getNumberOfElements() < 2){   // c'è solo il msg_no_task

            String MeseAnno = t.getMonth() + " " + t.getYear();
            String GiornoNum = t.getDay() + " " + t.getNumDay();
            String att = "- " + t.getDescription();

            Vis m_a = new Vis(MeseAnno, 0);
            Vis g_n = new Vis(GiornoNum, 1);
            Vis a = new Vis(att, 2);

            vs.deleteAll();
            vs.add(m_a);
            vs.add(g_n);
            vs.add(a);
        }
        else{   // c'è già almeno un task mostrato

            /*
            int i;
            for (i = 0; i < vs.getNumberOfElements(); i++){

                if (vs.getElement(0).getType() == 0){

                    if (vs.getElement(0).getYear() == t.getYear())
                }

            }
            */

            /*
            // e il primo elemento della lista?
            Iterator<Vis> iterator = vs.getElements().iterator();
            while(iterator.hasNext()){

                Vis v = iterator.next();

                // ...
            }
            */

            for(Vis v : vs) {

            	// ...
			}
        }
    }
}