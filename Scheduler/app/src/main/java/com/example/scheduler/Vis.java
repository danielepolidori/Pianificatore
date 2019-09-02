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
    private Date data_ora;
    private tipoVis tipo;
    private String[] textTokens;    // DATA -> textTokens[0]: giorno, textTokens[1]: numGiorno, textTokens[2]: trattino, textTokens[3]: mese, textTokens[4]: anno

    public Vis(String text, tipoVis type) {

        this.testo = text;
        this.tipo = type;
        this.textTokens = testo.split(" ");
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
}