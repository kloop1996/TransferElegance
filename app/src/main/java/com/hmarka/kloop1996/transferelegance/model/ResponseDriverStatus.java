package com.hmarka.kloop1996.transferelegance.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by kloop1996 on 12.06.2016.
 */
public class ResponseDriverStatus {

    @SerializedName("0")
    private DriverStatus driverStatus;

    public ResponseDriverStatus(DriverStatus data) {
        this.driverStatus = data;
    }

    public DriverStatus getData() {
        return driverStatus;
    }

    public void setData(DriverStatus data) {
        this.driverStatus = data;
    }

    public boolean getStatus(){return driverStatus.data.active;}

    class DriverStatus{
        private String message;

        public DriverStat data;
        class DriverStat{
            @SerializedName("last_geo_update_time")
            private Date lastGeoUpdateTime;

            @SerializedName("is_active")
            public boolean active;

            public DriverStat(Date lastGeoUpdateTime, boolean active) {
                this.lastGeoUpdateTime = lastGeoUpdateTime;
                this.active = active;
            }

            public Date getLastGeoUpdateTime() {
                return lastGeoUpdateTime;
            }

            public void setLastGeoUpdateTime(Date lastGeoUpdateTime) {
                this.lastGeoUpdateTime = lastGeoUpdateTime;
            }

            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }
        }

    }
}
