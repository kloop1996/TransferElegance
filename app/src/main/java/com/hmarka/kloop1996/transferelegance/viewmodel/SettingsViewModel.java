package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;
import com.hmarka.kloop1996.transferelegance.model.User;
import com.hmarka.kloop1996.transferelegance.ui.activtity.MainActivity;
import com.hmarka.kloop1996.transferelegance.ui.activtity.SettingsActivity;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Alexey on 10.06.2016.
 */
public class SettingsViewModel implements ViewModel {

    private String name;
    private String telephone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private Context context;

    public SettingsViewModel(Context context) {
        this.context = context;

        TransferEleganceApplication transferEleganceApplication = TransferEleganceApplication.get(context);

        telephone = transferEleganceApplication.getUser().getTelephone();
        name = transferEleganceApplication.getUser().getName();

    }

    public void onClick(View view) {
        TransferEleganceApplication transferEleganceApplication = TransferEleganceApplication.get(context);

        transferEleganceApplication.setUser(new User(name,telephone));
        transferEleganceApplication.updateSharedPreference();


        MainActivity.getInstance().updateTitle();
        ((Activity)context).onBackPressed();
    }

    public TextWatcher getNameEditTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    public TextWatcher getPhoneEditTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                telephone = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    @Override
    public void destroy() {
        context=null;
    }
}
