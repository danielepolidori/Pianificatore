package com.example.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Task {

    public enum priorTask {
        ALTA,
        MEDIA,
        BASSA
    }

    public enum classeTask {
        FAMIGLIA,
        LAVORO,
        TEMPO_LIBERO,
        ALTRO
    }

    public enum statoTask {
        PENDING,
        ONGOING,
        COMPLETED
    }

    private int id;
    private String descrizione;
    private Date data_ora;
    private priorTask priorita;
    private classeTask classe;
    private statoTask stato;

    private SimpleDateFormat sdf;
    private String strData;
    private String[] dateTokens;

    // ...

    public Task(String des, Date dat_ora, priorTask p, classeTask c, int nIdentificativo){

        this.id = nIdentificativo;
        this.descrizione = des;
        this.data_ora = dat_ora;
        this.sdf = new SimpleDateFormat("EEEE d MMM yyyy HH mm", Locale.ITALIAN);
        this.strData = sdf.format(data_ora);  // es: "venerdì 30 ago 2019 15 30"
        this.dateTokens = strData.split(" ");
        this.priorita = p;
        this.classe = c;
        this.stato = statoTask.PENDING;
        // ...
    }

    //.... get e set

    public int getId() {

        return id;
    }

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






