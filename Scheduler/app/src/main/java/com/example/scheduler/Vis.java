package com.example.scheduler;

public class Vis {

    protected String testo;
    protected int tipo;     // 0: mese&anno, 1: giorno&num, 2: singola attività
    private String[] tokens;

    public Vis(String text, int type) {

        this.testo = text;
        this.tipo = type;
        this.tokens = testo.split(" ");
        // ...
    }

    public String getText() {

        return testo;
    }

    public int getType() {

        return tipo;
    }

    public int getYear() {

        int ret = -1;

        if (tipo == 0)
            ret = Integer.parseInt(tokens[1]);

        return ret;
    }

    public String getMonth() {

        String ret = "[errore]";

        if (tipo == 0)
            ret = tokens[0];

        return ret;
    }

    public int getNumDay() {

        int ret = -1;

        if (tipo == 1)
            ret = Integer.parseInt(tokens[1]);

        return ret;
    }
}