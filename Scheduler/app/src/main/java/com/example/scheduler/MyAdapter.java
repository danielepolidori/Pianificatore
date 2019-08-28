package com.example.scheduler;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
/*
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
*/

// [html.it]
// "È responsabile di estrarre i dati dal Data Source e di usare questi dati per creare e popolare i ViewHolder.
//  Quest’ultimi saranno poi inviati al Layout Manager del RecyclerView.Adapter"
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // [html.it]
    // "È l’insieme di dati utilizzato per popolare la lista tramite l’Adapter."
    private String[] mDataset;

    /*
    // Dal codice di RecyclerViewHTMLit
    private static int viewHolderCount;     // numero di ViewHolder creati
    private int mNumberItems;               // numero di elementi della lista totali
    private Context parentContex;           // Context del parent
    */

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and you provide access to all the views for a data item in a view holder
    //
    // [html.it]
    // "È la chiave di volta tra il RecyclerView e l’Adapter e permette la riduzione nel numero di view da creare.
    //  Questo oggetto infatti fornisce il layout da popolare con i dati presenti nel DataSource e viene riutilizzato dal RecyclerView per ridurre il numero di layout da creare per popolare la lista."
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }

        /*
        // Dal codice di RecyclerViewHTMLit
        @BindView(R.id.tv_item_number)
		TextView mListItemNumberTV;
		@BindView(R.id.tv_view_holder_index)
		TextView mVHIndexTV;
		@BindView(R.id.iv_logo)
        ImageView mIVLogp;
		public MyViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
		void bind(int listIndex) {
			mListItemNumberTV.setText(String.valueOf(listIndex));
		}
        */

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    /*
    // Dal codice di RecyclerViewHTMLit
    public MyAdapter(int numberOfItems) {
        mNumberItems = numberOfItems;
        viewHolderCount = 0;
    }
    */

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                     int viewType) {

        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        // ...
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

        /*
        // Dal codice di RecyclerViewHTMLit
        parentContex = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(parentContex);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        MyViewHolder holder = new MyViewHolder(view);   // come parametro prende in ingresso un oggetto di tipo View, ossia il layout list_item
        holder.mVHIndexTV.setText("ViewHolder index: " + viewHolderCount);
        viewHolderCount++;
        return holder;
        */
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset[position]);

        // Dal codice di RecyclerViewHTMLit
        //holder.bind(position);      // invocazione del metodo bind della classe ItemViewHolder per impostare la posizione corrente dell’elemento
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
        //return mNumberItems;
    }

}