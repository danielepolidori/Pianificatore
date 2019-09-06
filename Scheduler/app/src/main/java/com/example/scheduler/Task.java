package com.example.scheduler;

import java.text.ParseException;
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

    private SimpleDateFormat sdf_data_ora;
    private String strDataOra;
    private String[] dataOraTokens;

    private SimpleDateFormat sdf_only_data;
    private String strData_tmp;
    private Date onlyDate;

    // ...

    public Task(String des, Date dat_ora, priorTask p, classeTask c, int nIdentificativo){

        this.id = nIdentificativo;
        this.descrizione = des;
        this.data_ora = dat_ora;
        this.priorita = p;
        this.classe = c;
        this.stato = statoTask.PENDING;

        this.sdf_data_ora = new SimpleDateFormat("EEEE d MMM yyyy HH mm", Locale.ITALIAN);
        this.strDataOra = sdf_data_ora.format(data_ora);  // es: "venerdì 30 ago 2019 15 30"
        this.dataOraTokens = strDataOra.split(" ");

        this.sdf_only_data = new SimpleDateFormat("EEEE d MMM yyyy", Locale.ITALIAN);
        this.strData_tmp = sdf_only_data.format(data_ora);  // es: "venerdì 30 ago 2019"
        try {
            this.onlyDate = sdf_only_data.parse(strData_tmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("DateHour: " + strDataOra.toString());
        System.out.println("onlyDate: " + strData_tmp.toString());

        // ...
    }

    //.... get e set

    public int getId() {

        return id;
    }

    public String getDescription() {

        return descrizione;
    }

    public Date getDateHour() {

        return data_ora;
    }

    public Date getOnlyDate() {

        return onlyDate;
    }

    public int getYear() {

        return Integer.parseInt(dataOraTokens[3]);
    }

    public String getMonth() {

        return dataOraTokens[2].toUpperCase();
    }

    public String getDay() {

        return (dataOraTokens[0].substring(0,1).toUpperCase() + dataOraTokens[0].substring(1));
    }

    public int getNumDay() {

        return Integer.parseInt(dataOraTokens[1]);
    }
}






