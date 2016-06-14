package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import rx.Observable;

/**
 * Created by kloop1996 on 09.06.2016.
 */
public class MainViewModel implements ViewModel {
    private Context context;

    public ObservableField<String> getDistanse() {
        return distanse;
    }

    public void setDistanse(ObservableField<String> distanse) {
        this.distanse = distanse;
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public void setTime(ObservableField<String> time) {
        this.time = time;
    }

    public ObservableField<String> getFee() {
        return fee;
    }

    public void setFee(ObservableField<String> fee) {
        this.fee = fee;
    }

    public ObservableField<String> getWaitUntil() {
        return waitUntil;
    }

    public void setWaitUntil(ObservableField<String> waitUntil) {
        this.waitUntil = waitUntil;
    }

    public ObservableField<String> getAppointment() {
        return appointment;
    }

    public void setAppointment(ObservableField<String> appointment) {
        this.appointment = appointment;
    }

    private ObservableField<String> distanse;
    private ObservableField<String> time;
    private ObservableField<String> fee;

    private ObservableField<String> appointment;
    private ObservableField<String> waitUntil;



    public MainViewModel(Context context) {
        distanse = new ObservableField<String>();
        time = new ObservableField<String>();
        fee  = new ObservableField<String>();

        appointment  = new ObservableField<String>();
        waitUntil = new ObservableField<String>();
        this.context = context;
    }

    @Override
    public void destroy() {
        context=null;
    }
}
