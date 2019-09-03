package com.example.scheduler;

public class VisualizeSet extends DataSet <Vis> {

    private boolean is_msgNoTask;
    private String msgNoTask;

    public VisualizeSet(){

        super();

        this.is_msgNoTask = true;
        this.msgNoTask = "Nessuna attività ancora in programma.";
    }

    public void init() {

        Vis vi = new Vis(msgNoTask, Vis.tipoVis.MSG_NO_TASK);
        Vis vv = new Vis("", Vis.tipoVis.RIGA_VUOTA);

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
        String vuota = "~";

        Vis d = new Vis(dat, t.getDate());
        Vis a = new Vis(att, t.getDate(), t.getId());
        Vis vv = new Vis(vuota, Vis.tipoVis.RIGA_VUOTA);

        if (is_msgNoTask) {                 // c'è solo il msg_no_task

            addFirstTaskToVis(d, a, vv);

            is_msgNoTask = false;
        }
        else{                               // c'è già almeno un task mostrato, allora cerca il punto in cui inserirlo cronologicamente

            int i, j;
            boolean stop = false;

            for(i = 0; i < elements.size() && !stop; i++) {

                Vis curr_i = elements.get(i);

                if (curr_i.getType() == Vis.tipoVis.DATA){        // allora inserisci il task, fra gli altri già presenti, nel punto giusto secondo l'ora

                    if (t.getDate().equals(curr_i.getDate())) {

                        for (j = i + 1; j < elements.size() && !stop; j++) {

                            Vis curr_j = elements.get(j);

                            if (curr_j.getType() == Vis.tipoVis.ATTIVITA && (t.getDate().equals(curr_j.getDate()) || t.getDate().before(curr_j.getDate()))) {

                                elements.add(j, vv);
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
            }

            if (!stop)
                addFinalTaskToVis(d, a, vv);
        }
    }

    // Cancella l'intero giorno con il task dalla visualizzazione
    private void delDaySingleTask(int ind) {

        // stesso indice, perché dopo la rimozione di un elemento il successivo occupa il suo posto

        elements.remove(ind-2);       // prec_prec		DATA		j-2
        elements.remove(ind-2);       // curr		    ATT		    j
        elements.remove(ind-2);       // prec	        VUOTA		j-1
        elements.remove(ind-2);       // suc			    VUOTA		j+1
        elements.remove(ind-2);       // suc_suc			VUOTA		j+2
        elements.remove(ind-2);       // suc_suc_suc		VUOTA		j+3
    }

    protected void toVisualizeDel(int id_t, int sizeTaskSet) {

        if (sizeTaskSet < 1){

            elements.clear();
            init();

            is_msgNoTask = true;
        }
        else{

            int i;
            boolean cancellato = false;

            for(i = 0; i < elements.size() && !cancellato; i++) {

                Vis curr_i = elements.get(i);

                if (curr_i.getType() == Vis.tipoVis.ATTIVITA && curr_i.getIdTask() == id_t){

                    Vis prec = elements.get(i-2);
                    Vis succ = elements.get(i+2);

                    if (prec.getType() == Vis.tipoVis.ATTIVITA || succ.getType() == Vis.tipoVis.ATTIVITA) {

                        // caso base: cancella il task e una riga vuota seguente

                        elements.remove(i);                // per cancellare il task
                        elements.remove(i);       // per cancellare la riga vuota (stesso indice, perché dopo la rimozione del task la riga vuota ha occupato il suo posto)
                    }
                    else {

                        delDaySingleTask(i);     // caso speciale: cancella l'intero giorno contenente un singolo task
                    }

                    cancellato = true;
                }
            }
        }
    }
}