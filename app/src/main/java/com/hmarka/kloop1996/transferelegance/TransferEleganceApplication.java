package com.hmarka.kloop1996.transferelegance;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import com.hmarka.kloop1996.transferelegance.model.HistoryEntity;
import com.hmarka.kloop1996.transferelegance.model.SavePlace;
import com.hmarka.kloop1996.transferelegance.util.SharedPreferenceFavourite;
import com.hmarka.kloop1996.transferelegance.core.TransferEleganceService;
import com.hmarka.kloop1996.transferelegance.model.User;
import com.hmarka.kloop1996.transferelegance.util.SharedPrefernceHistory;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;
import rx.schedulers.Schedulers;


/**
 * Created by kloop1996 on 06.06.2016.
 */
public class TransferEleganceApplication extends Application {

    private static final String PREFS_NAME = "AppPrefs";

    private SharedPreferences settings;
    private Scheduler defaultSubscribeScheduler;
    private TransferEleganceService transferEleganceService;

    private List<SavePlace> favouritePlaces;
    private SharedPreferenceFavourite favouritePlacesSave;

    public List<HistoryEntity> getHistories() {
        return histories;
    }

    private List<HistoryEntity> histories;

    public void setHistories(List<HistoryEntity> histories){this.histories=histories;}
    private SharedPrefernceHistory historySave;

    private User user;
    private String deviceToken;
    private String userToken;

    public static TransferEleganceApplication get(Context context) {
        return (TransferEleganceApplication) context.getApplicationContext();
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    public TransferEleganceService getTransferEleganceService() {
        if (transferEleganceService == null) {
            transferEleganceService = TransferEleganceService.Factory.create();
        }
        return transferEleganceService;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initConfiguration();
    }

    private void initConfiguration(){
        settings = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        favouritePlacesSave = new SharedPreferenceFavourite();
        favouritePlaces = favouritePlacesSave.getFavorites(getApplicationContext());

        if (favouritePlaces == null) {
            favouritePlaces = new ArrayList<SavePlace>();
        }

        historySave = new SharedPrefernceHistory();
        histories = historySave.getFavorites(getApplicationContext());

        if (histories==null){
            histories = new ArrayList<HistoryEntity>();
        }

        if (settings.contains(Constants.NAME) && settings.contains(Constants.TELEPHONE)) {
            user = new User(settings.getString(Constants.NAME, ""), settings.getString(Constants.TELEPHONE, ""));
        }

        if (settings.contains(Constants.USER_TOKEN)) {
            userToken = settings.getString(Constants.USER_TOKEN, "");
        }

        deviceToken = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }


    public boolean isUserLoad(){
        if (userToken!=null){
            return true;
        }else{
            return false;
        }
    }

    public void updateSharedPreference(){
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(Constants.USER_TOKEN,userToken);
        editor.putString(Constants.NAME,user.getName());
        editor.putString(Constants.TELEPHONE,user.getTelephone());

        editor.apply();
    }

    public List<SavePlace> getFavourite() {
        return favouritePlaces;
    }

    public void setFavouritePlaces(List<SavePlace> favouritePlaces) {
        this.favouritePlaces = favouritePlaces;
    }

    public void updateFavouritePlace() {
        favouritePlacesSave.saveFavorites(getApplicationContext(), favouritePlaces);
    }

    public void updateHistory(){
        historySave.saveFavorites(getApplicationContext(),histories);
    }
}
