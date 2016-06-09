package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.app.DownloadManager;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.core.TransferEleganceService;
import com.hmarka.kloop1996.transferelegance.databinding.ActivityMainBinding;
import com.hmarka.kloop1996.transferelegance.model.ResponseToken;
import com.hmarka.kloop1996.transferelegance.viewmodel.MainViewModel;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainViewModel = new MainViewModel(this);

        activityMainBinding.setViewModel(mainViewModel);


    }


}
