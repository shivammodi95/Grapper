package com.android.grapper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by saraf on 10/23/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    //private String[] mDataset;

    private List<MyAdapterDataSet> DataSet;

    //public TextView rvTextView;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public TextView mTextView;
        protected TextView vName;

        public ViewHolder(View v) {
            super(v);
            //mTextView = v;
            vName=(TextView)v.findViewById(R.id.rvTextView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<MyAdapterDataSet> myDataset) {
        DataSet = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //TextView a = holder.mTextView.findViewById(R.id.trial);
        //a.setText(mDataset[position]);
        //rvTextView=holder.mTextView.findViewById(R.id.rvTextView);
        //rvTextView.setText(mDataset[0]);
        //holder.mTextView.setText(mDataset[position]);


        MyAdapterDataSet ci=DataSet.get(position);
        holder.vName.setText(ci.name);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return DataSet.size();
    }
}
