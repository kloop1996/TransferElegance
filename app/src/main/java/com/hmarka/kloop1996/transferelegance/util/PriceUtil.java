package com.hmarka.kloop1996.transferelegance.util;

import com.hmarka.kloop1996.transferelegance.Constants;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class PriceUtil {
    public static int getPriceWay(int duration,int distance){
        double multipleDowntime = Constants.PRICE_HOUR_DOWNTIME/3600;
        double multipleWaydistance = Constants.PRICE_HOUR_WAYTIME/1000;

        return (int)(multipleDowntime*duration+multipleWaydistance*distance);
    }
}
