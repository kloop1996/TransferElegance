package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.databinding.LoginActivityBinding;
import com.hmarka.kloop1996.transferelegance.viewmodel.LoginViewModel;

/**
 * Created by kloop1996 on 06.06.2016.
 */
public class LoginActivity extends AppCompatActivity {

    private LoginActivityBinding loginActivityBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivityBinding = DataBindingUtil.setContentView(this, R.layout.login_activity);

        loginViewModel = new LoginViewModel(this);
        loginActivityBinding.setViewModel(loginViewModel);

    }
}
