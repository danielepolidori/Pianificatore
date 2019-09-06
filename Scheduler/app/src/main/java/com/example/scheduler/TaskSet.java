package com.example.scheduler;

import java.util.ArrayList;
import java.util.List;

public class TaskSet extends DataSet<Task> {

    //private List<Task> elements;

    public TaskSet(){

        super();
        //elements = new ArrayList<Task>();
    }

    public void addTask(Task t, VisualizeSet vs) {

        int i;
        boolean aggiunto = false, giaPresente = false;

        if (elements.size() < 1){

            elements.add(t);

            aggiunto = true;
        }
        else{

            for(i = 0; i < elements.size() && !aggiunto && !giaPresente; i++){

                Task currEl = elements.get(i);

                if (currEl.getId() == t.getId()){

                    giaPresente = true;
                }
                else{

                    if(currEl.getDateHour().equals(t.getDateHour()) || currEl.getDateHour().after(t.getDateHour())){

                        elements.add(i, t);

                        aggiunto = true;
                    }
                }
            }

            if (!aggiunto && !giaPresente){

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

    /*
    // Aggiunge elemento in coda
    public void add(Task element) {

        elements.add(element);
    }

    // Inserisce 'element' nella posizione 'index' e sposta tutti gli elementi, da 'index' in poi, di una posizione
    public void addIn(Task element, int index) {

        elements.add(index, element);
    }

    public void delete(int index) {

        elements.remove(index);
    }

    public void deleteAll() {

        elements.clear();
    }

    public int getNumberOfElements() {

        return elements.size();
    }

    public List<Task> getElements() {

        return elements;
    }

    public Task getElement(int index) {

        return elements.get(index);
    }

    public boolean isEmpty() {

        return (elements.size() < 1);
    }
    */
}