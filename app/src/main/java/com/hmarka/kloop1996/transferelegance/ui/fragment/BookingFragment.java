package com.hmarka.kloop1996.transferelegance.ui.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;
import com.hmarka.kloop1996.transferelegance.databinding.BookingFragmentBinding;
import com.hmarka.kloop1996.transferelegance.viewmodel.BookingViewModel;

/**
 * Created by kloop1996 on 11.06.2016.
 */
public class BookingFragment extends Fragment implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener,PlaceSelectionListener {
    private BookingFragmentBinding bookingFragmentBinding;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private GoogleMap mGoogleMap;

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

//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .addApi(Places.GEO_DATA_API)
//                    .addApi(Places.PLACE_DETECTION_API)
//                    .build();
//        }

//        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
//                .getCurrentPlace(mGoogleApiClient, null);
//        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
//            @Override
//            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
//                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
//
//                }
//                likelyPlaces.release();
//            }
//        });

        initAutocompleteView();

        return bookingFragmentBinding.getRoot();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapLongClickListener(this);
        googleMap.setMyLocationEnabled(true);

        mGoogleMap = googleMap;

    }

    @Override
    public void onMapLongClick(LatLng latLng) {



    }


    @Override
    public void onPlaceSelected(Place place) {
       MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(place.getLatLng());
        markerOptions.title(place.getAddress().toString());


        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }

    @Override
    public void onError(Status status) {

    }


    private void initAutocompleteView(){
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment_from);
        autocompleteFragment.setHint(getResources().getString(R.string.from_hint));
        autocompleteFragment.setOnPlaceSelectedListener(this);

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment_to);
        autocompleteFragment.setHint(getResources().getString(R.string.destination_hint));
        autocompleteFragment.setOnPlaceSelectedListener(this);
    }

}
