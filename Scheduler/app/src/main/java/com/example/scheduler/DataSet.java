package com.example.scheduler;

import java.util.ArrayList;
import java.util.List;

public abstract class DataSet<T> {

    protected List<T> elements;

    public DataSet(){

        elements = new ArrayList<T>();
    }

    // Aggiunge elemento in coda
    public void add(T element) {

        elements.add(element);
    }

    // Inserisce 'element' nella posizione 'index' e sposta tutti gli elementi, da 'index' in poi, di una posizione
    public void addIn(T element, int index) {

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
