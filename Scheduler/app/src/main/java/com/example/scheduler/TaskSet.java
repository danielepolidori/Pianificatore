package com.example.scheduler;

public class TaskSet extends DataSet<Task> {


    public TaskSet(){

        super();
    }

    public void addTask(Task t, VisualizeSet vs) {

        boolean giaPresente = false;

        for (Task el : elements){

            if (el.getId() == t.getId())
                giaPresente = true;
        }

        if (!giaPresente) {

            elements.add(t);

            vs.toVisualizeAdd(t);
        }
    }

    public void delTask(int id_t, VisualizeSet vs) {

        int i;
        boolean eliminato = false;

        for(i = 0; i < elements.size() && !eliminato; i++){

            Task currEl = elements.get(i);

            if(currEl.getId() == id_t){

                elements.remove(i);

                vs.toVisualizeDel(id_t, elements.size());

                eliminato = true;
            }
        }
    }
}