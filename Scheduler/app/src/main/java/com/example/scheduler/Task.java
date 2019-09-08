package com.example.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import io.realm.RealmObject;

public class Task extends RealmObject {

    public enum priorTask {
        ALTA,   // 0
        MEDIA,  // 1
        BASSA   // 2
    }

    public enum classeTask {
        FAMIGLIA,       // 0
        LAVORO,         // 1
        TEMPO_LIBERO,   // 2
        ALTRO           // 3
    }

    public enum statoTask {
        PENDING,    // 0
        ONGOING,    // 1
        COMPLETED   // 2
    }

    private int id;
    private String descrizione;
    private Date data_ora;
    private int priorita;
    private int classe;
    private int stato;
    // ...

    public Task(String des, Date dat_ora, priorTask p, classeTask c, int nIdentificativo){

        this.id = nIdentificativo;
        this.descrizione = des;
        this.data_ora = dat_ora;
        this.stato = 0;

        switch (p){

            case ALTA:
                this.priorita = 0;

            case MEDIA:
                this.priorita = 1;

            default:    // case BASSA e default
                this.priorita = 2;
        }

        switch (c){

            case FAMIGLIA:
                this.classe = 0;

            case LAVORO:
                this.classe = 1;

            case TEMPO_LIBERO:
                this.classe = 2;

            default:    // case ALTRO e default
                this.classe = 3;
        }

        // ...
    }

    // Costruttore vuoto, necessario per Realm
    public Task() {

        this.id = -1;
        this.descrizione = "";
        this.data_ora = new Date();
        this.priorita = -1;
        this.classe = -1;
        this.stato = -1;
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

        SimpleDateFormat sdf_only_data = new SimpleDateFormat("EEEE d MMM yyyy", Locale.ITALIAN);
        String strData_tmp = sdf_only_data.format(data_ora);  // es: "venerdì 30 ago 2019"
        Date onlyDate = new Date();     // Per non lasciarlo senza inizializzazione
        try {
            onlyDate = sdf_only_data.parse(strData_tmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return onlyDate;
    }

    public int getYear() {

        SimpleDateFormat sdf_data_ora = new SimpleDateFormat("EEEE d MMM yyyy HH mm", Locale.ITALIAN);
        String strDataOra = sdf_data_ora.format(data_ora);  // es: "venerdì 30 ago 2019 15 30"
        String[] dataOraTokens = strDataOra.split(" ");

        return Integer.parseInt(dataOraTokens[3]);
    }

    public String getMonth() {

        SimpleDateFormat sdf_data_ora = new SimpleDateFormat("EEEE d MMM yyyy HH mm", Locale.ITALIAN);
        String strDataOra = sdf_data_ora.format(data_ora);  // es: "venerdì 30 ago 2019 15 30"
        String[] dataOraTokens = strDataOra.split(" ");

        return dataOraTokens[2].toUpperCase();
    }

    public String getDay() {

        SimpleDateFormat sdf_data_ora = new SimpleDateFormat("EEEE d MMM yyyy HH mm", Locale.ITALIAN);
        String strDataOra = sdf_data_ora.format(data_ora);  // es: "venerdì 30 ago 2019 15 30"
        String[] dataOraTokens = strDataOra.split(" ");

        return (dataOraTokens[0].substring(0,1).toUpperCase() + dataOraTokens[0].substring(1));
    }

    public int getNumDay() {

        SimpleDateFormat sdf_data_ora = new SimpleDateFormat("EEEE d MMM yyyy HH mm", Locale.ITALIAN);
        String strDataOra = sdf_data_ora.format(data_ora);  // es: "venerdì 30 ago 2019 15 30"
        String[] dataOraTokens = strDataOra.split(" ");

        return Integer.parseInt(dataOraTokens[1]);
    }

    public priorTask getPrior() {

        priorTask p;

        switch (priorita){

            case 0:
                p = priorTask.ALTA;

            case 1:
                p = priorTask.MEDIA;

            default:    // case 2 e default
                p = priorTask.BASSA;
        }

        return p;
    }

    public int getPriorToStore() {

        return priorita;
    }

    public classeTask getClasse() {

        classeTask c;

        switch (classe){

            case 0:
                c = classeTask.FAMIGLIA;

            case 1:
                c = classeTask.LAVORO;

            case 2:
                c = classeTask.TEMPO_LIBERO;

            default:    // case 3 e default
                c = classeTask.ALTRO;
        }

        return c;
    }

    public int getClasseToStore() {

        return classe;
    }

    public statoTask getStato() {

        statoTask s;

        switch (stato){

            case 0:
                s = statoTask.PENDING;

            case 1:
                s = statoTask.ONGOING;

            default:    // case 2 e default
                s = statoTask.COMPLETED;
        }

        return s;
    }

    public void setId(int newId) {

        this.id = newId;
    }

    public void setDesc(String newDescrizione) {

        this.descrizione = newDescrizione;
    }

    public void setDateHour(Date newDataOra) {

        this.data_ora = newDataOra;
    }

    public void setPrior(int newPriorita) {

        this.priorita = newPriorita;
    }

    public void setClasse(int newClasse) {

        this.classe = newClasse;
    }

    public void setStato(statoTask newStato) {

        int s;

        switch (newStato){

            case PENDING:
                s = 0;

            case ONGOING:
                s = 1;

            default:    // case COMPLETED o default
                s = 2;
        }

        this.stato = s;
    }
}






