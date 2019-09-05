package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TaskSet myTaskSet = new TaskSet();
    VisualizeSet myVisSet = new VisualizeSet();

    Date dataCorrente = new Date();

    private Toast mToast;

    static final int REQ_CODE = 0;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        //
        // [html.it]
        // "È responsabile della creazione e del posizionamento delle view all’interno del RecyclerView."
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myVisSet.init();

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myVisSet, this);
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, FormActivity.class);

                //startActivity(intent);
                startActivityForResult(intent, REQ_CODE);
            }
        });
    }

    // Invoked when FormActivity completes its operations
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == REQ_CODE) {

            // Make sure the request was successful
            if(resultCode == Activity.RESULT_OK){

                String resultNome = data.getStringExtra("name");    // oppure ...= data.getExtras().get("name")
                String resultCognome = data.getStringExtra("lastname");

                Task newTask = new Task(resultNome, dataCorrente, 0, "A", 123);
                myTaskSet.addTask(newTask, myVisSet);

                // Aggiorna la visualizzazione della home
                int ind;
                for (ind = 0; ind < myVisSet.getNumberOfElements(); ind++)
                    mAdapter.notifyItemChanged(ind);
            }
            else if (resultCode == Activity.RESULT_CANCELED) {

                // Write your code if there's no result

                // ...
            }
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        mToast.show();

        // if (vieneCliccatoUnTask) mostraDettagliTask/segnaTaskCompletato
    }

    // ...
}

// "The views in the list are represented by view holder objects. These objects are instances of a class you define by extending RecyclerView.ViewHolder"
// "The view holder objects are managed by an adapter, which you create by extending RecyclerView.Adapter"

// "Once you have added a RecyclerView widget to your layout, obtain a handle to the object, connect it to a layout manager, and attach an adapter for the data to be displayed"


/*
// Richiama nel codice i due controlli TextView in cui visualizzeremo i dati inseriti dall’utente ed inviati dall’Activity di default attraverso il Bundle
        final TextView text_name = (TextView) findViewById(R.id.view_name);
        final TextView text_lastname = (TextView) findViewById(R.id.view_lastname);

        // Recupero i valori dal Bundle tramite il metodo getString() e li impostiamo rispettivamente negli oggetti TextView text_name e text_lastname
        Bundle bundle = this.getIntent().getExtras();
        text_name.setText(bundle.getString("name"));
        text_lastname.setText(bundle.getString("lastname"));
 */