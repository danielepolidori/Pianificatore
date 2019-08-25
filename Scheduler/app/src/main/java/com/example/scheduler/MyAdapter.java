package com.example.scheduler;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

// [html.it]
// "È responsabile di estrarre i dati dal Data Source e di usare questi dati per creare e popolare i ViewHolder.
//  Quest’ultimi saranno poi inviati al Layout Manager del RecyclerView.Adapter"
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // [html.it]
    // "È l’insieme di dati utilizzato per popolare la lista tramite l’Adapter."
    private String[] mDataset;

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
		public ItemViewHolder(View itemView) {
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

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        // ...
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}