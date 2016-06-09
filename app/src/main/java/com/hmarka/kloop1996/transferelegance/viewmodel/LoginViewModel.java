package com.hmarka.kloop1996.transferelegance.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;
import com.hmarka.kloop1996.transferelegance.core.TransferEleganceService;
import com.hmarka.kloop1996.transferelegance.model.ResponseToken;
import com.hmarka.kloop1996.transferelegance.model.User;
import com.hmarka.kloop1996.transferelegance.ui.activtity.MainActivity;
import com.hmarka.kloop1996.transferelegance.util.MultipartRequestBodyFactory;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by kloop1996 on 06.06.2016.
 */
public class LoginViewModel implements ViewModel {

    private Context context;
    private Subscription subscription;

    private ObservableField<String> nameField;
    private ObservableField<String> phoneField;

    private String name;
    private String phone;

    public LoginViewModel(Context context) {
        nameField = new ObservableField<String>();
        phoneField = new ObservableField<String>();
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ObservableField<String> getNameField() {
        return nameField;
    }

    public void setNameField(ObservableField<String> nameField) {
        this.nameField = nameField;
    }

    public ObservableField<String> getPhoneField() {
        return phoneField;
    }

    public void setPhoneField(ObservableField<String> phoneField) {
        this.phoneField = phoneField;
    }

    public void onClickLogin(View view) {
        if (name == null || phone == null) {
            Toast.makeText(context, R.string.EMPTY_FIELD_MESSAGE, Toast.LENGTH_SHORT).show();
        }

        final TransferEleganceApplication transferEleganceApplication = TransferEleganceApplication.get(context);
        TransferEleganceService transferEleganceService = transferEleganceApplication.getTransferEleganceService();

        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();

        subscription = transferEleganceService.getToken(MultipartRequestBodyFactory.createRequestBody(name), MultipartRequestBodyFactory.createRequestBody(phone),
                MultipartRequestBodyFactory.createRequestBody(transferEleganceApplication.getDeviceToken()), MultipartRequestBodyFactory.createRequestBody("android"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(transferEleganceApplication.defaultSubscribeScheduler())
                .subscribe(new Subscriber<ResponseToken>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, R.string.INTERNET_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ResponseToken responseToken) {
                        transferEleganceApplication.setUserToken(responseToken.getToken());
                        transferEleganceApplication.setUser(new User(name,phone));

                        transferEleganceApplication.updateSharedPreference();
                    }
                });
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
                phone = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    @Override
    public void destroy() {
        context = null;
    }
}
