package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.databinding.SettingsActivityBinding;
import com.hmarka.kloop1996.transferelegance.viewmodel.SettingsViewModel;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class SettingsActivity extends AppCompatActivity {

    private SettingsActivityBinding settingsActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsActivityBinding = DataBindingUtil.setContentView(this, R.layout.settings_activity);
        settingsActivityBinding.setViewModel(new SettingsViewModel(this));

        setSupportActionBar(settingsActivityBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
