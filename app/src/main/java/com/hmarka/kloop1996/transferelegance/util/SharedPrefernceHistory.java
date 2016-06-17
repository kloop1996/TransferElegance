package com.hmarka.kloop1996.transferelegance.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hmarka.kloop1996.transferelegance.model.HistoryEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPrefernceHistory {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_History";

    public SharedPrefernceHistory() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<HistoryEntity> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, HistoryEntity product) {
        ArrayList<HistoryEntity> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<HistoryEntity>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, HistoryEntity product) {
        ArrayList<HistoryEntity> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<HistoryEntity> getFavorites(Context context) {
        SharedPreferences settings;
        List<HistoryEntity> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            HistoryEntity[] favoriteItems = gson.fromJson(jsonFavorites,
                    HistoryEntity[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<HistoryEntity>(favorites);
        } else
            return null;

        return (ArrayList<HistoryEntity>) favorites;
    }
}