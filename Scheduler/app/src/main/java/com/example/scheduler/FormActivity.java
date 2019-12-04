package com.example.scheduler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormActivity extends AppCompatActivity implements OnClickListener, DatePickerDialog.OnDateSetListener {

    final CharSequence[] prior_items = {"Alta", "Media", "Bassa"};
    final CharSequence[] classe_items = {"Famiglia", "Lavoro", "Tempo libero", "Altro"};

    int priorScelta = -1;
    int classeScelta = -1;

    Date dataCorrente = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("d M yyyy",Locale.ITALIAN);
    String strData = sdf.format(dataCorrente);  // es: "1 1 2019"
    String[] dateTokens = strData.split(" ");
    int giornoCorrente = Integer.parseInt(dateTokens[0]);
    int meseCorrente = Integer.parseInt(dateTokens[1]);
    int annoCorrente = Integer.parseInt(dateTokens[2]);

    String descScelta = "";

    String data_setted = "";
    String ora_setted = "";
    String data_oraScelta = "";

    TimePickerDialog.OnTimeSetListener myOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int ora, int minuto) {

            ora_setted = ora + " " + minuto;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        final Button buttonData = (Button) findViewById(R.id.btnDataForm);
        final Button buttonOra = (Button) findViewById(R.id.btnOraForm);
        final Button buttonPrior = (Button) findViewById(R.id.btnPriorForm);
        final Button buttonClasse = (Button) findViewById(R.id.btnClasseForm);
        final Button buttonSalva = (Button) findViewById(R.id.btnSalvaForm);
        buttonData.setOnClickListener(this);
        buttonOra.setOnClickListener(this);
        buttonPrior.setOnClickListener(this);
        buttonClasse.setOnClickListener(this);
        buttonSalva.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnDataForm:

                DatePickerDialog dpd = new DatePickerDialog(this, FormActivity.this, annoCorrente, meseCorrente-1, giornoCorrente);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();

                break;

            case R.id.btnOraForm:

                TimePickerDialog tpd = new TimePickerDialog(this, myOnTimeSetListener, 0, 0, true);
                tpd.show();

                break;

            case R.id.btnPriorForm:

                AlertDialog.Builder builderPrior = new AlertDialog.Builder(this);
                builderPrior.setTitle("Scegli la priorità");

                builderPrior.setSingleChoiceItems(prior_items, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        priorScelta = which;
                    }
                });

                builderPrior.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                builderPrior.show();

                break;

            case R.id.btnClasseForm:

                AlertDialog.Builder builderClasse = new AlertDialog.Builder(this);
                builderClasse.setTitle("Scegli la classe");

                builderClasse.setSingleChoiceItems(classe_items, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        classeScelta = which;
                    }
                });

                builderClasse.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                builderClasse.show();

                break;

            case R.id.btnSalvaForm:

                // Recupera i riferimenti dei controlli EditText definiti precedentemente che serviranno per salvare i dati inseriti dall’utente
                final EditText desc_setted = (EditText) findViewById(R.id.edit_desc);
                descScelta = desc_setted.getText().toString();

                int is_newTask = getIntent().getIntExtra("is_new_task", -1);

                if (!getIntent().hasExtra("is_new_task"))
                    System.out.println("ERRORE: Dati non passati nell'intent.");

                if (is_newTask == 1) {      // Creazione di un nuovo task

                    // Controlla se l'utente ha inserito i dati in tutti i campi
                    if (!descScelta.isEmpty() && !data_setted.isEmpty() && !ora_setted.isEmpty() && priorScelta > -1 && classeScelta > -1){

                        data_oraScelta = data_setted + " " + ora_setted;

                        // Creiamo un oggetto Bundle che utilizziamo per salvare i dati inseriti dall’utente
                        Bundle bundleResults = new Bundle();
                        bundleResults.putString("desc", descScelta);
                        bundleResults.putString("data_ora", data_oraScelta);
                        bundleResults.putInt("prior", priorScelta);
                        bundleResults.putInt("classe", classeScelta);

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
                }
                else if (is_newTask == 0) {     // Modifica di un task già esistente

                    int idTask_ret = getIntent().getIntExtra("id", -1);
                    int indClick_ret = getIntent().getIntExtra("indClick", -1);

                    if (!(getIntent().hasExtra("id") && getIntent().hasExtra("indClick")))
                        System.out.println("ERRORE: Dati non passati nell'intent.");

                    // Controlla che l'utente abbia inserito i dati in almeno un campo
                    if (!descScelta.isEmpty() || !data_setted.isEmpty() || !ora_setted.isEmpty() || priorScelta > -1 || classeScelta > -1){

                        Bundle bundleResults = new Bundle();

                        bundleResults.putInt("id", idTask_ret);
                        bundleResults.putInt("indClick", indClick_ret);

                        if (!descScelta.isEmpty())
                            bundleResults.putString("desc", descScelta);

                        if (!data_setted.isEmpty())
                            bundleResults.putString("data", data_setted);

                        if (!ora_setted.isEmpty())
                            bundleResults.putString("ora", ora_setted);

                        if (priorScelta > -1)
                            bundleResults.putInt("prior", priorScelta);

                        if (classeScelta > -1)
                            bundleResults.putInt("classe", classeScelta);


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
                }

                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int anno, int mese, int giorno) {

        data_setted = giorno + " " + (mese+1) + " " + anno;
    }
}