package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.content.Context;

/**
 * Created by kloop1996 on 09.06.2016.
 */
public class MainViewModel implements ViewModel {
    private Context context;

    public MainViewModel(Context context) {
        this.context = context;
    }

    @Override
    public void destroy() {
        context=null;
    }
}
