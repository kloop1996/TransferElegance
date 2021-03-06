package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;

/**
 * Created by kloop1996 on 09.06.2016.
 */
public class ActivityLauncher extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent;
                TransferEleganceApplication transferEleganceApplication = TransferEleganceApplication.get(ActivityLauncher.this);

                if (transferEleganceApplication.isUserLoad()) {
                    mainIntent =  new Intent(ActivityLauncher.this, MainActivity.class);
                } else {
                    mainIntent = new Intent(ActivityLauncher.this, LoginActivity.class);
                }

                ActivityLauncher.this.startActivity(mainIntent);
                ActivityLauncher.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);




        finish();


    }
}
