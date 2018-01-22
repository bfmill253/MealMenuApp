package com.bfmill253.mealmenuapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    /**
     * Butterknife bindings
     */
    @BindView(R.id.menu_grid) RecyclerView menuItems;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.veggie_checkbox) CheckBox veggieFilter;
    @BindView(R.id.non_veggie_checkbox) CheckBox nonVeggieFilter;

    MenuStorage storage;
    MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        menuItems.setLayoutManager(new GridLayoutManager(this, 2));
        storage =  MenuStorage.getInstance(this);
        getItemsAndShow();
    }

    private void showMenuList(List<MenuStorage.MenuItem> mergedList){
        adapter = new MenuAdapter(this, mergedList);
        menuItems.setAdapter(adapter);
    }

    private void getItemsAndShow(){
        ArrayList<MenuStorage.MenuItem> mergedList = new ArrayList<>();
        if(veggieFilter.isChecked()){
            for(String name : storage.getVeggieList()){
                mergedList.add(new MenuStorage.MenuItem(name, MenuStorage.ItemType.VEGGIE));
            }
        }
        if(nonVeggieFilter.isChecked()){
            for(String name : storage.getNonVeggieList()){
                mergedList.add(new MenuStorage.MenuItem(name, MenuStorage.ItemType.NON_VEGGIE));
            }
        }
        showMenuList(mergedList);
    }

    private void showAddItemDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View v = this.getLayoutInflater().inflate(R.layout.add_item_dialog, null);
        final AlertDialog d = builder.setView(v).create();
        v.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name;
                MenuStorage.ItemType type;
                try{
                    name = ((TextInputLayout)v.findViewById(R.id.textInputLayout)).getEditText().getText().toString();
                }catch (Exception e){return;}
                if(((Switch)v.findViewById(R.id.veggie_switch)).isChecked()){
                    type = MenuStorage.ItemType.VEGGIE;
                }else{
                    type = MenuStorage.ItemType.NON_VEGGIE;
                }
                storage.addItem(name, type);
                getItemsAndShow();
                d.dismiss();
            }
        });
        v.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        d.show();
    }

    private void showSubmitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View v = this.getLayoutInflater().inflate(R.layout.submit_dialog, null);
        final AlertDialog d = builder.setView(v).setCancelable(false).create();
        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItemsAndShow();
                d.dismiss();
            }
        });
        ((RecyclerView)v.findViewById(R.id.submit_list)).setLayoutManager(new GridLayoutManager(this, 2));
        ((RecyclerView)v.findViewById(R.id.submit_list)).setAdapter(new SubmitAdapter(this, adapter.fetchSelected()));
        d.show();
    }

    /**
     * handle the FAB being clicked by displaying the 'Add Item' dialog
     */
    @OnClick(R.id.fab)
    public void fabAddItemClick(){
        showAddItemDialog();
    }

    /**
     * handle the submit button being clicked
     */
    @OnClick(R.id.submit_button)
    public void submitClicked(){
        showSubmitDialog();
    }

    @OnCheckedChanged({R.id.veggie_checkbox, R.id.non_veggie_checkbox})
    public void onFilterChanged(){
        getItemsAndShow();
    }





}
