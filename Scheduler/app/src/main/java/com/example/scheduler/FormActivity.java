package com.example.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FormActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        final Button button = (Button) findViewById(R.id.form_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.form_button:

                //if (utente_ha_inserito_tutti_i_dati){
                if (true){

                    // Recupera i riferimenti dei controlli EditText definiti precedentemente che serviranno per salvare i dati inseriti dall’utente
                    final EditText edit_name = (EditText) findViewById(R.id.edit_name);
                    final EditText edit_lastname = (EditText) findViewById(R.id.edit_lastname);

                    // Creiamo un oggetto Bundle che utilizziamo per salvare i dati inseriti dall’utente
                    // Utilizziamo il metodo putString() dell’oggetto bundle per salvare i dati inseriti, recuperati poi con il metodo getText() della classe EditText
                    Bundle bundleResults = new Bundle();
                    bundleResults.putString("name", edit_name.getText().toString());
                    bundleResults.putString("lastname", edit_lastname.getText().toString());

                    /*
                    // Con i dati memorizzati nel Bundle creiamo l’Intent per la chiamata dell’Activity alla quale li vogliamo passare
                    Intent form_intent = new Intent(getApplicationContext(), MainActivity.class);
                    form_intent.putExtras(bundle);      // associamo all’Intent il Bundle
                    startActivity(form_intent);         // lanciamo l’Activity
                    */

                    Intent returnIntent = new Intent();
                    returnIntent.putExtras(bundleResults);      // associamo all’Intent il Bundle
                    setResult(Activity.RESULT_OK, returnIntent);

                    finish();
                }
                else{

                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);

                    finish();
                }

                break;
        }
    }
}