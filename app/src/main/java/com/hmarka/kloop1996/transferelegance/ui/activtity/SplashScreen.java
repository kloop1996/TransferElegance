package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;
import com.hmarka.kloop1996.transferelegance.model.ResponseCreateOrder;
import com.hmarka.kloop1996.transferelegance.model.ResponseDriverStatus;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Alexey on 15.06.2016.
 */
public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private Subscription subscription;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);

        subscription = TransferEleganceApplication.get(this).getTransferEleganceService().getDriverStatus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(TransferEleganceApplication.get(this).defaultSubscribeScheduler())
                .subscribe(new Subscriber<ResponseDriverStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        TransferEleganceApplication.get(SplashScreen.this).setDriverStatus(false);
                    }

                    @Override
                    public void onNext(ResponseDriverStatus responseDriverStatus) {
                        TransferEleganceApplication.get(SplashScreen.this).setDriverStatus(responseDriverStatus.getStatus());
                    }
                });

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
               changeActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void changeActivity(){


        Intent mainIntent;
        TransferEleganceApplication transferEleganceApplication = TransferEleganceApplication.get(SplashScreen.this);

        if (transferEleganceApplication.isUserLoad()) {
            mainIntent =  new Intent(SplashScreen.this, MainActivity.class);
        } else {
            mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
        }


        SplashScreen.this.startActivity(mainIntent);
        SplashScreen.this.finish();
    }
}
