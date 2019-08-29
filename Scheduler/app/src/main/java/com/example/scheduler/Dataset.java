package com.example.scheduler;

import java.util.ArrayList;
import java.util.List;

public class Dataset {

    protected List<Task> elements;

    public Dataset() {

        elements = new ArrayList<Task>();
    }

    public void add(Task element){

        elements.add(element);
    }

    public void delete(Task element){

        elements.remove(element);
    }

    public int getNumberOfElements(){

        return elements.size();
    }

    public List<Task> getElements() {

        return elements;
    }

    public Task getElement(int index) {

        return elements.get(index);
    }
}