package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.view.View;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;
import com.hmarka.kloop1996.transferelegance.model.TimeEntity;
import com.hmarka.kloop1996.transferelegance.model.User;
import com.hmarka.kloop1996.transferelegance.ui.activtity.MainActivity;

/**
 * Created by kloop1996 on 09.06.2016.
 */
public class MainViewModel implements ViewModel {
    private Context context;

    public ObservableBoolean stateDriver;
    public ObservableBoolean stateOrder;
    private double currentDistance;
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

    private ObservableField<String> distanse = new ObservableField<String>();

    private ObservableField<String> time;
    private ObservableField<String> fee;

    private ObservableField<String> appointment;
    private ObservableField<String> waitUntil;

    public void setDistance(int distance){
        currentDistance = distance/1000.0;
        this.distanse.set(String.format("%.1f",(distance/1000.0)));
    }

    public void setTime(TimeEntity time){
        String result;

//        result= String.format("_%2d",time.getHour())+":"+"_%2d",String.format("_%2d",time.getHour());
        this.time.set(String.format("%02d",time.getHour())+":"+String.format("%02d",time.getMinute()));


    }

    public void setAppointmentTime(TimeEntity time){
        String result;

//        result= String.format("_%2d",time.getHour())+":"+"_%2d",String.format("_%2d",time.getHour());
        this.appointment.set(String.format("%02d",time.getHour())+":"+String.format("%02d",time.getMinute()));
    }

    public void setWaitUntilTime(TimeEntity time){
        String result;

//        result= String.format("_%2d",time.getHour())+":"+"_%2d",String.format("_%2d",time.getHour());
        this.waitUntil.set(String.format("%02d",time.getHour())+":"+String.format("%02d",time.getMinute()));
    }

    public void setPrice(int price){
        this.fee.set(String.valueOf(price));
    }

    public MainViewModel(Context context) {
        stateDriver = new ObservableBoolean(true);
        stateOrder = new ObservableBoolean(false);
        distanse = new ObservableField<String>("-");

        time= new ObservableField<String>("--:--");
        fee = new ObservableField<String>("");;

        appointment= new ObservableField<String>("--:--");
        waitUntil = new ObservableField<String>("--:--");;
        this.context = context;
    }

    public void onClickTelephone(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + context.getResources().getString(R.string.number_alex)));
        context.startActivity(intent);
    }

    public void onClickCall(View view){
        ((MainActivity)context).executeCall();
    }

    @Override
    public void destroy() {
        context=null;
    }
}
