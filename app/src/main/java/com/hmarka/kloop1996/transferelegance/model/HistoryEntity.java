package com.hmarka.kloop1996.transferelegance.model;

import java.util.Date;

/**
 * Created by kloop1996 on 17.06.2016.
 */
public class HistoryEntity {
    private String fromName;
    private String toName;
    private String date;
    private int price;

    public HistoryEntity(String fromName, String toName, String date, int price) {
        this.fromName = fromName;
        this.toName = toName;
        this.date = date;
        this.price = price;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryEntity that = (HistoryEntity) o;

        if (price != that.price) return false;
        if (fromName != null ? !fromName.equals(that.fromName) : that.fromName != null)
            return false;
        if (toName != null ? !toName.equals(that.toName) : that.toName != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        int result = fromName != null ? fromName.hashCode() : 0;
        result = 31 * result + (toName != null ? toName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }
}
