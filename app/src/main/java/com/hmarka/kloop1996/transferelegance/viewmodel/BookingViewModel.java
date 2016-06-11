package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.content.Context;
import android.view.View;

/**
 * Created by kloop1996 on 11.06.2016.
 */
public class BookingViewModel implements ViewModel {
    private Context context;

    public BookingViewModel(Context context) {
        this.context = context;
    }

    @Override
    public void destroy() {
        context=null;
    }
}
