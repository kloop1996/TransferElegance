package com.hmarka.kloop1996.transferelegance.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kloop1996 on 16.06.2016.
 */
public class SavePlace {
    private String name;
    private LatLng latLng;

    public SavePlace(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public SavePlace(){;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavePlace savePlace = (SavePlace) o;

        if (name != null ? !name.equals(savePlace.name) : savePlace.name != null) return false;
        return latLng != null ? latLng.equals(savePlace.latLng) : savePlace.latLng == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (latLng != null ? latLng.hashCode() : 0);
        return result;
    }
}
