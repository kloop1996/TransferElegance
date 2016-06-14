package com.hmarka.kloop1996.transferelegance.core;

import com.hmarka.kloop1996.transferelegance.model.ResponseDriverStatus;
import com.hmarka.kloop1996.transferelegance.model.ResponseToken;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by kloop1996 on 07.06.2016.
 */
public interface TransferEleganceService {


    @Multipart
    @POST("/api/nurse/login/")
    public rx.Observable<ResponseToken> getToken(@Part("name") RequestBody name, @Part("phone_number") RequestBody phone, @Part("token") RequestBody token, @Part("os_name") RequestBody osName);


    @Headers({
            "Content-Type : application/json"
    })
    @GET("/api/nurse/get_driver_status")
    public rx.Observable<ResponseDriverStatus> getDriverStatus();

    @Headers({
            "Content-Type : application/json"
    })
    @POST("/api/nurse/get_driver_status")
    public rx.Observable<ResponseBody> createOrder();

    class Factory {
        public static TransferEleganceService create() {
            Retrofit retrofit;
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://hmtest.pythonanywhere.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(TransferEleganceService.class);
        }
    }
}
