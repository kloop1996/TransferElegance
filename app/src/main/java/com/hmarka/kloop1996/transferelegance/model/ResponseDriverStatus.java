package com.hmarka.kloop1996.transferelegance.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by kloop1996 on 12.06.2016.
 */
public class ResponseDriverStatus {

    @SerializedName("0")
    private DriverStatus data;

    public ResponseDriverStatus(DriverStatus data) {
        this.data = data;
    }

    public DriverStatus getData() {
        return data;
    }

    public void setData(DriverStatus data) {
        this.data = data;
    }

    class DriverStatus{
        private String message;

        @SerializedName("last_geo_update_time")
        private Date lastGeoUpdateTime;

        @SerializedName("is_active")
        private boolean active;

        public DriverStatus(String message, Date lastGeoUpdateTime, boolean active) {
            this.message = message;
            this.lastGeoUpdateTime = lastGeoUpdateTime;
            this.active = active;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DriverStatus that = (DriverStatus) o;

            if (active != that.active) return false;
            if (message != null ? !message.equals(that.message) : that.message != null)
                return false;
            return lastGeoUpdateTime != null ? lastGeoUpdateTime.equals(that.lastGeoUpdateTime) : that.lastGeoUpdateTime == null;

        }

        @Override
        public int hashCode() {
            int result = message != null ? message.hashCode() : 0;
            result = 31 * result + (lastGeoUpdateTime != null ? lastGeoUpdateTime.hashCode() : 0);
            result = 31 * result + (active ? 1 : 0);
            return result;
        }
    }
}
