package com.bfmill253.mealmenuapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by brandon on 1/21/18.
 */

public class SubmitAdapter extends RecyclerView.Adapter<SubmitAdapter.ViewHolder> {

    List<String> items;
    Context mContext;

    public SubmitAdapter(Context mContext, List<String> items){
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new TextView(mContext));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView item;
        public ViewHolder(View itemView) {
            super(itemView);
            item = (TextView)itemView;
        }
    }
}
