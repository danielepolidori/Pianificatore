package com.example.scheduler;

public class Task {

    protected boolean is_task;  // 1: task, 0: messaggio da mostrare nella home
    protected String descrizione;
    //protected ? data;
    //protected ? ora;
    //protected String luogo;
    protected int stato;    // 0: ..., 1: ..., 2: ...
    // ...

    public Task(String d, String l){

        this.is_task = true;
        this.descrizione = d;
        //this.luogo = l;
        this.stato = 0;
        // ...
    }

    /*
    public Task(String msg){

        this.is_task = false;
        this.descrizione = msg;
        this.luogo = "";
        this.stato = -1;
        // ...
    }
    */

    //.... get e set

    public String getDescription() {

        return descrizione;
    }

    public boolean isTask() {

        return is_task;
    }
}