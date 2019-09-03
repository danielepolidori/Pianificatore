package com.example.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Task {

    public enum statoTask {
        PENDING,
        ONGOING,
        COMPLETED
    }

    private String descrizione;
    private Date data_ora;
    private SimpleDateFormat sdf;
    private String strData;
    private String[] dateTokens;
    private int priorita;
    private String classe;
    private statoTask stato;
    // ...

    public Task(String des, Date dat, int p, String c){

        this.descrizione = des;
        this.data_ora = dat;
        this.sdf = new SimpleDateFormat("EEEE d MMM yyyy", Locale.ITALIAN);
        this.strData = sdf.format(data_ora);  // es: "venerdì 30 ago 2019"
        this.dateTokens = strData.split(" ");
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






