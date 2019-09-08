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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TaskSet myTaskSet = new TaskSet();
    VisualizeSet myVisSet = new VisualizeSet();

    Date dataCorrente = new Date();

    private Toast mToast;

    static final int REQ_CODE = 0;  // The request code

    Id id_val = new Id();

    private Realm realm;
    RealmResults<Task> resultsTask;
    RealmResults<Id> resultsId;

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

        realm = Realm.getDefaultInstance();

        resultsTask = realm.where(Task.class).findAll();

        if (resultsTask.isEmpty()){

            myVisSet.init();
        }
        else{

            for (Task t_stored : resultsTask) {

                Task t = new Task(t_stored.getDescription(), t_stored.getDateHour(), t_stored.getPrior(), t_stored.getClasse(), t_stored.getId());
                t.setStato(t_stored.getStato());

                myTaskSet.addTask(t, myVisSet);
            }

            /*
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    resultsTask.deleteAllFromRealm();
                }
            });
            */
        }

        resultsId = realm.where(Id.class).findAll();

        if (!resultsId.isEmpty()){

            id_val.setVal(resultsId.get(0).getVal());

            /*
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    resultsId.deleteAllFromRealm();
                }
            });
            */
        }

        // specify an adapter
        mAdapter = new MyAdapter(myVisSet, this);
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fabNewTask = findViewById(R.id.fab);
        fabNewTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, FormActivity.class);

                startActivityForResult(intent, REQ_CODE);
            }
        });
    }

    // Invoked when FormActivity completes its operations
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("inizio activityResult");

        // Check which request we're responding to
        if (requestCode == REQ_CODE) {

            // Make sure the request was successful
            if(resultCode == Activity.RESULT_OK){

                // Raccogli i dati del form

                String resultDesc = data.getStringExtra("desc");    // oppure ...= data.getExtras().get("name")
                String resultDataOraStr = data.getStringExtra("data_ora");
                int resultPriorInt = data.getIntExtra("prior", -1);
                int resultClasseInt = data.getIntExtra("classe", -1);

                Task.priorTask resultPrior;
                switch (resultPriorInt){

                    case 0:
                        resultPrior = Task.priorTask.ALTA;
                        break;

                    case 1:
                        resultPrior = Task.priorTask.MEDIA;
                        break;

                    default:    // caso 2 o caso default
                        resultPrior = Task.priorTask.BASSA;
                }

                Task.classeTask resultClasse;
                switch (resultClasseInt){

                    case 0:
                        resultClasse = Task.classeTask.FAMIGLIA;
                        break;

                    case 1:
                        resultClasse = Task.classeTask.LAVORO;
                        break;

                    case 2:
                        resultClasse = Task.classeTask.TEMPO_LIBERO;
                        break;

                    default:    // caso 3 o caso di default
                        resultClasse = Task.classeTask.ALTRO;
                }

                Date resultDataOra = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("d M yyyy HH mm");
                try {
                    resultDataOra = sdf.parse(resultDataOraStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Utilizza i dati raccolti dal form per creare un nuovo task
                Task newTask = new Task(resultDesc, resultDataOra, resultPrior, resultClasse, id_val.getValAndInc());
                myTaskSet.addTask(newTask, myVisSet);

                for (Task t : myTaskSet.getElements())
                    System.out.println(t.getDescription());

                for (Vis v : myVisSet.getElements())
                    System.out.println(v.getText());

                // Aggiorna la visualizzazione della home
                int ind;
                for (ind = 0; ind < myVisSet.getNumberOfElements(); ind++)
                    mAdapter.notifyItemChanged(ind);

                salvaDatiApp();
            }
            else if (resultCode == Activity.RESULT_CANCELED) {

                // Write your code if there's no result

                // ...
            }
        }

        System.out.println("fine activityResult");
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

/*
    @Override
    protected void onStop() {

        super.onStop();

        if(salvaDatiApp())
            realm.close();
    }*/

    @Override
    protected void onDestroy() {

        super.onDestroy();

        realm.close();
    }

    private boolean salvaDatiApp() {

        System.out.println("inizio salva");

        // Cancella i vecchi dati (se presenti)

        if (!resultsTask.isEmpty()) {

            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    resultsTask.deleteAllFromRealm();
                }
            });
        }

        if (!resultsId.isEmpty()) {

            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {

                    resultsId.deleteAllFromRealm();
                }
            });
        }

        // Salva i nuovi dati

        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {

                for (Task el : myTaskSet.getElements()){

                    Task t_toStore = realm.createObject(Task.class);

                    t_toStore.setId(el.getId());
                    t_toStore.setDesc(el.getDescription());
                    t_toStore.setDateHour(el.getDateHour());
                    t_toStore.setPrior(el.getPriorToStore());
                    t_toStore.setClasse(el.getClasseToStore());
                    t_toStore.setStato(el.getStato());
                    // ...
                }

                Id id = realm.createObject(Id.class);

                id.setVal(id_val.getVal());
            }
        });

        System.out.println("fine salva");

        return true;
    }

    // ...
}