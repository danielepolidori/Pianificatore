package com.example.scheduler;

import io.realm.RealmObject;

public class Id extends RealmObject {

    private int val;

    public Id() {

        val = 0;
    }

    public int getValAndInc() {

        int ret = val;

        val = val + 1;

        return ret;
    }

    public int getVal() {

        return val;
    }

    public void setVal(int newVal) {

        val = newVal;
    }
}
