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

                if(currEl.getDate().equals(t.getDate()) || currEl.getDate().after(t.getDate())){

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

    public void delTask(Task t, VisualizeSet vs) {

        int i;
        boolean eliminato = false;

        for(i = 0; i < elements.size() && !eliminato; i++){

            Task currEl = elements.get(i);

            if(currEl.getDescription().equals(t.getDescription())){

                elements.remove(i);

                eliminato = true;
            }
        }

        if (eliminato)
            vs.toVisualizeDel(t, elements.size());
    }
}