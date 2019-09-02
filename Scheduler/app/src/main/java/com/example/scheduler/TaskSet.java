package com.example.scheduler;

import android.content.res.Resources;

public class TaskSet extends DataSet <Task> {

    public TaskSet(){

        super();
    }

    private void addFirstTaskToVis(Vis data, Vis attivita, Vis vuota, VisualizeSet vis_set) {

        vis_set.deleteAll();
        vis_set.add(vuota);
        vis_set.add(data);
        vis_set.add(vuota);
        vis_set.add(attivita);
        vis_set.add(vuota);
        vis_set.add(vuota);
        vis_set.add(vuota);
    }

    private void addTaskToVis(Vis data, Vis attivita, Vis vuota, VisualizeSet vis_set, int indice) {

        vis_set.addIn(data, indice++);
        vis_set.addIn(vuota, indice++);
        vis_set.addIn(attivita, indice++);
        vis_set.addIn(vuota, indice++);
        vis_set.addIn(vuota, indice++);
        vis_set.addIn(vuota, indice);
    }

    private void addFinalTaskToVis(Vis data, Vis attivita, Vis vuota, VisualizeSet vis_set) {

        vis_set.add(data);
        vis_set.add(vuota);
        vis_set.add(attivita);
        vis_set.add(vuota);
        vis_set.add(vuota);
        vis_set.add(vuota);
    }

    private void toVisualizeAdd(Task t, VisualizeSet vs) {

        String dat = t.getDay() + " " + t.getNumDay() + " - " + t.getMonth() + " " + t.getYear();
        String att = "- " + t.getDescription();

        Vis d = new Vis(dat, Vis.tipoVis.DATA);
        Vis a = new Vis(att, Vis.tipoVis.ATTIVITA);
        Vis vv = new Vis(Resources.getSystem().getString(R.string.vuota), Vis.tipoVis.RIGA_VUOTA);

        if (vs.isMsgNoTask()) {   // c'è solo il msg_no_task

            addFirstTaskToVis(d, a, vv, vs);
        }
        else{   // c'è già almeno un task mostrato, allora cerca il punto in cui inserirlo cronologicamente

            int i, j;
            boolean stop = false;

            for(i = 0; i < vs.getNumberOfElements() && !stop; i++) {

                Vis curr_i = vs.getElement(i);

                if (curr_i.getType() == Vis.tipoVis.DATA && t.getDate().equals(curr_i.getDate())){        // allora inserisci il task, fra gli altri già presenti, nel punto giusto secondo l'ora

                    for(j = i+1; j < vs.getNumberOfElements() && !stop; j++){

                        Vis curr_j = vs.getElement(j);

                        if(curr_j.getType() == Vis.tipoVis.ATTIVITA && (t.getDate().equals(curr_j.getDate()) || t.getDate().before(curr_j.getDate()))){

                            vs.addIn(a, j);

                            stop = true;
                        }
                    }
                }
                else if (t.getDate().before(curr_i.getDate())){   // allora crea un nuovo giorno con l'attività

                    addTaskToVis(d, a, vv, vs, i);

                    stop = true;
                }
			}

            if (!stop)
                addFinalTaskToVis(d, a, vv, vs);
        }
    }

    public void addTask(Task t, VisualizeSet vs) {

        int i;
        boolean stop = false;

        for(i = 0; i < elements.size() && !stop; i++){

            Task currEl = elements.get(i);

            if(currEl.getDate().equals(t.getDate()) || currEl.getDate().after(t.getDate())){

                elements.add(i, t);

                stop = true;
            }
        }

        toVisualizeAdd(t, vs);
    }

    private void toVisualizeDel(Task t, VisualizeSet vs) {

        int i, j;
        boolean stop = false;

        for(i = 0; i < vs.getNumberOfElements() && !stop; i++) {

            Vis curr_i = vs.getElement(i);

            if (curr_i.getType() == Vis.tipoVis.DATA && t.getDate().equals(curr_i.getDate())){        // allora cerca il task, secondo l'ora, fra gli altri già presenti ed eliminalo

                for(j = i+1; j < vs.getNumberOfElements() && !stop; j++){

                    Vis curr_j = vs.getElement(j);

                    if(curr_j.getType() == Vis.tipoVis.ATTIVITA && t.getDescription().equals(curr_j.getTextAtt())){

                        vs.delete(curr_j);  // RIMANE LO SPAZIO VUOTO ???

                        stop = true;
                    }
                }
            }
        }
    }

    public void delTask(Task t, VisualizeSet vs) {

        int i;
        boolean stop = false;

        for(i = 0; i < elements.size() && !stop; i++){

            Task currEl = elements.get(i);

            if(currEl.getDescription().equals(t.getDescription())){

                elements.remove(i);

                stop = true;
            }
        }

        toVisualizeDel(t, vs);
    }
}