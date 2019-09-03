package com.example.scheduler;

import android.content.res.Resources;

public class VisualizeSet extends DataSet <Vis> {

    private boolean is_msgNoTask;

    public VisualizeSet(){

        super();

        init();

        this.is_msgNoTask = true;
    }

    protected void init() {

        String i = Resources.getSystem().getString(R.string.msg_no_task);

        Vis vi = new Vis(i, Vis.tipoVis.MSG_NO_TASK);
        Vis vv = new Vis(Resources.getSystem().getString(R.string.vuota), Vis.tipoVis.RIGA_VUOTA);

        elements.add(vv);
        elements.add(vi);
    }

    public boolean isMsgNoTask(){

        return is_msgNoTask;
    }

    private void addFirstTaskToVis(Vis data, Vis attivita, Vis vuota) {

        elements.clear();
        elements.add(vuota);
        elements.add(data);
        elements.add(vuota);
        elements.add(attivita);
        elements.add(vuota);
        elements.add(vuota);
        elements.add(vuota);
    }

    private void addTaskToVis(Vis data, Vis attivita, Vis vuota, int indice) {

        elements.add(indice++, data);
        elements.add(indice++, vuota);
        elements.add(indice++, attivita);
        elements.add(indice++, vuota);
        elements.add(indice++, vuota);
        elements.add(indice, vuota);
    }

    private void addFinalTaskToVis(Vis data, Vis attivita, Vis vuota) {

        elements.add(data);
        elements.add(vuota);
        elements.add(attivita);
        elements.add(vuota);
        elements.add(vuota);
        elements.add(vuota);
    }

    protected void toVisualizeAdd(Task t) {

        String dat = t.getDay() + " " + t.getNumDay() + " - " + t.getMonth() + " " + t.getYear();
        String att = "- " + t.getDescription();

        Vis d = new Vis(dat, Vis.tipoVis.DATA);
        Vis a = new Vis(att, Vis.tipoVis.ATTIVITA);
        Vis vv = new Vis(Resources.getSystem().getString(R.string.vuota), Vis.tipoVis.RIGA_VUOTA);

        if (is_msgNoTask) {   // c'è solo il msg_no_task

            addFirstTaskToVis(d, a, vv);
        }
        else{   // c'è già almeno un task mostrato, allora cerca il punto in cui inserirlo cronologicamente

            int i, j;
            boolean stop = false;

            for(i = 0; i < elements.size() && !stop; i++) {

                Vis curr_i = elements.get(i);

                if (curr_i.getType() == Vis.tipoVis.DATA && t.getDate().equals(curr_i.getDate())){        // allora inserisci il task, fra gli altri già presenti, nel punto giusto secondo l'ora

                    for(j = i+1; j < elements.size() && !stop; j++){

                        Vis curr_j = elements.get(j);

                        if(curr_j.getType() == Vis.tipoVis.ATTIVITA && (t.getDate().equals(curr_j.getDate()) || t.getDate().before(curr_j.getDate()))){

                            elements.add(j, a);

                            stop = true;
                        }
                    }
                }
                else if (t.getDate().before(curr_i.getDate())){   // allora crea un nuovo giorno con l'attività

                    addTaskToVis(d, a, vv, i);

                    stop = true;
                }
            }

            if (!stop)
                addFinalTaskToVis(d, a, vv);
        }
    }

    // Cancella l'intero giorno con il task dalla visualizzazione
    private void delDaySingleTask(int ind) {

        elements.remove(elements.get(ind-2));       // prec_prec		DATA		j-2
        elements.remove(elements.get(ind-1));       // prec			    VUOTA		j-1
        elements.remove(elements.get(ind));         // curr			    ATT		    j
        elements.remove(elements.get(ind+1));       // suc			    VUOTA		j+1
        elements.remove(elements.get(ind+2));       // suc_suc			VUOTA		j+2
        elements.remove(elements.get(ind+3));       // suc_suc_suc		VUOTA		j+3
    }

    protected void toVisualizeDel(Task t, int sizeTaskSet) {

        if (sizeTaskSet < 1){

            elements.clear();
            init();
        }
        else{

            int i, j;
            boolean stop = false;

            for(i = 0; i < elements.size() && !stop; i++) {

                Vis curr_i = elements.get(i);

                if (curr_i.getType() == Vis.tipoVis.DATA && t.getDate().equals(curr_i.getDate())){        // allora cerca il task, secondo l'ora, fra gli altri già presenti ed eliminalo

                    for(j = i+1; j < elements.size() && !stop; j++){

                        Vis curr_j = elements.get(j);

                        if(curr_j.getType() == Vis.tipoVis.ATTIVITA && t.getDescription().equals(curr_j.getTextAtt())){

                            Vis prec = elements.get(j-1);
                            Vis succ = elements.get(j+1);

                            if (prec.getType() == Vis.tipoVis.ATTIVITA || succ.getType() == Vis.tipoVis.ATTIVITA)
                                elements.remove(curr_j);          // caso base: cancella il task
                            else
                                delDaySingleTask(j);     // caso speciale: cancella l'intero giorno con un singolo task

                            stop = true;
                        }
                    }
                }
            }
        }
    }
}