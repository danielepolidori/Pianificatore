package com.example.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import io.realm.RealmObject;

public class Vis {

    public enum tipoVis {
        DATA,           // 0
        ATTIVITA,       // 1
        RIGA_VUOTA,     // 2
        MSG_NO_TASK,    // 3
        TMP             // 4
    }

    private String testo;
    private tipoVis tipo;
    //private int tipo;
    private Date data_ora;
    private int idTask;

    private String[] textTokens;    // DATA -> textTokens[0]: giorno, textTokens[1]: numGiorno, textTokens[2]: trattino, textTokens[3]: mese, textTokens[4]: anno

    private SimpleDateFormat sdf_only_data;
    private String strData_tmp;
    private Date only_data;

    // Costruttore per RIGA_VUOTA e MSG_NO_TASK
    public Vis(String text, tipoVis type) {

        this.testo = text;
        this.data_ora = new Date();     // Soltanto per non lasciarlo non inizializzato, non verrà mai usato
        this.textTokens = testo.split(" ");
        this.idTask = -1;

        //this.sdf_only_data = new SimpleDateFormat("EEEE d MMM yyyy", Locale.ITALIAN);   // Soltanto per non lasciarlo non inizializzato, non verrà mai usato
        this.strData_tmp = "";                                                                  // Soltanto per non lasciarlo non inizializzato, non verrà mai usato
        this.only_data = new Date();                                                            // Soltanto per non lasciarlo non inizializzato, non verrà mai usato

        this.tipo = type;
        /*
        switch (type){

            case RIGA_VUOTA:
                this.tipo = 2;

            case MSG_NO_TASK:
                this.tipo = 3;

            default:    // non deve mai capitare, è errore
                this.tipo = -1;
        }*/

        // ...
    }

    // Costruttore per DATA
    public Vis(String text, Date dataOraTask) {

        this.testo = text;
        this.tipo = tipoVis.DATA;
        this.data_ora = dataOraTask;
        this.textTokens = testo.split(" ");
        this.idTask = -1;

        this.sdf_only_data = new SimpleDateFormat("EEEE d MMM yyyy", Locale.ITALIAN);
        this.strData_tmp = sdf_only_data.format(data_ora);  // es: "venerdì 30 ago 2019"
        try {
            this.only_data = sdf_only_data.parse(strData_tmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // ...
    }

    // Costruttore per ATTIVITA
    public Vis(String text, Date dataOraTask, int nId) {

        this.testo = text;
        this.tipo = tipoVis.ATTIVITA;
        this.data_ora = dataOraTask;
        this.textTokens = testo.split(" ");
        this.idTask = nId;

        this.sdf_only_data = new SimpleDateFormat("EEEE d MMM yyyy", Locale.ITALIAN);
        this.strData_tmp = sdf_only_data.format(data_ora);  // es: "venerdì 30 ago 2019"
        try {
            this.only_data = sdf_only_data.parse(strData_tmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // ...
    }

    /*
    // Costruttore senza argomenti
    public Vis() {

        this.testo = "";
        this.tipo = 4;
        this.data_ora = new Date();     // Soltanto per non lasciarlo non inizializzato, non verrà mai usato
        //this.textTokens = testo.split(" ");
        this.idTask = -1;

        //this.sdf_only_data = new SimpleDateFormat("EEEE d MMM yyyy", Locale.ITALIAN);   // Soltanto per non lasciarlo non inizializzato, non verrà mai usato
        this.strData_tmp = "";                                                                  // Soltanto per non lasciarlo non inizializzato, non verrà mai usato
        this.only_data = new Date();                                                            // Soltanto per non lasciarlo non inizializzato, non verrà mai usato

        // ...
    }*/

    //.... get e set

    public String getText() {

        return testo;
    }

    public String getTextAtt() {

        String ret = "[errore]";

        if (tipo == tipoVis.ATTIVITA)
            ret = textTokens[1];

        return ret;
    }

    public tipoVis getType() {
/*
        tipoVis tv;

        switch (tipo){

            case 0:
                tv = tipoVis.DATA;

            case 1:
                tv = tipoVis.ATTIVITA;

            case 2:
                tv = tipoVis.RIGA_VUOTA;

            case 3:
                tv = tipoVis.MSG_NO_TASK;

            default:    // case 4 o default
                tv = tipoVis.TMP;
        }

        return tv;*/

        return tipo;
    }

    public Date getDateHour() {

        return data_ora;
    }

    public Date getOnlyDate() {

        return only_data;
    }

    public int getYear() {

        int ret = -1;

        if (tipo == tipoVis.DATA)
            ret = Integer.parseInt(textTokens[4]);

        return ret;
    }

    public String getMonth() {

        String ret = "[errore]";

        if (tipo == tipoVis.DATA)
            ret = textTokens[3];

        return ret;
    }

    public int getNumDay() {

        int ret = -1;

        if (tipo == tipoVis.DATA)
            ret = Integer.parseInt(textTokens[1]);

        return ret;
    }

    public int getIdTask() {

        return idTask;
    }

    public String[] getTextTokens() {

        return textTokens;
    }

    public SimpleDateFormat getSdfOnlyData() {

        return sdf_only_data;
    }

    public String getDataTmp() {

        return strData_tmp;
    }

    public void setText(String newText) {

        testo = newText;
    }

    public void setType(tipoVis newType) {

        tipo = newType;
    }

    public void setDateHour(Date newDateHour) {

        data_ora = newDateHour;
    }

    public void setIdTask(int newId) {

        idTask = newId;
    }

    public void setTextTokens(String[] newTextTokens) {

        textTokens = newTextTokens;
    }

    public void setSdfOnlyData(SimpleDateFormat newSdfOnlyData) {

        sdf_only_data = newSdfOnlyData;
    }

    public void setStrDataTmp(String newStrDataTmp) {

        strData_tmp = newStrDataTmp;
    }

    public void setOnlyDate(Date newOnlyDate) {

        only_data = newOnlyDate;
    }
}