package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;

/**
 * Created by Alexey on 15.06.2016.
 */
public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(SplashScreen.this, ActivityLauncher.class);

                Intent mainIntent;
                TransferEleganceApplication transferEleganceApplication = TransferEleganceApplication.get(SplashScreen.this);

                if (transferEleganceApplication.isUserLoad()) {
                    mainIntent =  new Intent(SplashScreen.this, MainActivity.class);
                } else {
                    mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                }

//                ActivityLauncher.this.startActivity(mainIntent);
//                ActivityLauncher.this.finish();
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
