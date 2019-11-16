package com.example.scheduler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// "È responsabile di estrarre i dati dal Data Source e di usare questi dati per creare e popolare i ViewHolder.
//  Quest’ultimi saranno poi inviati al Layout Manager del RecyclerView.Adapter" [html.it]
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // "È l’insieme di dati utilizzato per popolare la lista tramite l’Adapter." [html.it]
    private VisualizeSet mDataset;   // deve essere inizializzato con 'new...' ?

    // An on-click handler that we've defined to make it easy for an Activity to interface with our RecyclerView
    final private ItemClickListener mOnClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and you provide access to all the views for a data item in a view holder
    // "È la chiave di volta tra il RecyclerView e l’Adapter e permette la riduzione nel numero di view da creare.
    //  Questo oggetto infatti fornisce il layout da popolare con i dati presenti nel DataSource e viene riutilizzato dal RecyclerView per ridurre il numero di layout da creare per popolare la lista." [html.it]
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // each data item is just a string in this case
        public TextView textView;

        public MyViewHolder(TextView v) {

            super(v);
            textView = v;

            textView.setOnClickListener(this);
        }

        // verrà invocato ogni qualvolta l’utente cliccherà su un elemento della lista
        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(VisualizeSet myDataset, ItemClickListener listener) {

        mDataset = myDataset;

        mOnClickListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Vis element = mDataset.getElement(position);

        holder.textView.setText(element.getText());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mDataset.getNumberOfElements();
    }

    public interface ItemClickListener {

        void onListItemClick(int clickedItemIndex);
    }
}