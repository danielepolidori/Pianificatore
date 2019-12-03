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

    public void creaNotifica(int id, String desc, long time) {

        Intent notifyIntent = new Intent(this, MyReceiver.class);
        notifyIntent.putExtra("id", id);
        notifyIntent.putExtra("descTask", desc);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public void creazioneTask(Task t) {

        myTaskSet.addTask(t, myVisSet);

        storeTask(t);

        creaNotifica(t.getId(), t.getDescription(), t.getDateHour().getTime());     // Crea la notifica del task

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

        Toast.makeText(this, "Attività rimossa.", Toast.LENGTH_LONG).show();

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
        int resultPriorInt = data.getIntExtra("prior", -1);
        int resultClasseInt = data.getIntExtra("classe", -1);

        Task.priorTask resultPrior;
        switch (resultPriorInt) {

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
        switch (resultClasseInt) {

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
        creazioneTask(newTask);
    }

    public void gestisciFormMod(Intent data) {

        int idTask = data.getIntExtra("id", -1);
        Task taskToMod = myTaskSet.getTask(idTask);


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

        Task.priorTask resultPrior;
        if (data.hasExtra("prior")){

            int resultPriorInt = data.getIntExtra("prior", -1);

            switch (resultPriorInt) {

                case 0:
                    resultPrior = Task.priorTask.ALTA;
                    break;

                case 1:
                    resultPrior = Task.priorTask.MEDIA;
                    break;

                default:    // case 2
                    resultPrior = Task.priorTask.BASSA;
            }
        }
        else{   // caso in cui non sia stata modificata

            resultPrior = taskToMod.getPrior();
        }

        Task.classeTask resultClasse;
        if (data.hasExtra("classe")){

            int resultClasseInt = data.getIntExtra("classe", -1);

            switch (resultClasseInt) {

                case 0:
                    resultClasse = Task.classeTask.FAMIGLIA;
                    break;

                case 1:
                    resultClasse = Task.classeTask.LAVORO;
                    break;

                case 2:
                    resultClasse = Task.classeTask.TEMPO_LIBERO;
                    break;

                default:    // case 3
                    resultClasse = Task.classeTask.ALTRO;
            }
        }
        else{   // caso in cui non sia stata modificata

            resultClasse = taskToMod.getClasse();
        }


        // Utilizza i dati raccolti dal form per modificare il task
        final Task modTask = new Task(resultDesc, resultDataOra, resultPrior, resultClasse, id_val.getValAndInc());


        // Eliminazione del vecchio task
        int indTask = data.getIntExtra("indClick", -1);
        deleteTask(idTask, indTask);

        creazioneTask(modTask);
    }

    public void gestisciDetTask(Intent data) {

        int cmd_ret = data.getIntExtra("comando", -1);
        int id_ret = data.getIntExtra("idTask", -1);
        int indClicked_ret = data.getIntExtra("indClick", -1);

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

                break;

            case 2:     // Cliccato su 'Completato'

                myTaskSet.getTask(id_ret).setStato(Task.statoTask.COMPLETED);

                // ~ Inserimento del task nella cronologia
                // ...

                //deleteTask(id_ret, indClicked_ret);

                break;

            default:

                Toast.makeText(this, "Errore.", Toast.LENGTH_LONG).show();
        }
    }
}