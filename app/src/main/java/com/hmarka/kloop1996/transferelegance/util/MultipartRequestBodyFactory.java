package com.hmarka.kloop1996.transferelegance.util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by kloop1996 on 09.06.2016.
 */
public class MultipartRequestBodyFactory {
    public static RequestBody createRequestBody(String value){
        return RequestBody.create(MediaType.parse("text/plain"),value);
    }
}
