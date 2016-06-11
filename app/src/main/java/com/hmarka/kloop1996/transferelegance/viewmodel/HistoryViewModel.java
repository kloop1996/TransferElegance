package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.content.Context;

import rx.Subscription;

/**
 * Created by Alexey on 10.06.2016.
 */
public class HistoryViewModel implements ViewModel {

    private Context context;
    private Subscription subscription;

    public HistoryViewModel(Context context) {
        this.context = context;
    }

    @Override
    public void destroy() {
        context=null;
    }
}

