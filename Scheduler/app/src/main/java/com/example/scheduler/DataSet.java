package com.example.scheduler;

import java.util.ArrayList;
import java.util.List;

public abstract class DataSet<T> {

    protected List<T> elements;

    public DataSet(){

        elements = new ArrayList<T>();
    }

    // sarà abstract: task in ordine cronologico e vis in ordine di stampa
    public void add(T element) {

        //elements.add(element);
    }

    public void delete(T element) {

        elements.remove(element);
    }

    public void deleteAll() {

        elements.clear();
    }

    public int getNumberOfElements() {

        return elements.size();
    }

    public List<T> getElements() {

        return elements;
    }

    public T getElement(int index) {

        return elements.get(index);
    }

    public boolean isEmpty() {

        return (elements.size() < 1);
    }
}
