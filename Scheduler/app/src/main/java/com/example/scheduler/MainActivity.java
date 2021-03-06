package com.example.scheduler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TaskSet principalTaskSet = new TaskSet();    // Insieme dei task pending e ongoing
    private VisualizeSet principalVisSet = new VisualizeSet();

    private boolean is_viewing_crono = false;
    private RecyclerView.Adapter completedAdapter;
    private TaskSet completedTaskSet = new TaskSet();    // Insieme dei task completati
    private VisualizeSet completedVisSet = new VisualizeSet();

    private boolean filtro_home_attivo = false;
    private RecyclerView.Adapter filteredAdapter;
    private TaskSet filteredTaskSet = new TaskSet();    // Insieme dei task filtrati
    private VisualizeSet filteredVisSet = new VisualizeSet();
    private final CharSequence[] filterHomeItems = {"Classe - Famiglia", "Classe - Lavoro", "Classe - Tempo libero", "Classe - Altro"};
    private boolean[] itemsFilterHomeChecked = new boolean[filterHomeItems.length];
    private boolean[] itemsFilterHomeChecked_tmp = new boolean[filterHomeItems.length];

    private final CharSequence[] filterNotifItems = {"Priorità - Alta", "Priorità - Media", "Priorità - Bassa"};
    private boolean[] itemsFilterNotifChecked = new boolean[filterNotifItems.length];

    private Date dataCorrente = new Date();

    private static final int REQ_CODE_FORM_NEW = 0;
    private static final int REQ_CODE_FORM_MOD = 1;
    private static final int REQ_CODE_DET_TASK = 2;

    private Id id_val = new Id();

    private Realm realm;
    private RealmResults<Task> resultsTask;
    private RealmResults<Id> resultsId;

    private FloatingActionButton fabNewTask;
    private DrawerLayout mDrawerLayout;
    private ActionBar actionbar;

    private static final String CHANNEL_ID = "Pianificatore";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // Imposta il valore di default del filtro delle notifiche
        for (int i = 0; i < filterNotifItems.length; i = i + 1)
            itemsFilterNotifChecked[i] = true;

        // Canale delle notifiche
        createNotificationChannel();


        // Toolbar

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // "È responsabile della creazione e del posizionamento delle view all’interno del RecyclerView." [html.it]
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // Recupera i dati dallo storage

        realm = Realm.getDefaultInstance();

        resultsTask = realm.where(Task.class).findAll();

        if (resultsTask.isEmpty()) {

            principalVisSet.init();
        }
        else{

            for (Task t_stored : resultsTask) {

                Task t = new Task(t_stored.getDescription(), t_stored.getDateHour(), t_stored.getPrior(), t_stored.getClasse(), t_stored.getStato(), t_stored.getId());

                if (t.getStato() < 2)
                    principalTaskSet.addTask(t, principalVisSet);
                else
                    completedTaskSet.addTask(t, completedVisSet);
            }

            if (principalTaskSet.isEmpty())
                principalVisSet.init();
        }

        resultsId = realm.where(Id.class).findAll();

        if (!resultsId.isEmpty()){

            id_val.setVal(resultsId.get(0).getVal());
        }


        // specify an adapter
        mAdapter = new RecyclerViewAdapter(principalVisSet, this);
        recyclerView.setAdapter(mAdapter);


        // Bottone per la creazione di un nuovo task
        fabNewTask = findViewById(R.id.fab);
        fabNewTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("is_new_task", 1);      // true (perché è la creazione di un nuovo task)

                startActivityForResult(intent, REQ_CODE_FORM_NEW);
            }
        });


        // Nuovo adapter
        completedAdapter = new RecyclerViewAdapter(completedVisSet, this);

        // Navigation Drawer

        mDrawerLayout = findViewById(R.id.drawer_layout_main);

        NavigationView navigationView = findViewById(R.id.nav_view_main);

        navigationView.setNavigationItemSelectedListener(

            new NavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    menuItem.setCheckable(false);
                    mDrawerLayout.closeDrawers();

                    Intent i;
                    switch (menuItem.getItemId()) {


                        case R.id.item_crono:

                            // Modifica la grafica dell'activity
                            setTitle(getString(R.string.menu_nav_crono));
                            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                            fabNewTask.hide();
                            invalidateOptionsMenu();    // Verrà chiamato 'onPrepareOptionsMenu'

                            is_viewing_crono = true;

                            if (completedVisSet.getNumberOfElements() < 1) {

                                completedVisSet.init();
                            }
                            else {

                                if (completedVisSet.getElement(0).getType() != Vis.tipoVis.RIGA_VUOTA) {

                                    Vis rigaVuotaIniziale = new Vis("", Vis.tipoVis.RIGA_VUOTA);
                                    completedVisSet.addIn(rigaVuotaIniziale, 0);
                                }
                            }

                            recyclerView.setAdapter(completedAdapter);

                            break;


                        case R.id.item_graf:

                            // Dati necessari per riempire i grafici

                            Bundle dati = new Bundle();

                            // Istogramma
                            dati.putInt("num_task_gen", getNumTaskOfMonth(1));
                            dati.putInt("num_task_feb", getNumTaskOfMonth(2));
                            dati.putInt("num_task_mar", getNumTaskOfMonth(3));
                            dati.putInt("num_task_apr", getNumTaskOfMonth(4));
                            dati.putInt("num_task_mag", getNumTaskOfMonth(5));
                            dati.putInt("num_task_giu", getNumTaskOfMonth(6));
                            dati.putInt("num_task_lug", getNumTaskOfMonth(7));
                            dati.putInt("num_task_ago", getNumTaskOfMonth(8));
                            dati.putInt("num_task_set", getNumTaskOfMonth(9));
                            dati.putInt("num_task_ott", getNumTaskOfMonth(10));
                            dati.putInt("num_task_nov", getNumTaskOfMonth(11));
                            dati.putInt("num_task_dic", getNumTaskOfMonth(12));

                            // Torta
                            dati.putInt("num_task_fam", getNumTaskOfClass(0));
                            dati.putInt("num_task_lav", getNumTaskOfClass(1));
                            dati.putInt("num_task_templib", getNumTaskOfClass(2));
                            dati.putInt("num_task_altro", getNumTaskOfClass(3));


                            i = new Intent(MainActivity.this, GraphicsActivity.class);
                            i.putExtras(dati);

                            startActivity(i);


                            break;


                        case R.id.item_cred:

                            i = new Intent(MainActivity.this, CreditsActivity.class);

                            startActivity(i);

                            break;


                        default:

                            System.out.println("ERRORE_NAVIGATION_DRAWER");
                    }

                    return true;
                }
            });
    }

    @Override
    protected void onResume() {

        super.onResume();


        if (getIntent().hasExtra("cmd_notif")) {

            if (!getIntent().hasExtra("id"))
                System.out.println("ERRORE_DATI_INTENT");

            String comando = getIntent().getStringExtra("cmd_notif");
            int idTask_ret = getIntent().getIntExtra("id", -1);

            if (principalTaskSet.containsTask(idTask_ret)) {

                if (comando.equals("ongoing_notif")) {

                    // Setta lo stato del task su 'In corso'

                    Task taskCorrente = principalTaskSet.getTask(idTask_ret);

                    if (taskCorrente.getStato() == 0) {

                        Task taskModStato = new Task(taskCorrente.getDescription(), taskCorrente.getDateHour(), taskCorrente.getPrior(), taskCorrente.getClasse(), 1, idTask_ret);

                        deleteTask(idTask_ret, principalVisSet.getIndOfTask(idTask_ret));
                        creazioneTask_senzaNotif(taskModStato);


                        Date dataOraTask = taskModStato.getDateHour();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.ITALIAN);
                        String dataOraTask_str = sdf.format(dataOraTask);

                        Intent intent = new Intent(MainActivity.this, DetailTaskActivity.class);
                        intent.putExtra("descTask", taskModStato.getDescription());
                        intent.putExtra("dataoraTask", dataOraTask_str);
                        intent.putExtra("priorTask", taskModStato.getPrior_string());
                        intent.putExtra("classeTask", taskModStato.getClasse_string());
                        intent.putExtra("statoTask", taskModStato.getStato_string());
                        intent.putExtra("idTask", idTask_ret);
                        intent.putExtra("indClick", principalVisSet.getIndOfTask(idTask_ret));

                        // Cancella la notifica appena cliccata
                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.cancel(idTask_ret);

                        Toast.makeText(this, "L'attività è in corso di svolgimento.", Toast.LENGTH_LONG).show();

                        startActivityForResult(intent, REQ_CODE_DET_TASK);
                    }
                }
                else if (comando.equals("postpone_notif")) {

                    Intent notifIntent = new Intent(this, FormActivity.class);
                    notifIntent.putExtra("is_new_task", 0);
                    notifIntent.putExtra("id", idTask_ret);
                    notifIntent.putExtra("indClick", principalVisSet.getIndOfTask(idTask_ret));

                    // Cancella la notifica appena cliccata
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.cancel(idTask_ret);

                    startActivityForResult(notifIntent, REQ_CODE_FORM_MOD);
                }
            }
        }


        if (filtro_home_attivo) {

            // Ricalcola i task da mostrare (se ci sono state modifiche o eliminazioni in questo modo viene aggiornata la visualizzazione)

            // Svuoti le variabili
            filteredTaskSet.deleteAll();
            filteredVisSet.deleteAll();

            for (Task t : principalTaskSet.getElements()) {

                if (itemsFilterHomeChecked[0]) {

                    if (t.getClasse() == 0)
                        filteredTaskSet.addTask(t, filteredVisSet);
                }

                if (itemsFilterHomeChecked[1]) {

                    if (t.getClasse() == 1)
                        filteredTaskSet.addTask(t, filteredVisSet);
                }

                if (itemsFilterHomeChecked[2]) {

                    if (t.getClasse() == 2)
                        filteredTaskSet.addTask(t, filteredVisSet);
                }

                if (itemsFilterHomeChecked[3]) {

                    if (t.getClasse() == 3)
                        filteredTaskSet.addTask(t, filteredVisSet);
                }
            }


            if (filteredVisSet.getNumberOfElements() < 1)
                filteredVisSet.init();
            else {

                if (filteredVisSet.getElement(0).getType() != Vis.tipoVis.RIGA_VUOTA) {

                    Vis rigaVuotaIniziale = new Vis("", Vis.tipoVis.RIGA_VUOTA);
                    filteredVisSet.addIn(rigaVuotaIniziale, 0);
                }
            }


            // Nuovo adapter
            filteredAdapter = new RecyclerViewAdapter(filteredVisSet, this);
            recyclerView.setAdapter(filteredAdapter);
        }
        else if (is_viewing_crono) {

            // Ricalcola i task da mostrare (se ci sono state modifiche o eliminazioni in questo modo viene aggiornata la visualizzazione)

            if (completedVisSet.getNumberOfElements() < 1) {

                completedVisSet.init();
            }
            else {

                if (completedVisSet.getElement(0).getType() != Vis.tipoVis.RIGA_VUOTA) {

                    Vis rigaVuotaIniziale = new Vis("", Vis.tipoVis.RIGA_VUOTA);
                    completedVisSet.addIn(rigaVuotaIniziale, 0);
                }
            }

            // Nuovo adapter
            completedAdapter = new RecyclerViewAdapter(completedVisSet, this);
            recyclerView.setAdapter(completedAdapter);
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

        Vis visElemClicked;

        if (filtro_home_attivo) {

            visElemClicked = filteredVisSet.getElement(clickedItemIndex);
        }
        else if (is_viewing_crono) {

            visElemClicked = completedVisSet.getElement(clickedItemIndex);
        }
        else {

            visElemClicked = principalVisSet.getElement(clickedItemIndex);
        }

        int idTask = visElemClicked.getIdTask();


        Task taskClicked;
        int indClicked;

        if (is_viewing_crono) {

            taskClicked = completedTaskSet.getTask(idTask);
            indClicked = completedVisSet.getIndOfTask(idTask);
        }
        else {

            taskClicked = principalTaskSet.getTask(idTask);
            indClicked = principalVisSet.getIndOfTask(idTask);
        }


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
            intent.putExtra("indClick", indClicked);

            startActivityForResult(intent, REQ_CODE_DET_TASK);
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        realm.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case android.R.id.home:

                if (filtro_home_attivo || is_viewing_crono) {       // Ha cliccato sulla freccia indietro

                    // Rimuovi la visualizzazione con filtri o la cronologia e torna alla home classica

                    setTitle(getString(R.string.app_name));
                    actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
                    fabNewTask.show();
                    invalidateOptionsMenu();    // Verrà chiamato 'onPrepareOptionsMenu'

                    filtro_home_attivo = false;
                    is_viewing_crono = false;

                    recyclerView.setAdapter(mAdapter);
                }
                else                                                // Ha cliccato sul simbolo del panino
                    mDrawerLayout.openDrawer(GravityCompat.START);

                return true;


            case R.id.aggiungi_filtri_home:

                // Nuovo adapter
                filteredAdapter = new RecyclerViewAdapter(filteredVisSet, this);

                if (!filtro_home_attivo) {

                    // Azzera le vecchie variabili
                    for (int i = 0; i < itemsFilterHomeChecked.length; i = i + 1)
                        itemsFilterHomeChecked[i] = false;
                }


                // Raccolgo i dati

                itemsFilterHomeChecked_tmp = itemsFilterHomeChecked.clone();

                final AlertDialog.Builder builderFiltriHome = new AlertDialog.Builder(this);
                builderFiltriHome.setTitle("Verranno visualizzate solo le attività con gli attributi selezionati");

                builderFiltriHome.setMultiChoiceItems(filterHomeItems, itemsFilterHomeChecked, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {

                        if (isChecked)
                            itemsFilterHomeChecked_tmp[indexSelected] = true;
                        else
                            itemsFilterHomeChecked_tmp[indexSelected] = false;
                    }
                });

                builderFiltriHome.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (!filtro_home_attivo) {

                            // Modifica la grafica dell'activity
                            setTitle("Visualizzazione con filtri");
                            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                            fabNewTask.hide();
                            invalidateOptionsMenu();    // Verrà chiamato 'onPrepareOptionsMenu'

                            filtro_home_attivo = true;
                        }


                        filteredTaskSet.deleteAll();
                        filteredVisSet.deleteAll();

                        itemsFilterHomeChecked = itemsFilterHomeChecked_tmp.clone();


                        // Setto la nuova visualizzazione della home

                        for (Task t : principalTaskSet.getElements()) {

                            // Famiglia
                            if (itemsFilterHomeChecked[0]) {

                                if (t.getClasse() == 0)
                                    filteredTaskSet.addTask(t, filteredVisSet);
                            }

                            // Lavoro
                            if (itemsFilterHomeChecked[1]) {

                                if (t.getClasse() == 1)
                                    filteredTaskSet.addTask(t, filteredVisSet);
                            }

                            // Tempo libero
                            if (itemsFilterHomeChecked[2]) {

                                if (t.getClasse() == 2)
                                    filteredTaskSet.addTask(t, filteredVisSet);
                            }

                            // Altro
                            if (itemsFilterHomeChecked[3]) {

                                if (t.getClasse() == 3)
                                    filteredTaskSet.addTask(t, filteredVisSet);
                            }
                        }

                        if (filteredVisSet.getNumberOfElements() < 1) {

                            filteredVisSet.init();
                        }
                        else {

                            if (filteredVisSet.getElement(0).getType() != Vis.tipoVis.RIGA_VUOTA) {

                                Vis rigaVuotaIniziale = new Vis("", Vis.tipoVis.RIGA_VUOTA);
                                filteredVisSet.addIn(rigaVuotaIniziale, 0);
                            }
                        }

                        recyclerView.setAdapter(filteredAdapter);
                    }
                });

                builderFiltriHome.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });

                builderFiltriHome.show();

                return true;


            case R.id.aggiungi_filtri_notif:

                // Raccolgo i dati

                final AlertDialog.Builder builderFiltriNotif = new AlertDialog.Builder(this);
                builderFiltriNotif.setTitle("Ricevi le notifiche solo delle attività con le priorità selezionate");

                builderFiltriNotif.setMultiChoiceItems(filterNotifItems, itemsFilterNotifChecked, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {

                        if (isChecked)
                            itemsFilterNotifChecked[indexSelected] = true;
                        else
                            itemsFilterNotifChecked[indexSelected] = false;
                    }
                });

                builderFiltriNotif.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (Task t : principalTaskSet.getElements()) {

                            int idTask = t.getId();
                            long time = t.getDateHour().getTime();

                            if (t.getStato() == 0) {        // Pending

                                if (t.getPrior() == 0) {                // Alta

                                    if (itemsFilterNotifChecked[0])
                                        creaNotifica(idTask, time);
                                    else
                                        eliminaNotifica(idTask);
                                }
                                else if (t.getPrior() == 1) {         // Media

                                    if (itemsFilterNotifChecked[1])
                                        creaNotifica(idTask, time);
                                    else
                                        eliminaNotifica(idTask);
                                }
                                else if (t.getPrior() == 2) {         // Bassa

                                    if (itemsFilterNotifChecked[2])
                                        creaNotifica(idTask, time);
                                    else
                                        eliminaNotifica(idTask);
                                }
                            }
                        }
                    }
                });

                builderFiltriNotif.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });

                builderFiltriNotif.show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        if (filtro_home_attivo)
            menu.findItem(R.id.aggiungi_filtri_notif).setEnabled(false);
        else if (is_viewing_crono)
            menu.findItem(R.id.imposta_filtri).setEnabled(false);
        else {

            menu.findItem(R.id.imposta_filtri).setEnabled(true);
            menu.findItem(R.id.aggiungi_filtri_home).setEnabled(true);
            menu.findItem(R.id.aggiungi_filtri_notif).setEnabled(true);
        }

        return true;
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

    private void createNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void creaNotifica(int idTask, long time) {

        Intent notifyIntent = new Intent(this, NotificationAlarmReceiver.class);
        notifyIntent.putExtra("id", idTask);
        notifyIntent.putExtra("descTask", principalTaskSet.getTask(idTask).getDescription());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, idTask, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public void eliminaNotifica(int id) {

        Intent notifyIntent = new Intent(this, NotificationAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    public void creazioneTask(Task t) {

        principalTaskSet.addTask(t, principalVisSet);

        storeTask(t);

        if (itemsFilterNotifChecked[t.getPrior()])      // Controlla il filtro delle notifiche
            creaNotifica(t.getId(), t.getDateHour().getTime());     // Crea la notifica del task

        // Aggiorna la visualizzazione della home dopo l'aggiunta di un task
        mAdapter.notifyDataSetChanged();
    }

    public void creazioneTask_senzaNotif(Task t) {

        principalTaskSet.addTask(t, principalVisSet);

        storeTask(t);

        // Aggiorna la visualizzazione della home dopo l'aggiunta di un task
        mAdapter.notifyDataSetChanged();
    }

    public void deleteTask(int id, int indClicked) {

        Vis visElemClicked;

        if (is_viewing_crono)
            visElemClicked = completedVisSet.getElement(indClicked);
        else
            visElemClicked = principalVisSet.getElement(indClicked);


        // Cancella notifica (non ancora mostrata) relativa al task
        eliminaNotifica(id);


        VisualizeSet.tipoDel td;

        if (is_viewing_crono)
            td = completedTaskSet.delTask(visElemClicked.getIdTask(), completedVisSet);
        else
            td = principalTaskSet.delTask(visElemClicked.getIdTask(), principalVisSet);


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

    public void spostaTaskInCronologia(Task t, int indClickedTask) {

        deleteTask(t.getId(), indClickedTask);

        completedTaskSet.addTask(t, completedVisSet);

        storeTask(t);
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
            System.out.println("ERRORE_DATI_INTENT");

        Date resultDataOra = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("d M yyyy HH mm");
        try {
            resultDataOra = sdf.parse(resultDataOraStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Utilizza i dati raccolti dal form per creare un nuovo task
        final Task newTask = new Task(resultDesc, resultDataOra, resultPrior, resultClasse, 0, id_val.getValAndInc());
        creazioneTask(newTask);
    }

    public void gestisciFormMod(Intent data) {

        int idTask = data.getIntExtra("id", -1);
        Task taskToMod = principalTaskSet.getTask(idTask);

        if (!data.hasExtra("id"))
            System.out.println("ERRORE_DATI_INTENT");


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

                Date data_notMod = principalTaskSet.getTask(idTask).getOnlyDate();

                SimpleDateFormat sdf_only_data = new SimpleDateFormat("d M yyyy", Locale.ITALIAN);
                resultDataStr = sdf_only_data.format(data_notMod);  // es: "30 8 2019"
            }

            String resultOraStr;
            if (data.hasExtra("ora"))
                resultOraStr = data.getStringExtra("ora");
            else{

                Date ora_notMod = principalTaskSet.getTask(idTask).getOnlyOra();

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
        final Task modTask = new Task(resultDesc, resultDataOra, resultPrior, resultClasse, taskToMod.getStato(), id_val.getValAndInc());


        if (!data.hasExtra("indClick"))
            System.out.println("ERRORE_DATI_INTENT");

        // Eliminazione del vecchio task
        int indTask = data.getIntExtra("indClick", -1);
        deleteTask(idTask, indTask);

        creazioneTask(modTask);

        Toast.makeText(this, "L'attività è stata modificata.", Toast.LENGTH_LONG).show();
    }

    public void gestisciDetTask(Intent data) {

        int cmd_ret = data.getIntExtra("comando", -1);
        int id_ret = data.getIntExtra("idTask", -1);
        int indClicked_ret = data.getIntExtra("indClick", -1);

        if (!(data.hasExtra("comando") && data.hasExtra("idTask") && data.hasExtra("indClick")))
            System.out.println("ERRORE_DATI_INTENT");


        Task taskCorrente;
        if (is_viewing_crono)
            taskCorrente = completedTaskSet.getTask(id_ret);
        else
            taskCorrente = principalTaskSet.getTask(id_ret);

        switch (cmd_ret) {

            case 0:     // Cliccato su 'modifica'

                if (taskCorrente.getStato() == 0) {    // Lo stato del task è 'pending'

                    Intent intent = new Intent(MainActivity.this, FormActivity.class);
                    intent.putExtra("is_new_task", 0);      // false (perché non è la creazione di un nuovo task, ma la modifica di uno già esistente)
                    intent.putExtra("id", id_ret);
                    intent.putExtra("indClick", indClicked_ret);

                    startActivityForResult(intent, REQ_CODE_FORM_MOD);
                }
                else if (taskCorrente.getStato() == 1)     // Lo stato del task è 'ongoing'
                    Toast.makeText(this, "L'attività non può più essere modificata perché è già in corso di svolgimento.", Toast.LENGTH_LONG).show();
                else if (taskCorrente.getStato() == 2)     // Lo stato del task è 'completed'
                    Toast.makeText(this, "L'attività non può più essere modificata perché è già stata completata.", Toast.LENGTH_LONG).show();
                else
                    System.out.println("ERRORE_STATO_TASK");

                break;


            case 1:     // Cliccato su 'elimina'

                deleteTask(id_ret, indClicked_ret);

                Toast.makeText(this, "L'attività è stata eliminata.", Toast.LENGTH_LONG).show();

                break;


            case 2:     // Cliccato su 'Completato'

                // Setta lo stato del task su 'Completato'

                if (taskCorrente.getStato() == 1) {     // Se il task è nello stato 'ongoing'

                    Task taskModStato = new Task(taskCorrente.getDescription(), taskCorrente.getDateHour(), taskCorrente.getPrior(), taskCorrente.getClasse(), 2, id_ret);

                    // Sposto il task nella lista di quelli in cronologia
                    spostaTaskInCronologia(taskModStato, principalVisSet.getIndOfTask(id_ret));

                    Toast.makeText(this, "L'attività è stata completata.", Toast.LENGTH_LONG).show();
                }
                else if (taskCorrente.getStato() == 0)      // Se il task è nello stato 'pending'
                    Toast.makeText(this, "L'attività può essere completata soltanto mentre è in corso di svolgimento.", Toast.LENGTH_LONG).show();
                else if (taskCorrente.getStato() == 2)      // Se il task è nello stato 'completed'
                    Toast.makeText(this, "L'attività è già stata completata.", Toast.LENGTH_LONG).show();
                else
                    System.out.println("ERRORE_STATO_TASK");

                break;

            default:

                Toast.makeText(this, "Errore.", Toast.LENGTH_LONG).show();
        }
    }

    public int getNumTaskOfMonth(int mese) {

        int numOfTask = 0;

        // Vengono scelti soltanto i task relativi all'anno corrente
        SimpleDateFormat sdf_data_ora = new SimpleDateFormat("EEEE d M yyyy HH mm", Locale.ITALIAN);
        String strDataOra = sdf_data_ora.format(dataCorrente);  // es: "venerdì 30 8 2019 15 30"
        String[] dataOraTokens = strDataOra.split(" ");
        int annoCorr = Integer.parseInt(dataOraTokens[3]);


        for (Task t : principalTaskSet.getElements()) {
            if ((t.getMonth_int() == mese) && (t.getYear() == annoCorr))
                numOfTask = numOfTask + 1;
        }

        return numOfTask;
    }

    public int getNumTaskOfClass(int classe) {

        int numOfTask = 0;

        for (Task t : principalTaskSet.getElements()) {
            if (t.getClasse() == classe)
                numOfTask = numOfTask + 1;
        }

        return numOfTask;
    }
}