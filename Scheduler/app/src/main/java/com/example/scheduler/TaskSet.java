package com.example.scheduler;

public class TaskSet extends DataSet <Task> {

    public TaskSet(){

        super();
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

        vs.toVisualizeAdd(t);
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

        vs.toVisualizeDel(t, elements.size());
    }
}