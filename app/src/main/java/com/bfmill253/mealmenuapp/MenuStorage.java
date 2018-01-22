package com.bfmill253.mealmenuapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by brandon on 1/21/18.
 *
 * This class will store/access the meal menu items. We use this to write new items to the disk
 * so that items can persist between sessions.
 */

public class MenuStorage {

    private static final String MENU_KEY = "menu";

    private static final String NON_VEGGIE_KEY = "non_veggie";
    private static final String VEGGIE_KEY = "veggie";

    private Context currentContext;
    private static MenuStorage instance;
    private SharedPreferences storage;

    private MenuStorage(Context mContext){
        currentContext = mContext;
        storage = currentContext.getSharedPreferences(MENU_KEY, Context.MODE_PRIVATE);
    }

    /**
     * Here we pass a live context to get the current instance of MenuStorage. This means everywhere
     * we want to use 'MenuStorage' to access the menu items we will insure that the current instance
     * of MenuStorage will have a live context. This keeps us from having to create/destroy multiple
     * instances of MenuStorage each time we need to access it.
     *
     * @param mContext a live context
     * @return the instance on MenuStorage
     */
    public static MenuStorage getInstance(Context mContext){
        if(instance != null){
            instance.setContext(mContext);
            return instance;
        }else {
            instance = new MenuStorage(mContext);
            return instance;
        }
    }

    private Set<String> getDefaultVeggies(){
        return new HashSet<>(Arrays.asList(new String[]{"French fries", "Veggieburger", "Carrots", "Apple", "Banana", "Milkshake"}));
    }

    private Set<String> getDefaultNonVeggies(){
        return new HashSet<>(Arrays.asList(new String[]{"Cheeseburger", "Hamburger", "Hot dog"}));
    }

    private void setContext(Context mContext){
        currentContext = mContext;
        storage = currentContext.getSharedPreferences(MENU_KEY, Context.MODE_PRIVATE);
    }

    public Set<String> getVeggieList(){
        Set<String> veggies = storage.getStringSet(VEGGIE_KEY, null);
        if(veggies == null){
            //we have an empty list so we will populate it with the default values
            veggies = getDefaultVeggies();
            //store the default values
            storeVeggieSet(veggies);
        }
        return veggies;
    }

    private void storeVeggieSet(Set<String> veggies){
        storage.edit().putStringSet(VEGGIE_KEY, veggies).apply();
    }

    public Set<String> getNonVeggieList(){
        Set<String> veggies = storage.getStringSet(NON_VEGGIE_KEY, null);
        if(veggies == null){
            //we have an empty list so we will populate it with the default values
            veggies = getDefaultNonVeggies();
            //store the default values
            storeNonVeggieSet(veggies);
        }
        return veggies;
    }

    private void storeNonVeggieSet(Set<String> veggies){
        storage.edit().putStringSet(NON_VEGGIE_KEY, veggies).apply();
    }

    public void addItem(String name, ItemType type){
        if(type == ItemType.VEGGIE){
            Set<String> newSet = new HashSet<>();
            Set<String> veggieSet = getVeggieList();
            newSet.addAll(veggieSet);
            newSet.add(name);
            storeVeggieSet(newSet);
        }else{
            Set<String> newSet = new HashSet<>();
            Set<String> veggieSet = getNonVeggieList();
            newSet.addAll(veggieSet);
            newSet.add(name);
            storeNonVeggieSet(newSet);
        }
    }

    enum ItemType{
        VEGGIE,
        NON_VEGGIE
    }

    public static class MenuItem {
        String name;
        ItemType type;

        public MenuItem(String name, ItemType type){
            this.name = name;
            this.type = type;
        }
    }

}
