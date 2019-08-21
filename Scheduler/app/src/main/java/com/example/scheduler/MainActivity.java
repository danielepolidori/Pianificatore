package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // (creato da me)
        String[] myDataset = new String[7];
        myDataset[0] = getResources().getString(R.string.lun);
        myDataset[1] = getResources().getString(R.string.mar);
        myDataset[2] = getResources().getString(R.string.mer);
        myDataset[3] = getResources().getString(R.string.gio);
        myDataset[4] = getResources().getString(R.string.ven);
        myDataset[5] = getResources().getString(R.string.sab);
        myDataset[6] = getResources().getString(R.string.dom);
        /*
        myDataset[7] = getResources().getString(R.string.lun);
        myDataset[8] = getResources().getString(R.string.mar);
        myDataset[9] = getResources().getString(R.string.mer);
        myDataset[10] = getResources().getString(R.string.gio);
        myDataset[11] = getResources().getString(R.string.ven);
        myDataset[12] = getResources().getString(R.string.sab);
        myDataset[13] = getResources().getString(R.string.dom);
        myDataset[14] = getResources().getString(R.string.lun);
        myDataset[15] = getResources().getString(R.string.mar);
        myDataset[16] = getResources().getString(R.string.mer);
        myDataset[17] = getResources().getString(R.string.gio);
        myDataset[18] = getResources().getString(R.string.ven);
        myDataset[19] = getResources().getString(R.string.sab);
        myDataset[20] = getResources().getString(R.string.dom);
        myDataset[21] = getResources().getString(R.string.lun);
        myDataset[22] = getResources().getString(R.string.mar);
        myDataset[23] = getResources().getString(R.string.mer);
        myDataset[24] = getResources().getString(R.string.gio);
        myDataset[25] = getResources().getString(R.string.ven);
        myDataset[26] = getResources().getString(R.string.sab);
        */

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }
    // ...
}

// "The views in the list are represented by view holder objects. These objects are instances of a class you define by extending RecyclerView.ViewHolder"
// "The view holder objects are managed by an adapter, which you create by extending RecyclerView.Adapter"

// "Once you have added a RecyclerView widget to your layout, obtain a handle to the object, connect it to a layout manager, and attach an adapter for the data to be displayed"