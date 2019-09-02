package com.example.scheduler;

import android.content.res.Resources;

public class VisualizeSet extends DataSet <Vis> {

    private boolean is_msgNoTask;

    public VisualizeSet(){

        super();

        init();

        this.is_msgNoTask = true;
    }

    private void init() {

        String i = Resources.getSystem().getString(R.string.msg_no_task);

        Vis vi = new Vis(i, Vis.tipoVis.MSG_NO_TASK);
        Vis vv = new Vis(Resources.getSystem().getString(R.string.vuota), Vis.tipoVis.RIGA_VUOTA);

        elements.add(vv);
        elements.add(vi);
        elements.add(vv);
    }

    public boolean isMsgNoTask(){

        return is_msgNoTask;
    }
}