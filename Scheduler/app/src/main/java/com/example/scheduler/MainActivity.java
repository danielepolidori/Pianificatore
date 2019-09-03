package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Date;

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

        myVisSet.init();


        /*
        long v1 = dataCorrente.getTime() + 1000000000;
        long v2 = dataCorrente.getTime() + 2000000000;
        Date d1 = new Date(v1);
        Date d2 = new Date(v2);

        Task t1 = new Task("Attività numero uno", dataCorrente, 0, "A");
        Task t2 = new Task("Attività numero due", d1, 0, "A");
        Task t3 = new Task("Attività numero tre", d1, 0, "A");
        Task t4 = new Task("Attività numero quattro", d1, 0, "A");
        Task t5 = new Task("Attività numero cinque", d2, 0, "A");
        myTaskSet.addTask(t1, myVisSet);
        myTaskSet.addTask(t2, myVisSet);
        myTaskSet.addTask(t3, myVisSet);
        myTaskSet.addTask(t4, myVisSet);
        myTaskSet.addTask(t5, myVisSet);
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