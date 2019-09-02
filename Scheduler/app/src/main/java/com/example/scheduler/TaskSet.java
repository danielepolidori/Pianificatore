package com.example.scheduler;

import android.content.res.Resources;

public class TaskSet extends DataSet <Task> {

    public TaskSet(){

        super();
    }

    public void toVisualizeAdd(Task t, VisualizeSet vs) {

        String data = t.getDay() + " " + t.getNumDay() + " - " + t.getMonth() + " " + t.getYear();
        String att = "- " + t.getDescription();

        Vis d = new Vis(data, 1);
        Vis a = new Vis(att, 2);
        Vis vv = new Vis(Resources.getSystem().getString(R.string.vuota), 0);

        if (vs.isMsgNoTask()){   // c'è solo il msg_no_task

            vs.deleteAll();
            vs.add(vv);
            vs.add(d);
            vs.add(vv);
            vs.add(a);
            vs.add(vv);
            vs.add(vv);
            vs.add(vv);
        }
        else{   // c'è già almeno un task mostrato, allora cerca il punto in cui inserirlo cronologicamente

            int i, j;
            boolean stop_i, stop_j;

            stop_i = false;
            stop_j = false;

            for(i = 0; i < vs.getNumberOfElements() && !stop_i; i++) {

                Vis curr_i = vs.getElement(i);

                if (curr_i.getType() == 1){      // se l'elemento è una data

                    if (t.getDate().equals(curr_i.getDate())){        // allora inserisci il task, fra gli altri già presenti, nel punto giusto secondo l'ora

                        for(j = i+1; j < vs.getNumberOfElements() && !stop_j; j++){

                            Vis curr_j = vs.getElement(j);

                            if(curr_j.getType() == 2){

                                if(t.getDate().equals(curr_j.getDate()) || t.getDate().before(curr_j.getDate())){

                                    vs.addIn(a, j);

                                    stop_j = true;
                                    stop_i = true;
                                }
                            }
                        }
                    }
                    else if (t.getDate().before(curr_i.getDate())){   // allora crea un nuovo giorno con l'attività

                        vs.addIn(d, i++);
                        vs.addIn(vv, i++);
                        vs.addIn(a, i++);
                        vs.addIn(vv, i++);
                        vs.addIn(vv, i++);
                        vs.addIn(vv, i);

                        stop_i = true;
                    }
                }
			}
        }
    }
}