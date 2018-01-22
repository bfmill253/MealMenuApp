package com.bfmill253.mealmenuapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by brandon on 1/21/18.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context mContext;
    private List<MenuStorage.MenuItem> mergedMenu;
    private List<CheckBox> liveMenu;


    public MenuAdapter(Context mContext, List<MenuStorage.MenuItem> mergedMenu){
        this.mContext = mContext;
        this.mergedMenu = mergedMenu;
        liveMenu = new ArrayList<>();
    }

    public List<String> fetchSelected(){
        List<String> selected = new ArrayList<>();
        for(CheckBox c : liveMenu){
            if(c.isChecked()){
                selected.add(c.getText().toString());
            }
        }
        return selected;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new CheckBox(mContext));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuStorage.MenuItem cItem = mergedMenu.get(position);
        holder.item.setText(cItem.name);
        if(cItem.type == MenuStorage.ItemType.VEGGIE){
            holder.item.setTextColor(mContext.getColor(android.R.color.holo_green_dark));
        }else{
            holder.item.setTextColor(mContext.getColor(android.R.color.holo_red_dark));
        }
        liveMenu.add(holder.item);
    }

    @Override
    public int getItemCount() {
        return mergedMenu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (CheckBox)itemView;
        }
    }
}
