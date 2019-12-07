package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import java.util.Locale;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TaskSet myTaskSet = new TaskSet();
    private VisualizeSet myVisSet = new VisualizeSet();

    private Date dataCorrente = new Date();

    private static final int REQ_CODE_FORM_NEW = 0;
    private static final int REQ_CODE_FORM_MOD = 1;
    private static final int REQ_CODE_DET_TASK = 2;

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
                intent.putExtra("is_new_task", 1);      // true (perché è la creazione di un nuovo task)

                startActivityForResult(intent, REQ_CODE_FORM_NEW);
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();


        if (getIntent().hasExtra("cmd_notif")) {

            String comando = getIntent().getStringExtra("cmd_notif");

            if (comando.equals("postpone_notif")) {

                Intent notifIntent = new Intent(this, FormActivity.class);
                notifIntent.putExtra("is_new_task", 0);
                notifIntent.putExtra("id", );
                notifIntent.putExtra("indClick", );

                startActivityForResult(notifIntent, REQ_CODE_FORM_MOD);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Check which request we're responding to
        if (requestCode == REQ_CODE_FORM_NEW) {     // Invoked when FormActivity completes its operations

            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK)
                gestisciFormNew(data);
            else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Errore: Attività non creata.", Toast.LENGTH_LONG).show();
        }
        else if (requestCode == REQ_CODE_FORM_MOD) {     // Invoked when FormActivity completes its operations

            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK)
                gestisciFormMod(data);
            else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Errore: Attività non modificata.", Toast.LENGTH_LONG).show();
        }
        else if (requestCode == REQ_CODE_DET_TASK) {     // Invoked when DetailTaskActivity completes its operations

            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK)
                gestisciDetTask(data);
            //else if (resultCode == Activity.RESULT_CANCELED)
                //Toast.makeText(this, "Errore nel visualizzare l'attività.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Vis visElemClicked = myVisSet.getElement(clickedItemIndex);
        int idTask = visElemClicked.getIdTask();
        Task taskClicked = myTaskSet.getTask(idTask);

        if (visElemClicked.getType() == Vis.tipoVis.ATTIVITA){

            Date dataOraTask = taskClicked.getDateHour();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.ITALIAN);
            String dataOraTask_str = sdf.format(dataOraTask);


            Intent intent = new Intent(MainActivity.this, DetailTaskActivity.class);
            intent.putExtra("descTask", taskClicked.getDescription());
            intent.putExtra("dataoraTask", dataOraTask_str);
            intent.putExtra("priorTask", taskClicked.getPrior_string());
            intent.putExtra("classeTask", taskClicked.getClasse_string());
            intent.putExtra("statoTask", taskClicked.getStato_string());
            intent.putExtra("idTask", idTask);
            intent.putExtra("indClick", clickedItemIndex);

            startActivityForResult(intent, REQ_CODE_DET_TASK);
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
                t_toStore.setPrior(t.getPrior());
                t_toStore.setClasse(t.getClasse());
                t_toStore.setStato(t.getStato());

                if (!resultsId.isEmpty())
                    resultsId.deleteAllFromRealm();
                Id id_toStore = realm.createObject(Id.class);
                id_toStore.setVal(id_val.getVal());
            }
        });
    }

    public void creaNotifica(int id, long time) {

        Intent notifyIntent = new Intent(this, MyReceiver.class);
        notifyIntent.putExtra("id", id);
        notifyIntent.putExtra("descTask", myTaskSet.getTask(id).getDescription());
        notifyIntent.putExtra("indVis", );

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public void creazioneTask(Task t) {

        myTaskSet.addTask(t, myVisSet);

        storeTask(t);

        creaNotifica(t.getId(), t.getDateHour().getTime());     // Crea la notifica del task

        // Aggiorna la visualizzazione della home dopo l'aggiunta di un task
        mAdapter.notifyDataSetChanged();
    }

    public void deleteTask(int id, int indClicked) {

        Vis visElemClicked = myVisSet.getElement(indClicked);
        VisualizeSet.tipoDel td;

        // Cancella notifica (non ancora mostrata) relativa al task
        Intent notifyIntent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);

        td = myTaskSet.delTask(visElemClicked.getIdTask(), myVisSet);

        delTaskFromStore(visElemClicked.getIdTask());

        aggiornaHome_del(indClicked, td);

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

    public void gestisciFormNew(Intent data) {

        // Raccogli i dati del form

        String resultDesc = data.getStringExtra("desc");
        String resultDataOraStr = data.getStringExtra("data_ora");
        int resultPrior = data.getIntExtra("prior", -1);
        int resultClasse = data.getIntExtra("classe", -1);

        if (!(data.hasExtra("desc") && data.hasExtra("data_ora") && data.hasExtra("prior") && data.hasExtra("classe")))
            System.out.println("ERRORE: Dati non passati nell'intent.");

        Date resultDataOra = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("d M yyyy HH mm");
        try {
            resultDataOra = sdf.parse(resultDataOraStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Utilizza i dati raccolti dal form per creare un nuovo task
        final Task newTask = new Task(resultDesc, resultDataOra, resultPrior, resultClasse, id_val.getValAndInc());
        creazioneTask(newTask);
    }

    public void gestisciFormMod(Intent data) {

        int idTask = data.getIntExtra("id", -1);
        Task taskToMod = myTaskSet.getTask(idTask);

        if (!data.hasExtra("id"))
            System.out.println("ERRORE: Dati non passati nell'intent.");


        // Raccogli i dati del form, se non inseriti utilizza i dati del task prima della modifica

        String resultDesc;
        if (data.hasExtra("desc"))
            resultDesc = data.getStringExtra("desc");
        else
            resultDesc = taskToMod.getDescription();

        String resultDataOraStr;
        Date resultDataOra = new Date();
        if (data.hasExtra("data") || data.hasExtra("ora")){

            String resultDataStr;
            if (data.hasExtra("data"))
                resultDataStr = data.getStringExtra("data");
            else{

                Date data_notMod = myTaskSet.getTask(idTask).getOnlyDate();

                SimpleDateFormat sdf_only_data = new SimpleDateFormat("d M yyyy", Locale.ITALIAN);
                resultDataStr = sdf_only_data.format(data_notMod);  // es: "30 8 2019"
            }

            String resultOraStr;
            if (data.hasExtra("ora"))
                resultOraStr = data.getStringExtra("ora");
            else{

                Date ora_notMod = myTaskSet.getTask(idTask).getOnlyOra();

                SimpleDateFormat sdf_only_ora = new SimpleDateFormat("HH mm", Locale.ITALIAN);
                resultOraStr = sdf_only_ora.format(ora_notMod);
            }

            resultDataOraStr = resultDataStr + " " + resultOraStr;

            SimpleDateFormat sdf = new SimpleDateFormat("d M yyyy HH mm");
            try {
                resultDataOra = sdf.parse(resultDataOraStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else{

            resultDataOra = taskToMod.getDateHour();
        }

        int resultPrior;
        if (data.hasExtra("prior"))
            resultPrior = data.getIntExtra("prior", -1);
        else
            resultPrior = taskToMod.getPrior();

        int resultClasse;
        if (data.hasExtra("classe"))
            resultClasse = data.getIntExtra("classe", -1);
        else
            resultClasse = taskToMod.getClasse();


        // Utilizza i dati raccolti dal form per modificare il task
        final Task modTask = new Task(resultDesc, resultDataOra, resultPrior, resultClasse, id_val.getValAndInc());


        // Eliminazione del vecchio task
        int indTask = data.getIntExtra("indClick", -1);
        deleteTask(idTask, indTask);

        if (!data.hasExtra("indClick"))
            System.out.println("ERRORE: Dati non passati nell'intent.");

        creazioneTask(modTask);

        Toast.makeText(this, "L'attività è stata modificata.", Toast.LENGTH_LONG).show();
    }

    public void gestisciDetTask(Intent data) {

        int cmd_ret = data.getIntExtra("comando", -1);
        int id_ret = data.getIntExtra("idTask", -1);
        int indClicked_ret = data.getIntExtra("indClick", -1);

        if (!(data.hasExtra("comando") && data.hasExtra("idTask") && data.hasExtra("indClick")))
            System.out.println("ERRORE: Dati non passati nell'intent.");

        switch (cmd_ret) {

            case 0:     // Cliccato su 'modifica'

                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("is_new_task", 0);      // false (perché non è la creazione di un nuovo task, ma la modifica di uno già esistente)
                intent.putExtra("id", id_ret);
                intent.putExtra("indClick", indClicked_ret);

                startActivityForResult(intent, REQ_CODE_FORM_MOD);

                break;

            case 1:     // Cliccato su 'elimina'

                deleteTask(id_ret, indClicked_ret);

                Toast.makeText(this, "L'attività è stata eliminata.", Toast.LENGTH_LONG).show();

                break;

            case 2:     // Cliccato su 'Completato'

                myTaskSet.getTask(id_ret).setStato(2);

                // ~ Inserimento del task nella cronologia
                // ~ copiare il task nella lista di quelli in cronologia
                // deleteTask(id_ret, indClicked_ret);
                // ...

                Toast.makeText(this, "L'attività è stata completata.", Toast.LENGTH_LONG).show();

                break;

            default:

                Toast.makeText(this, "Errore.", Toast.LENGTH_LONG).show();
        }
    }
}