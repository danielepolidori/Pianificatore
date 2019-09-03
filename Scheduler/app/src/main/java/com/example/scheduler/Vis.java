package com.example.scheduler;

import java.util.Date;

public class Vis {

    public enum tipoVis {
        DATA,
        ATTIVITA,
        RIGA_VUOTA,
        MSG_NO_TASK
    }

    private String testo;
    private tipoVis tipo;
    private Date data_ora;
    private int idTask;

    private String[] textTokens;    // DATA -> textTokens[0]: giorno, textTokens[1]: numGiorno, textTokens[2]: trattino, textTokens[3]: mese, textTokens[4]: anno

    // Costruttore per RIGA_VUOTA e MSG_NO_TASK
    public Vis(String text, tipoVis type) {

        this.testo = text;
        this.tipo = type;
        this.data_ora = new Date();     // Soltanto per non lasciarlo non inizializzato, non verrà mai usato
        this.textTokens = testo.split(" ");
        this.idTask = -1;
        // ...
    }

    // Costruttore per DATA
    public Vis(String text, Date dataTask) {

        this.testo = text;
        this.tipo = tipoVis.DATA;
        this.data_ora = dataTask;
        this.textTokens = testo.split(" ");
        this.idTask = -1;
        // ...
    }

    // Costruttore per ATTIVITA
    public Vis(String text, Date dataTask, int nId) {

        this.testo = text;
        this.tipo = tipoVis.ATTIVITA;
        this.data_ora = dataTask;
        this.textTokens = testo.split(" ");
        this.idTask = nId;
        // ...
    }

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

        return tipo;
    }

    public Date getDate() {

        return data_ora;
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
}