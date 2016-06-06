package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.content.Context;

/**
 * Created by kloop1996 on 06.06.2016.
 */
public class LoginViewModel implements ViewModel {

    private Context context;

    public LoginViewModel(Context context) {
        this.context = context;
    }

    @Override
    public void destroy() {
        context=null;
    }
}
