package com.hmarka.kloop1996.transferelegance;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.hmarka.kloop1996.transferelegance.model.User;

/**
 * Created by kloop1996 on 06.06.2016.
 */
public class TransferEleganceApplication extends Application {

    private static final String PREFS_NAME = "AppPrefs";
    private SharedPreferences settings;
    private User user;

    public static TransferEleganceApplication get(Context context) {
        return (TransferEleganceApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initConfiguration();
    }

    private void initConfiguration(){
        settings = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        if (settings.contains("login") && settings.contains("password")) {
            user =  new User(settings.getString("login",""), settings.getString("password", ""));
        }

    }

    public boolean isUserLoad(){
        if (user!=null){
            return true;
        }else{
            return false;
        }
    }

}
