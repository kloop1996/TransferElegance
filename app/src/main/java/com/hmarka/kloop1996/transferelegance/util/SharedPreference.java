package com.hmarka.kloop1996.transferelegance.util;

/**
 * Created by kloop1996 on 16.06.2016.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.android.gms.location.places.Place;
import com.google.gson.Gson;
import com.hmarka.kloop1996.transferelegance.model.SavePlace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<SavePlace> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, SavePlace product) {
        List<SavePlace> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<SavePlace>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, SavePlace product) {
        ArrayList<SavePlace> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<SavePlace> getFavorites(Context context) {
        SharedPreferences settings;
        List<SavePlace> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            SavePlace[] favoriteItems = gson.fromJson(jsonFavorites,
                    SavePlace[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<SavePlace>(favorites);
        } else
            return null;

        return (ArrayList<SavePlace>) favorites;
    }
}