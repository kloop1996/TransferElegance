package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hmarka.kloop1996.transferelegance.Constants;
import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;
import com.hmarka.kloop1996.transferelegance.core.TransferEleganceService;
import com.hmarka.kloop1996.transferelegance.databinding.ActivityMainBinding;
import com.hmarka.kloop1996.transferelegance.databinding.BookingFragmentBinding;
import com.hmarka.kloop1996.transferelegance.model.ResponseCreateOrder;
import com.hmarka.kloop1996.transferelegance.model.ResponseDriverStatus;
import com.hmarka.kloop1996.transferelegance.model.ResponseToken;
import com.hmarka.kloop1996.transferelegance.model.TimeEntity;
import com.hmarka.kloop1996.transferelegance.model.User;
import com.hmarka.kloop1996.transferelegance.ui.dialog.EndTimePickerDialog;
import com.hmarka.kloop1996.transferelegance.ui.fragment.CustomPlaceAutoCompleteFragment;
import com.hmarka.kloop1996.transferelegance.ui.fragment.OfflineMessageFragment;
import com.hmarka.kloop1996.transferelegance.util.MultipartRequestBodyFactory;
import com.hmarka.kloop1996.transferelegance.util.PriceUtil;
import com.hmarka.kloop1996.transferelegance.util.TimeConverUtil;
import com.hmarka.kloop1996.transferelegance.viewmodel.MainViewModel;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerDragListener, RoutingListener {



    private static MainActivity instance;

    private MainViewModel mainViewModel;
    private ActivityMainBinding activityMainBinding;

    private boolean startMap = false;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private GoogleMap mGoogleMap;

    private LatLng from;
    private LatLng to;

    private Marker markerFrom;
    private Marker markerTo;

    public static TimeEntity appointmentTime;
    public static TimeEntity countTime;

    private int currentDistanse;
    private int currentDuration;

    private Drawer drawer;
    private Toolbar toolbar;

    private ProgressDialog pd;

    private Subscription subscription;
    private CustomPlaceAutoCompleteFragment toAutocomplete;
    private CustomPlaceAutoCompleteFragment fromAutocomplete;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        instance=this;
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainViewModel = new MainViewModel(this);

        activityMainBinding.setViewModel(mainViewModel);

        toolbar = activityMainBinding.toolbar;
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.second_name);

        drawer = new DrawerBuilder(this)
                .withRootView(R.id.drawer_container)
                .withToolbar(toolbar)
                .withCloseOnClick(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDisplayBelowStatusBar(false)
                .addDrawerItems(
                        //new PrimaryDrawerItem().withName(R.string.history).withIcon(R.drawable.ic_menu).withTag(Constants.HISTORY_FRAGMENT),
                        new PrimaryDrawerItem().withName(R.string.settings).withIcon(R.drawable.ic_menu).withTag(Constants.SETTINGS_FRAGMENT)

                )
                .withSavedInstance(savedInstanceState)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        changeActivity((String)drawerItem.getTag());
                        return false;
                    }
                })
                .build();

        pd = new ProgressDialog(this);
        pd.setTitle("Check driver status");
        pd.show();


        final TransferEleganceApplication transferEleganceApplication = TransferEleganceApplication.get(this);
        TransferEleganceService transferEleganceService = transferEleganceApplication.getTransferEleganceService();
        ResponseDriverStatus responseDriverStatus = null;
        try {


           mainViewModel.stateDriver.set(transferEleganceService.getDriverStatus().execute().body().getStatus());
        } catch (IOException e) {
            mainViewModel.stateDriver.set(false);
        }

        pd.cancel();

        if (!mainViewModel.stateDriver.get()) {
            mainViewModel.stateOrder.set(true);
            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.map_container, new OfflineMessageFragment());
            fragmentTransaction.commit();

        }else {

            GoogleMapOptions options = new GoogleMapOptions();
            options.compassEnabled(true);
            options.useViewLifecycleInFragment(false);
            MapFragment mMapFragment = MapFragment.newInstance(options);
            mMapFragment.getMapAsync(this);

            FragmentTransaction fragmentTransaction =
                    getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.map_container, mMapFragment);
            fragmentTransaction.commit();

            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .build();
            }

            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                            builder.build());

            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location requests here.

                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied, but this can be fixed
                            // by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(MainActivity.this, 2);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way
                            // to fix the settings so we won't show the dialog.

                            break;

                    }
                }
            });


        }
        initAutocompleteView();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapLongClickListener(this);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerDragListener(this);

        mGoogleMap = googleMap;

        mGoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (!startMap){
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
            }
        });

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }


    private void initAutocompleteView(){
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment_from);
        fromAutocomplete = (CustomPlaceAutoCompleteFragment)autocompleteFragment;
        if (!mainViewModel.stateDriver.get()){
            fromAutocomplete.setEnable(false);
        }

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                if (markerFrom != null) {
                    markerFrom.remove();

                }


                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(place.getLatLng());
                markerOptions.snippet(place.getAddress().toString());
                markerOptions.title(getResources().getString(R.string.from));
                markerOptions.draggable(true);
                from = place.getLatLng();

                markerFrom = mGoogleMap.addMarker(markerOptions);
                markerFrom.showInfoWindow();
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                notifySelectPlace();
            }

            @Override
            public void onError(Status status) {

            }
        });

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment_to);
        toAutocomplete = (CustomPlaceAutoCompleteFragment)autocompleteFragment;
        if (!mainViewModel.stateDriver.get())
            toAutocomplete.setEnable(false);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(place.getLatLng());

                markerOptions.title(getResources().getString(R.string.to));
                markerOptions.snippet(place.getAddress().toString());
                to = place.getLatLng();

                markerTo = mGoogleMap.addMarker(markerOptions);
                markerTo.showInfoWindow();
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                notifySelectPlace();
            }

            @Override
            public void onError(Status status) {

            }
        });
        ;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient!=null)
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient!=null)
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        from = marker.getPosition();
        marker.setSnippet("");
    }

    private void notifySelectPlace() {
        if (from != null && to != null) {
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .waypoints(to, from)
                    .key(getResources().getString(R.string.google_direction_key))
                    .build();
            routing.execute();
            DialogFragment fragmentDialog = new EndTimePickerDialog();
            fragmentDialog.show(getFragmentManager(),"");
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int i) {

        List<Polyline> polylines = new ArrayList<>();
        //add route(s) to the map.
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        latLngBuilder.include(from);
        latLngBuilder.include(to);

        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.width(25);
        polyOptions.color(R.color.polyline_color);
        polyOptions.addAll(route.get(i).getPoints());
        for (LatLng latLng : route.get(i).getPoints()){
            latLngBuilder.include(latLng);
        }
        Polyline polyline = mGoogleMap.addPolyline(polyOptions);
        polylines.add(polyline);
        route.get(i);
        //Toast.makeText(this, "Route " + (i + 1) + ": distance - " + route.get(0).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();

        mainViewModel.setDistance(route.get(0).getDistanceValue());
        currentDistanse = route.get(0).getDistanceValue();
        currentDuration=route.get(0).getDurationValue();
        mainViewModel.setTime(TimeConverUtil.getTimeEntity(route.get(0).getDurationValue()));

        LatLngBounds latLngBounds = latLngBuilder.build();
        int size = getResources().getDisplayMetrics().widthPixels;
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds,size,size,25);
        mGoogleMap.moveCamera(track);


    }

    @Override
    public void onRoutingCancelled() {

    }

    private void changeActivity(String tag){
        switch (tag){
            case Constants.SETTINGS_FRAGMENT:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case Constants.HISTORY_FRAGMENT:
                startActivity(new Intent(this, HistoryActivity.class));
                break;

        }
    }

    public void notifySetTime(){

        if (appointmentTime!=null)
            mainViewModel.setAppointmentTime(appointmentTime);
        if(countTime!=null){
            mainViewModel.setWaitUntilTime(countTime);
        }

        double divider = Constants.PRICE_HOUR_WAYTIME/3600.0;

       // mainViewModel.setPrice((int)(currentDuration*divider)+PriceUtil.getPriceDownTime(appointmentTime,countTime));
        mainViewModel.setPrice(((int)(currentDuration*divider))+(int)((countTime.getAbsoluteValue() - appointmentTime.getAbsoluteValue())*0.83));

        mainViewModel.stateOrder.set(true);


    }

    public void executeCall(){
        final TransferEleganceApplication transferEleganceApplication = TransferEleganceApplication.get(this);
        TransferEleganceService transferEleganceService = transferEleganceApplication.getTransferEleganceService();

        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        String token = transferEleganceApplication.getUserToken();
        subscription = transferEleganceService.createOrder(from.latitude+","+from.longitude, appointmentTime.getHour()+":"+appointmentTime.getMinute(),
                countTime.getHour()+":"+countTime.getMinute(),transferEleganceApplication.getUserToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(transferEleganceApplication.defaultSubscribeScheduler())
                .subscribe(new Subscriber<ResponseCreateOrder>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(ResponseCreateOrder responseCreateOrder) {
                        Toast.makeText(MainActivity.this,"Sucsess",Toast.LENGTH_SHORT).show();
                    }
                });



    }
    }



