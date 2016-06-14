package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.content.Context;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class GoogleMapViewModel implements ViewModel {

    private Context context;



    @Override
    public void destroy() {
        context=null;
    }
}
