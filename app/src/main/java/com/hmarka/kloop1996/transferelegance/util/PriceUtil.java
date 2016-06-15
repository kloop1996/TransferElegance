package com.hmarka.kloop1996.transferelegance.util;

import com.hmarka.kloop1996.transferelegance.Constants;
import com.hmarka.kloop1996.transferelegance.model.TimeEntity;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class PriceUtil {
    public static int getPriceWay(int duration,int distance){
        double multipleDowntime = Constants.PRICE_HOUR_DOWNTIME/3600;
        double multipleWaydistance = Constants.PRICE_HOUR_WAYTIME/1000;

        return (int)(multipleDowntime*duration+multipleWaydistance*distance);
    }

    public static int getPriceDownTime(TimeEntity start,TimeEntity end){
        int startAbsoluteValue = start.getHour()*60+start.getMinute();
        int endAbsoluteValue = end.getHour()*60+end.getMinute();

        if (startAbsoluteValue>endAbsoluteValue) {
            endAbsoluteValue += 1440;
        }

        double multiple = Constants.PRICE_HOUR_DOWNTIME/60.0;

        return (int) multiple * (endAbsoluteValue-startAbsoluteValue);
    }
}
