package com.example.scheduler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DetailTaskActivity extends AppCompatActivity implements View.OnClickListener {

    int idTask_ret = -1;
    int indClick_ret = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_task);


        // Toolbar

        Toolbar toolbar = findViewById(R.id.toolbar_det);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);


        if (!(getIntent().hasExtra("descTask") &&
                getIntent().hasExtra("dataoraTask") &&
                getIntent().hasExtra("priorTask") &&
                getIntent().hasExtra("classeTask") &&
                getIntent().hasExtra("statoTask") &&
                getIntent().hasExtra("idTask") &&
                getIntent().hasExtra("indClick")))
            System.out.println("ERRORE_DATI_INTENT");

        String descTask_ret = getIntent().getStringExtra("descTask");
        String dataoraTask_ret = getIntent().getStringExtra("dataoraTask");
        String priorTask_ret = getIntent().getStringExtra("priorTask");
        String classeTask_ret = getIntent().getStringExtra("classeTask");
        String statoTask_ret = getIntent().getStringExtra("statoTask");
        idTask_ret = getIntent().getIntExtra("idTask", -1);
        indClick_ret = getIntent().getIntExtra("indClick", -1);

        TextView desc = (TextView) findViewById(R.id.desc_input);
        TextView dataora = (TextView) findViewById(R.id.dataora_input);
        TextView prior = (TextView) findViewById(R.id.prior_input);
        TextView classe = (TextView) findViewById(R.id.classe_input);
        TextView stato = (TextView) findViewById(R.id.stato_input);

        desc.setText(descTask_ret);
        dataora.setText(dataoraTask_ret);
        prior.setText(priorTask_ret);
        classe.setText(classeTask_ret);
        stato.setText(statoTask_ret);


        final Button buttonMod = (Button) findViewById(R.id.btnModTask);
        final Button buttonElim = (Button) findViewById(R.id.btnElimTask);
        final Button buttonCompl = (Button) findViewById(R.id.btnComplTask);

        buttonMod.setOnClickListener(this);
        buttonElim.setOnClickListener(this);
        buttonCompl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("idTask", idTask_ret);
        returnIntent.putExtra("indClick", indClick_ret);

        switch (view.getId()) {

            case R.id.btnModTask:

                returnIntent.putExtra("comando", 0);

                setResult(Activity.RESULT_OK, returnIntent);

                finish();

                break;

            case R.id.btnElimTask:

                returnIntent.putExtra("comando", 1);

                setResult(Activity.RESULT_OK, returnIntent);

                finish();

                break;

            case R.id.btnComplTask:

                returnIntent.putExtra("comando", 2);

                setResult(Activity.RESULT_OK, returnIntent);

                finish();

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}