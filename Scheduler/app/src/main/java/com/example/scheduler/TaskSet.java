package com.example.scheduler;

public class TaskSet extends DataSet <Task> {

    public TaskSet(){

        super();
    }

    public void addTask(Task t, VisualizeSet vs) {

        int i;
        boolean aggiunto = false;

        if (elements.size() < 1){

            elements.add(t);

            aggiunto = true;
        }
        else{

            for(i = 0; i < elements.size() && !aggiunto; i++){

                Task currEl = elements.get(i);

                if(currEl.getDateHour().equals(t.getDateHour()) || currEl.getDateHour().after(t.getDateHour())){

                    elements.add(i, t);

                    aggiunto = true;
                }
            }

            if (!aggiunto){

                elements.add(t);

                aggiunto = true;
            }
        }

        if (aggiunto)
            vs.toVisualizeAdd(t);
    }

    public void delTask(int id_t, VisualizeSet vs) {

        int i;
        boolean eliminato = false;

        for(i = 0; i < elements.size() && !eliminato; i++){

            Task currEl = elements.get(i);

            if(currEl.getId() == id_t){

                elements.remove(i);

                eliminato = true;
            }
        }

        if (eliminato)
            vs.toVisualizeDel(id_t, elements.size());
    }
}