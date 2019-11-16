package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
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

    private TaskSet myTaskSet = new TaskSet();
    private VisualizeSet myVisSet = new VisualizeSet();

    private Date dataCorrente = new Date();

    private Toast toastDelTask;
    private Toast toastErrForm;

    private static final int REQ_CODE = 0;  // The request code

    private Id id_val = new Id();

    private Realm realm;
    private RealmResults<Task> resultsTask;
    private RealmResults<Id> resultsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // "È responsabile della creazione e del posizionamento delle view all’interno del RecyclerView." [html.it]
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
        }

        resultsId = realm.where(Id.class).findAll();

        if (!resultsId.isEmpty()){

            id_val.setVal(resultsId.get(0).getVal());
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
                final Task newTask = new Task(resultDesc, resultDataOra, resultPrior, resultClasse, id_val.getValAndInc());
                myTaskSet.addTask(newTask, myVisSet);

                storeTask(newTask);

                /*
                // VECCHIO
                // Invia una notifica nel giorno e nell'ora del task
                Intent notifyIntent = new Intent(this, MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,  newTask.getDateHour().getTime(), pendingIntent);

                 */

                // NUOVO

                Intent notifyIntent = new Intent(this, MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notifyIntent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,  newTask.getDateHour().getTime(), pendingIntent);


                // Aggiorna la visualizzazione della home dopo l'aggiunta di un task
                mAdapter.notifyDataSetChanged();
            }
            else if (resultCode == Activity.RESULT_CANCELED) {

                toastErrForm = Toast.makeText(this, "Attività non creata.\nE' necessario inserire i dati in tutti i campi.", Toast.LENGTH_LONG);
                toastErrForm.show();
            }
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Vis visElemClicked = myVisSet.getElement(clickedItemIndex);
        VisualizeSet.tipoDel td;

        if (visElemClicked.getType() == Vis.tipoVis.ATTIVITA){

            td = myTaskSet.delTask(visElemClicked.getIdTask(), myVisSet);

            delTaskFromStore(visElemClicked.getIdTask());

            for (Task t : myTaskSet.getElements())
                System.out.println(t.getDescription());

            for (Vis v : myVisSet.getElements())
                System.out.println(v.getText());

            aggiornaHome_del(clickedItemIndex, td);

            toastDelTask = Toast.makeText(this, "Attività rimossa.", Toast.LENGTH_LONG);
            toastDelTask.show();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        realm.close();
    }

    public void storeTask(final Task t) {

        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {

                Task t_toStore = realm.createObject(Task.class);
                t_toStore.setId(t.getId());
                t_toStore.setDesc(t.getDescription());
                t_toStore.setDateHour(t.getDateHour());
                t_toStore.setPrior(t.getPriorToStore());
                t_toStore.setClasse(t.getClasseToStore());
                t_toStore.setStato(t.getStato());

                if (!resultsId.isEmpty())
                    resultsId.deleteAllFromRealm();
                Id id_toStore = realm.createObject(Id.class);
                id_toStore.setVal(id_val.getVal());
            }
        });
    }

    public void delTaskFromStore(int id_t) {

        int i;
        boolean eliminato = false;

        for (i = 0; i < resultsTask.size() && !eliminato; i++){

            if (resultsTask.get(i).getId() == id_t){

                final int tmp = i;

                realm.executeTransaction(new Realm.Transaction() {

                    @Override
                    public void execute(Realm realm) {

                        resultsTask.get(tmp).deleteFromRealm();
                    }
                });

                eliminato = true;
            }
        }
    }

    // Aggiorna la visualizzazione della home dopo la rimozione di un task
    public void aggiornaHome_del(int positionTaskDel, VisualizeSet.tipoDel type) {

        switch (type){

            case SINGOLO_TASK:
                mAdapter.notifyItemRangeRemoved(positionTaskDel, 2);

            case GIORNO:
                mAdapter.notifyItemRangeRemoved(positionTaskDel-2, 6);

            default:    // case ULTIMO_TASK e default
                mAdapter.notifyItemRangeRemoved(0, 7);
        }

        mAdapter.notifyDataSetChanged();
    }
}