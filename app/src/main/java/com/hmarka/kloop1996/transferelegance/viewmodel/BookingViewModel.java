package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by kloop1996 on 11.06.2016.
 */
public class BookingViewModel implements ViewModel {
    private Context context;

    public ObservableInt mapVisibility;
    public ObservableInt offflineProgressVisibiliy;

    public BookingViewModel(Context context) {


        this.context = context;
    }

    @Override
    public void destroy() {
        context=null;
    }

}
