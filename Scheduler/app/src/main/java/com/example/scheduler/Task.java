package com.example.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import io.realm.RealmObject;

public class Task extends RealmObject {

    private int id;
    private String descrizione;
    private Date data_ora;
    private int priorita;
    private int classe;
    private int stato;

    public Task(String des, Date dat_ora, int p, int c, int s, int nIdentificativo){

        this.id = nIdentificativo;
        this.descrizione = des;
        this.data_ora = dat_ora;
        this.priorita = p;
        this.classe = c;
        this.stato = s;
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

    public Date getOnlyOra() {

        SimpleDateFormat sdf_only_ora = new SimpleDateFormat("HH mm", Locale.ITALIAN);
        String strOra_tmp = sdf_only_ora.format(data_ora);
        Date onlyOra = new Date();     // Per non lasciarlo senza inizializzazione
        try {
            onlyOra = sdf_only_ora.parse(strOra_tmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return onlyOra;
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

    public int getMonth_int() {

        SimpleDateFormat sdf_data_ora = new SimpleDateFormat("EEEE d M yyyy HH mm", Locale.ITALIAN);
        String strDataOra = sdf_data_ora.format(data_ora);  // es: "venerdì 30 8 2019 15 30"
        String[] dataOraTokens = strDataOra.split(" ");

        return Integer.parseInt(dataOraTokens[2]);
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

    public int getPrior() {

        return priorita;
    }

    public String getPrior_string() {

        String ret = "";

        if (priorita == 0)
            ret = "Alta";
        else if (priorita == 1)
            ret = "Media";
        else if (priorita == 2)
            ret = "Bassa";
        else
            System.out.println("ERRORE_VALORE_PRIORITÀ_TASK");

        return ret;
    }

    public int getClasse() {

        return classe;
    }

    public String getClasse_string() {

        String ret = "";

        if (classe == 0)
            ret = "Famiglia";
        else if (classe == 1)
            ret = "Lavoro";
        else if (classe == 2)
            ret = "Tempo libero";
        else if (classe == 3)
            ret = "Altro";
        else
            System.out.println("ERRORE_VALORE_CLASSE_TASK");

        return ret;
    }

    public int getStato() {

        return stato;
    }

    public String getStato_string() {

        String ret = "";

        if (stato == 0)
            ret = "In attesa di essere svolta";
        else if (stato == 1)
            ret = "In corso";
        else if (stato == 2)
            ret = "Completata";
        else
            System.out.println("ERRORE_VALORE_STATO_TASK");

        return ret;
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

    public void setStato(int newStato) {

        this.stato = newStato;
    }
}