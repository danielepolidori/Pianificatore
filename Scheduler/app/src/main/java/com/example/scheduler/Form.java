package com.example.scheduler;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Form extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        final TextView text_name = (TextView) findViewById(R.id.view_name);
        final TextView text_lastname = (TextView) findViewById(R.id.view_lastname);
        Bundle bundle = this.getIntent().getExtras();
        text_name.setText(bundle.getString("name"));
        text_lastname.setText(bundle.getString("lastname"));
    }
}

// Recupero i valori dal Bundle tramite il metodo getString() e li impostiamo rispettivamente negli oggetti TextView text_name e text_lastname