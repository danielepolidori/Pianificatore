package com.example.scheduler;

import java.util.Date;

public class Task {

    public enum statoTask {
        PENDING,
        ONGOING,
        COMPLETED
    }

    private String descrizione;
    private Date data_ora;
    String[] dateTokens;
    private int priorita;
    private String classe;
    private statoTask stato;
    // ...

    public Task(String des, Date dat, int p, String c){

        this.descrizione = des;
        this.data_ora = dat;
        this.dateTokens = data_ora.toString().split(" ");
        this.priorita = p;
        this.classe = c;
        this.stato = statoTask.PENDING;
        // ...
    }

    //.... get e set

    public String getDescription() {

        return descrizione;
    }

    public Date getDate() {

        return data_ora;
    }

    public int getYear() {

        return Integer.parseInt(dateTokens[3]);
    }

    public String getMonth() {

        return dateTokens[2].toUpperCase();
    }

    public String getDay() {

        return (dateTokens[0].substring(0,1).toUpperCase() + dateTokens[0].substring(1));
    }

    public int getNumDay() {

        return Integer.parseInt(dateTokens[1]);
    }
}






