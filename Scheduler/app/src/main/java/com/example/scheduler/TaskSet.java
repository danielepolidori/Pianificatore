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

    public VisualizeSet.tipoDel delTask(int id_t, VisualizeSet vs) {

        int i;
        boolean eliminato = false;
        VisualizeSet.tipoDel ret = VisualizeSet.tipoDel.TMP;

        for(i = 0; i < elements.size() && !eliminato; i++){

            Task currEl = elements.get(i);

            if(currEl.getId() == id_t){

                elements.remove(i);

                ret = vs.toVisualizeDel(id_t, elements.size());

                eliminato = true;
            }
        }

        return ret;
    }

    public Task getTask(int id) {

        Task ret = new Task();

        for (Task el : elements){

            if (el.getId() == id)
                ret = el;
        }

        return ret;
    }

    public boolean containsTask(int idTask) {

        boolean result = false;

        for (Task el : elements){

            if (el.getId() == idTask)
                result = true;
        }

        return result;
    }
}