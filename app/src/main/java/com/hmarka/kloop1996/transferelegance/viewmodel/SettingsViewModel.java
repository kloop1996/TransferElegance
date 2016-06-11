package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.content.Context;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Alexey on 10.06.2016.
 */
public class SettingsViewModel implements ViewModel {
    private Context context;
    private Subscriptions subscriptions;

    public SettingsViewModel(Context context) {
        this.context = context;
    }

    @Override
    public void destroy() {
        context=null;
    }
}
