package com.example.scheduler;

public class Task {

    protected String descrizione;
    //protected ? data;
    //protected ? ora;
    protected String luogo;
    protected int stato;
    // ...

    public Task(String d, String l, int s){

        this.descrizione = d;
        this.luogo = l;
        this.stato = s;
        // ...
    }

    //.... get e set

    public String getDescription() {

        return descrizione;
    }
}