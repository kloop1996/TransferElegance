package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;
import com.hmarka.kloop1996.transferelegance.model.TimeEntity;
import com.hmarka.kloop1996.transferelegance.model.User;
import com.hmarka.kloop1996.transferelegance.ui.activtity.MainActivity;

import rx.Observable;

/**
 * Created by kloop1996 on 09.06.2016.
 */
public class MainViewModel implements ViewModel {
    private Context context;

    public ObservableBoolean stateDriver;
    public ObservableBoolean stateOrder;
    private int viewInvisible = View.INVISIBLE;
    private int viewVisible = View.VISIBLE;



    public void setAutocopleteVisible(ObservableInt autocopleteVisible) {
        this.autocopleteVisible = autocopleteVisible;
    }

    public ObservableInt autocopleteVisible;
    public ObservableInt timerVisible;
    public ObservableField<String> textTimer;
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
    private int countTime;

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

        this.time.set(String.format("%02d",time.getHour())+":"+String.format("%02d",time.getMinute()));


    }

    public void setAppointmentTime(TimeEntity time){
        String result;

        this.appointment.set(String.format("%02d",time.getHour())+":"+String.format("%02d",time.getMinute()));
    }

    public void setWaitUntilTime(TimeEntity time){
        String result;

        this.waitUntil.set(String.format("%02d",time.getHour())+":"+String.format("%02d",time.getMinute()));
    }

    public void setPrice(int price){
        this.fee.set(String.valueOf(price));
    }

    public MainViewModel(Context context) {
        stateDriver = new ObservableBoolean(true);
        stateOrder = new ObservableBoolean(false);
        autocopleteVisible = new ObservableInt(View.VISIBLE);
        timerVisible = new ObservableInt(View.INVISIBLE);
        distanse = new ObservableField<String>("-");
        countTime = 180;
        time= new ObservableField<String>("--:--");
        fee = new ObservableField<String>("");;

        textTimer = new ObservableField<String>();

        appointment= new ObservableField<String>("--:--");
        waitUntil = new ObservableField<String>("--:--");;
        this.context = context;
    }


    public void updateTime(){

        int h = countTime/60;
        int m = countTime%60;

        String hour;
        String minute;

        if (h<10)
            hour="0"+h;
        else
            hour=String.valueOf(h);

        if (m<10)
            minute="0"+m;
        else
            minute=String.valueOf(m);

        setTextTimer(hour+":"+minute);
        countTime--;

        if (countTime==0){
            countTime = 180;
        }
    }

    public void setTextTimer(String text){
        textTimer.set(text);
    }

    public void onClickTelephone(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + context.getResources().getString(R.string.number_alex)));
        context.startActivity(intent);
    }

    public void onClickCall(View view){
        ((MainActivity)context).executeCall(view);
    }

    @Override
    public void destroy() {
        context=null;
    }



}
