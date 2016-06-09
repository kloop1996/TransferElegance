package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;
import com.hmarka.kloop1996.transferelegance.viewmodel.LoginViewModel;

/**
 * Created by kloop1996 on 09.06.2016.
 */
public class ActivityLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransferEleganceApplication transferEleganceApplication = TransferEleganceApplication.get(this);

        if (transferEleganceApplication.isUserLoad()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish();


    }
}
