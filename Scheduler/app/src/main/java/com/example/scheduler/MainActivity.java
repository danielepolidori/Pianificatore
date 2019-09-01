package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TaskSet myTaskSet = new TaskSet();
    VisualizeSet myVisSet = new VisualizeSet();

    Date dataCorrente = new Date();

    private Toast mToast;

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

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d MMMM yyyy", Locale.ITALIAN);
        String strData = sdf.format(dataCorrente);  // es: "venerdì 30 agosto 2019"

        String init = getResources().getString(R.string.msg_no_task);
        myVisSet.add(init);

        /*
        String[] tokens = strData.split(" ");
        String textGiorno = tokens[0].substring(0,1).toUpperCase() + tokens[0].substring(1);
        String numGiorno = tokens[1];
        String mese = tokens[2].toUpperCase();
        String anno = tokens[3];

        Task t1 = new Task(mese + " " + anno, "A", 0);
        Task t2 = new Task(textGiorno + " " + numGiorno, "B", 1);
        Task t3 = new Task(getResources().getString(R.string.att), "C", 0);
        Task t4 = new Task(getResources().getString(R.string.att), "D", 2);
        Task t5 = new Task(getResources().getString(R.string.att), "E", 1);
        myTaskSet.add(t1);
        myTaskSet.add(t2);
        myTaskSet.add(t3);
        myTaskSet.add(t4);
        myTaskSet.add(t5);
        */

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myVisSet, this);
        recyclerView.setAdapter(mAdapter);
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