package com.hmarka.kloop1996.transferelegance.core;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import rx.Observable;

/**
 * Created by kloop1996 on 19.06.2016.
 */
public class ReverseGeocodeService {

    public static String getAddress(Context context,double latitude, double longitude){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String result = null;

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                // sending back first address line and locality
                if (address.getLocality()!=null)
                    result= address.getAddressLine(0) + ", " + address.getLocality();
                else {
                    result= address.getAddressLine(0) + ", " + address.getAdminArea();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;

    }


    public static Observable<String> getAddressAsync(Context context,double latitude, double longitude){
        return Observable.just(getAddress(context,latitude,longitude));
    }
}
