package com.example.scheduler;

public class VisualizeSet extends DataSet<Vis> {

    public enum tipoDel {
        SINGOLO_TASK,
        GIORNO,
        ULTIMO_TASK,
        TMP
    }

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
        String att = "- " + t.getDescription() + ".";
        String vuota = "";

        Vis d = new Vis(dat, t.getDateHour());
        Vis a = new Vis(att, t.getDateHour(), t.getId());
        Vis vv = new Vis(vuota, Vis.tipoVis.RIGA_VUOTA);

        if (is_msgNoTask) {                 // c'è solo il msg_no_task

            addFirstTaskToVis(d, a, vv);

            is_msgNoTask = false;
        }
        else{                               // c'è già almeno un task mostrato, allora cerca il punto in cui inserirlo cronologicamente

            int i, j;
            boolean added = false;

            for(i = 0; i < elements.size() && !added; i++) {

                Vis curr_i = elements.get(i);

                if (curr_i.getType() == Vis.tipoVis.DATA){        // allora inserisci il task, fra gli altri già presenti, nel punto giusto secondo l'ora

                    System.out.println(t.getOnlyDate().equals(curr_i.getOnlyDate()));

                    if (t.getOnlyDate().equals(curr_i.getOnlyDate())) {

                        for (j = i + 1; j < elements.size() && !added; j++) {

                            Vis curr_j = elements.get(j);
                            Vis succ_j = elements.get(j + 2);

                            if (curr_j.getType() == Vis.tipoVis.ATTIVITA){

                                if (t.getDateHour().equals(curr_j.getDateHour()) || t.getDateHour().before(curr_j.getDateHour())){

                                    elements.add(j, vv);
                                    elements.add(j, a);

                                    added = true;
                                }
                                else if (succ_j.getType() == Vis.tipoVis.RIGA_VUOTA){

                                    elements.add(j+1, a);
                                    elements.add(j+1, vv);

                                    added = true;
                                }
                            }
                        }
                    }
                    else if (t.getOnlyDate().before(curr_i.getOnlyDate())){   // allora crea un nuovo giorno con l'attività

                        addTaskToVis(d, a, vv, i);

                        added = true;
                    }
                }
            }

            if (!added)
                addFinalTaskToVis(d, a, vv);
        }
    }

    // Cancella l'intero giorno con il task dalla visualizzazione
    private void delDaySingleTask(int ind) {

        // stesso indice, perché dopo la rimozione di un elemento il successivo occupa il suo posto

        elements.remove(ind-2);       // prec_prec		DATA		j-2
        elements.remove(ind-2);       // prec	        VUOTA		j-1
        elements.remove(ind-2);       // curr		    ATT		    j
        elements.remove(ind-2);       // suc			    VUOTA		j+1
        elements.remove(ind-2);       // suc_suc			VUOTA		j+2
        elements.remove(ind-2);       // suc_suc_suc		VUOTA		j+3
    }

    protected tipoDel toVisualizeDel(int id_t, int sizeTaskSet) {

        tipoDel ret = tipoDel.TMP;

        if (sizeTaskSet < 1){

            elements.clear();
            init();

            is_msgNoTask = true;

            ret = tipoDel.ULTIMO_TASK;
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

                        elements.remove(i);         // per cancellare il task
                        elements.remove(i);         // per cancellare la riga vuota (stesso indice, perché dopo la rimozione del task la riga vuota ha occupato il suo posto)

                        ret = tipoDel.SINGOLO_TASK;
                    }
                    else {

                        delDaySingleTask(i);     // caso speciale: cancella l'intero giorno contenente un singolo task

                        ret = tipoDel.GIORNO;
                    }

                    cancellato = true;
                }
            }
        }

        return ret;
    }
}