package com.hmarka.kloop1996.transferelegance.core;

import com.hmarka.kloop1996.transferelegance.model.ResponseRoute;
import com.hmarka.kloop1996.transferelegance.model.ResponseToken;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public interface GoogleDirectionsService {

    @GET("/maps/api/directions/json")
    rx.Observable<ResponseRoute> getRoute(
            @Query(value = "origin") String position,
            @Query(value = "destination") String destination,
            @Query(value = "key") String key);


    class Factory {
        public static GoogleDirectionsService create() {
            Retrofit retrofit;
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(GoogleDirectionsService.class);
        }
    }
}
