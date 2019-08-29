package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
*/

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemClickListener {

    // Dal codice di RecyclerViewHTMLit
    //private static final int NUM_LIST_ITEMS = 100;      // numero di elementi nella lista

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // per prova
    private Toast mToast;

    // (creato da me)
    //String[] myDataset = new String[31];
    // c'è da definire un myDataset di List<Elem>

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        //
        // [html.it]
        // "È responsabile della creazione e del posizionamento delle view all’interno del RecyclerView."
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        /*
        myDataset[0] = getResources().getString(R.string.anno2019);
        myDataset[1] = getResources().getString(R.string.gen);
        myDataset[2] = getResources().getString(R.string.lun);
        myDataset[3] = getResources().getString(R.string.att);
        myDataset[4] = getResources().getString(R.string.att);
        myDataset[5] = getResources().getString(R.string.att);
        myDataset[6] = getResources().getString(R.string.mart);
        myDataset[7] = getResources().getString(R.string.att);
        myDataset[8] = getResources().getString(R.string.att);
        myDataset[9] = getResources().getString(R.string.mer);
        myDataset[10] = getResources().getString(R.string.att);
        myDataset[11] = getResources().getString(R.string.ven);
        myDataset[12] = getResources().getString(R.string.att);
        myDataset[13] = getResources().getString(R.string.vuota);
        myDataset[14] = getResources().getString(R.string.feb);
        myDataset[15] = getResources().getString(R.string.lun);
        myDataset[16] = getResources().getString(R.string.att);
        myDataset[17] = getResources().getString(R.string.mer);
        myDataset[18] = getResources().getString(R.string.att);
        myDataset[19] = getResources().getString(R.string.att);
        myDataset[20] = getResources().getString(R.string.att);
        myDataset[21] = getResources().getString(R.string.dom);
        myDataset[22] = getResources().getString(R.string.att);
        myDataset[23] = getResources().getString(R.string.vuota);
        myDataset[24] = getResources().getString(R.string.mar);
        myDataset[25] = getResources().getString(R.string.mart);
        myDataset[26] = getResources().getString(R.string.att);
        myDataset[27] = getResources().getString(R.string.vuota);
        myDataset[28] = getResources().getString(R.string.anno2020);
        myDataset[29] = getResources().getString(R.string.apr);
        myDataset[30] = getResources().getString(R.string.att);
        */

        /*
        // Dal codice di RecyclerViewHTMLit
        ButterKnife.bind(this);
        mAdapter = new MyAdapter(NUM_LIST_ITEMS);
        */

        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter(myDataset, this);
        mAdapter = new MyAdapter(myDataset.getProducts(), this);
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