package com.example.scheduler;

import android.provider.ContactsContract;

import java.util.Date;

public class Task {

    protected String descrizione;
    protected Date data_ora;
    protected int priorita;
    protected String classe;
    protected int stato;    // 0: pending, 1: ongoing, 2: completed
    // ...

    public Task(String d, int p, String c){

        this.descrizione = d;
        this.priorita = p;
        this.classe = c;
        this.stato = 0;
        // ...
    }

    //.... get e set

    public String getDescription() {

        return descrizione;
    }

    public Date getDate() {

        return data_ora;
    }

    /*
    public int getYear() {

        // ...
    }

    public String getMonth() {

        // ...
    }

    public String getDay() {

        // ...
    }

    public int getNumDay() {

        // ...
    }
    */
}