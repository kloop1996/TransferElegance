package com.hmarka.kloop1996.transferelegance.ui.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.databinding.BookingFragmentBinding;
import com.hmarka.kloop1996.transferelegance.viewmodel.BookingViewModel;

/**
 * Created by kloop1996 on 11.06.2016.
 */
public class BookingFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener {
    private BookingFragmentBinding bookingFragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookingFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.booking_fragment,container,false);

        bookingFragmentBinding.setViewModel(new BookingViewModel(getActivity()));

        GoogleMapOptions options = new GoogleMapOptions();
        options.compassEnabled(true);

        MapFragment mMapFragment = MapFragment.newInstance(options);
        mMapFragment.getMapAsync(this);

        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_container, mMapFragment);
        fragmentTransaction.commit();

        return bookingFragmentBinding.getRoot();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapLongClickListener(this);
        googleMap.setMyLocationEnabled(true);

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        bookingFragmentBinding.currentLocation.setText(latLng.latitude+" "+latLng.longitude);
    }
}
