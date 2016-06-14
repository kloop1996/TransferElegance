package com.hmarka.kloop1996.transferelegance.util;

import com.hmarka.kloop1996.transferelegance.model.TimeEntity;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class TimeConverUtil {
    public static TimeEntity getTimeEntity(int duration){
        TimeEntity result = new TimeEntity();
        result.setHour(duration/3600);
        result.setMinute((duration%3600)/60);

        return result;
    }
}
