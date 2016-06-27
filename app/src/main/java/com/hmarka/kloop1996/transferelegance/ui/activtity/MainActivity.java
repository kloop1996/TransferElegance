package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.google.android.gms.common.data.DataBufferObserver;
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
import com.hmarka.kloop1996.transferelegance.core.ReverseGeocodeService;
import com.hmarka.kloop1996.transferelegance.core.TransferEleganceService;
import com.hmarka.kloop1996.transferelegance.databinding.ActivityMainBinding;
import com.hmarka.kloop1996.transferelegance.model.HistoryEntity;
import com.hmarka.kloop1996.transferelegance.model.ResponseCreateOrder;
import com.hmarka.kloop1996.transferelegance.model.ResponseDriverStatus;
import com.hmarka.kloop1996.transferelegance.model.ResponseStatusOrder;
import com.hmarka.kloop1996.transferelegance.model.SavePlace;
import com.hmarka.kloop1996.transferelegance.model.TimeEntity;
import com.hmarka.kloop1996.transferelegance.ui.dialog.DialogFactory;
import com.hmarka.kloop1996.transferelegance.ui.dialog.EndTimePickerDialog;
import com.hmarka.kloop1996.transferelegance.ui.fragment.CustomPlaceAutoCompleteFragment;
import com.hmarka.kloop1996.transferelegance.ui.fragment.OfflineMessageFragment;
import com.hmarka.kloop1996.transferelegance.util.TimeConverUtil;
import com.hmarka.kloop1996.transferelegance.viewmodel.MainViewModel;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


import java.io.IOException;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMyLocationButtonClickListener, RoutingListener {

    public static TimeEntity appointmentTime;
    public static TimeEntity countTime;
    private static MainActivity instance;
    TransferEleganceApplication transferEleganceApplication;
    private MainViewModel mainViewModel;
    private ActivityMainBinding activityMainBinding;
    private int currentPrice;
    private CompositeSubscription mSubscriptions;
    private boolean startMap = false;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;

    public void setStateFrom(boolean stateFrom) {
        this.stateFrom = stateFrom;
    }

    private boolean stateFrom;
    private LatLng from;
    private LatLng to;
    private Marker markerFrom;
    private Marker markerTo;
    private int currentDistanse;
    private int currentDuration;
    private Polyline polyline;
    private Drawer drawer;
    private Toolbar toolbar;
    private Subscription subscription;
    private Subscription statusSubsription;
    private CustomPlaceAutoCompleteFragment toAutocomplete;
    private CustomPlaceAutoCompleteFragment fromAutocomplete;

    private Subscription subscriptionReverseGeocode;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        stateFrom =false;
        instance = this;
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);

        activityMainBinding.setViewModel(mainViewModel);

        toolbar = activityMainBinding.toolbar;

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.second_name);

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.drawer_header, null);


        TextView textView = (TextView) v.findViewById(R.id.nurse_name);
        textView.setText(TransferEleganceApplication.get(this).getUser().getName());


        drawer = new DrawerBuilder(this)
                .withSliderBackgroundColor(getResources().getColor(R.color.settings_background))

                .withScrollToTopAfterClick(true)
                .withRootView(R.id.drawer_container)
                .withToolbar(toolbar)
                .withHeader(v)
                .withCloseOnClick(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDisplayBelowStatusBar(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.settings).withIcon(R.drawable.ic_settings_white).withTag(Constants.SETTINGS_FRAGMENT).withTextColorRes(R.color.white).withSelectedColorRes(R.color.black),
                        new PrimaryDrawerItem().withName(R.string.history).withIcon(R.drawable.ic_history).withTag(Constants.HISTORY_FRAGMENT).withTextColorRes(R.color.white).withSelectedColorRes(R.color.black)
                )
                .withSavedInstance(savedInstanceState)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        changeActivity((String) drawerItem.getTag());
                        return false;
                    }
                })
                .withHeaderPadding(false)
                .withSelectedItemByPosition(4)
                .build();


        transferEleganceApplication = TransferEleganceApplication.get(this);
        TransferEleganceService transferEleganceService = transferEleganceApplication.getTransferEleganceService();

        ResponseDriverStatus responseDriverStatus = null;
        mainViewModel.stateDriver.set(TransferEleganceApplication.get(this).isDriverStatus());



        if (!mainViewModel.stateDriver.get()) {
            mainViewModel.stateOrder.set(true);


        }

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


                            break;

                    }
                }
            });





        initAutocompleteView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMapLongClickListener(this);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerDragListener(this);

        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);


        mGoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                if (!stateFrom){

                    if (markerFrom != null) {
                        markerFrom.remove();

                    }

                    MarkerOptions markerOptions = new MarkerOptions();

                    if (subscriptionReverseGeocode!=null){
                        subscriptionReverseGeocode.unsubscribe();
                    }

                    subscriptionReverseGeocode = ReverseGeocodeService.getAddressAsync(MainActivity.this,location.getLatitude(), location.getLongitude())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(transferEleganceApplication.defaultSubscribeScheduler())
                            .subscribe(new Subscriber<String>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(String s) {
                                    fromAutocomplete.setText(s);
                                    markerFrom.setSnippet(s);
                                    markerFrom.showInfoWindow();
                                }
                            });
                    from = new LatLng(location.getLatitude(), location.getLongitude());

                    markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
                    markerOptions.title(getResources().getString(R.string.from));
                    markerOptions.draggable(true);
                    markerFrom = mGoogleMap.addMarker(markerOptions);
                    markerFrom.showInfoWindow();

                    if (!startMap) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        moveMapCamera(latLng);
                        startMap = true;
                    }

                }
            }
        });

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }


    private void initAutocompleteView() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment_from);
        fromAutocomplete = (CustomPlaceAutoCompleteFragment) autocompleteFragment;
        fromAutocomplete.setTag("from");
//        if (!mainViewModel.stateDriver.get()) {
//            fromAutocomplete.setEnable(false);
//        }

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                if (markerFrom != null) {
                    markerFrom.remove();

                }

                stateFrom = true;

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(place.getLatLng());
                markerOptions.snippet(place.getAddress().toString());
                markerOptions.title(getResources().getString(R.string.from));
                markerOptions.draggable(true);
                from = place.getLatLng();

                markerFrom = mGoogleMap.addMarker(markerOptions);
                markerFrom.showInfoWindow();

                moveMapCamera(place.getLatLng());

                notifySelectPlace();
            }

            @Override
            public void onError(Status status) {

            }
        });

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment_to);
        toAutocomplete = (CustomPlaceAutoCompleteFragment) autocompleteFragment;
//        if (!mainViewModel.stateDriver.get())
//            toAutocomplete.setEnable(false);

        toAutocomplete.setTag("to");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(place.getLatLng());

                markerOptions.title(getResources().getString(R.string.to));
                markerOptions.snippet(place.getAddress().toString());
                to = place.getLatLng();

                if (transferEleganceApplication.getFavourite().indexOf(place) == -1) {
                    transferEleganceApplication.getFavourite().add(new SavePlace(place.getName().toString(), place.getLatLng()));
                    transferEleganceApplication.updateFavouritePlace();

                }

                stateFrom = true;

                markerTo = mGoogleMap.addMarker(markerOptions);
                markerTo.showInfoWindow();

                moveMapCamera(place.getLatLng());

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
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        stateFrom = true;
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        from = marker.getPosition();
        Location location = new Location("from");
        location.setLatitude(from.latitude);

        location.setLongitude(from.longitude);
        marker.setSnippet("");
    }

    public void notifySelectPlace() {
        if (from != null && to != null) {
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .waypoints(to, from)
                    .key(getResources().getString(R.string.google_direction_key))
                    .build();
            routing.execute();

            DialogFragment fragmentDialog = new EndTimePickerDialog();
            fragmentDialog.show(getFragmentManager(), "");
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
        for (LatLng latLng : route.get(i).getPoints()) {
            latLngBuilder.include(latLng);
        }

        if (polyline != null)
            polyline.remove();


        polyline = mGoogleMap.addPolyline(polyOptions);

        polylines.add(polyline);
        route.get(i);

        mainViewModel.setDistance(route.get(0).getDistanceValue());
        currentDistanse = route.get(0).getDistanceValue();
        currentDuration = route.get(0).getDurationValue();
        mainViewModel.setTime(TimeConverUtil.getTimeEntity(route.get(0).getDurationValue()));

        moveMapCamera(latLngBuilder.build());


    }

    @Override
    public void onRoutingCancelled() {

    }

    private void changeActivity(String tag) {
        switch (tag) {
            case Constants.SETTINGS_FRAGMENT:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case Constants.HISTORY_FRAGMENT:
                startActivity(new Intent(this, HistoryActivity.class));
                break;

        }
    }

    public void notifySetTime() {

        if (appointmentTime != null)
            mainViewModel.setAppointmentTime(appointmentTime);

        double divider = Constants.PRICE_HOUR_WAYTIME / 3600.0;
        double divider2 = Constants.PRICE_HOUR_DOWNTIME / 60.0;
        // mainViewModel.setPrice((int)(currentDuration*divider)+PriceUtil.getPriceDownTime(appointmentTime,countTime));
        currentPrice = ((int) (currentDuration * divider)) + (int) ((countTime.getAbsoluteValue() * divider2));

        if (countTime != null) {

            int absoluteTime = appointmentTime.getAbsoluteValue() + countTime.getAbsoluteValue();

            if (absoluteTime >= 1440) {
                absoluteTime -= 1440;
            }

            countTime.setHour(absoluteTime / 60);
            countTime.setMinute(absoluteTime % 60);

            mainViewModel.setWaitUntilTime(countTime);
        }

        mainViewModel.setPrice(currentPrice);

        mainViewModel.stateOrder.set(true);


    }

    public void executeCall(final  View view) {
        final TransferEleganceService transferEleganceService = transferEleganceApplication.getTransferEleganceService();

        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        String token = transferEleganceApplication.getUserToken();
        subscription = transferEleganceService.createOrder(from.latitude + "," + from.longitude, to.latitude + "," + to.longitude, appointmentTime.getHour() + ":" + appointmentTime.getMinute(),
                countTime.getHour() + ":" + countTime.getMinute(), transferEleganceApplication.getUserToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(transferEleganceApplication.defaultSubscribeScheduler())
                .subscribe(new Subscriber<ResponseCreateOrder>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar snackbar = Snackbar
                                .make(view, "Error", Snackbar.LENGTH_LONG);

                    }

                    @Override
                    public void onNext(final ResponseCreateOrder responseCreateOrder) {

                        Snackbar snackbar = Snackbar
                                .make(view, "Success", Snackbar.LENGTH_LONG);

                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.YELLOW);
                        snackbar.show();

                        mainViewModel.autocopleteVisible.set(View.INVISIBLE);
                        mainViewModel.timerVisible.set(View.VISIBLE);
                        mainViewModel.stateOrder.set(false);
                        final CountDownTimer countDownTimer = new CountDownTimer(180000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                mainViewModel.updateTime();
                            }

                            public void onFinish() {
                                mainViewModel.autocopleteVisible.set(View.VISIBLE);
                                mainViewModel.timerVisible.set(View.INVISIBLE);
                                mainViewModel.stateOrder.set(true);



                                if (statusSubsription!=null)
                                    statusSubsription.unsubscribe();
                            }
                        }.start();


                        responseCreateOrder.getOrderId();
                        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
                        String date = df.format(Calendar.getInstance().getTime());
                        statusSubsription = rx.Observable.interval(10,TimeUnit.SECONDS)
                                .flatMap(new Func1<Long, Observable<ResponseStatusOrder>>() {
                                    @Override
                                    public Observable<ResponseStatusOrder> call(Long aLong) {
                                        return transferEleganceService.getStatusOrderById(String.valueOf(responseCreateOrder.getOrderId()),transferEleganceApplication.getUserToken());
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(transferEleganceApplication.defaultSubscribeScheduler())
                                .subscribe(new Subscriber<ResponseStatusOrder>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(ResponseStatusOrder responseStatusOrder) {
                                        if (responseStatusOrder.getStatus()!=null){
                                            countDownTimer.onFinish();
                                            countDownTimer.cancel();

                                            mainViewModel.autocopleteVisible.set(View.VISIBLE);
                                            mainViewModel.timerVisible.set(View.INVISIBLE);
                                            mainViewModel.stateOrder.set(true);

                                            AlertDialog.Builder builder;
                                            builder = new AlertDialog.Builder(
                                                    MainActivity.this);
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    MainActivity.this.finish();
                                                }
                                            });

                                            AlertDialog alertDialog = builder.create();
                                            // Setting Dialog Title
                                            alertDialog.setTitle("Congrats!");

                                            // Setting Dialog Message
                                            String time = responseStatusOrder.getTime();
                                            time = time.substring(0,time.length()-3);
                                            alertDialog.setMessage("Driver will pick you up in  "+time);


                                            alertDialog.show();
                                            //
                                        }
                                    }
                                });


                        transferEleganceApplication.getHistories().add(new HistoryEntity(fromAutocomplete.getTextValue(), toAutocomplete.getTextValue(), date, currentPrice));
                        transferEleganceApplication.updateHistory();
                    }
                });


    }

    @Override
    public boolean onMyLocationButtonClick() {
        stateFrom = false;
        return false;
    }

    public void notifyFromFill(String value) {
        int index = 0;
        SavePlace place = new SavePlace();
        for (SavePlace savePlace : transferEleganceApplication.getFavourite()) {
            if (savePlace.getName().equals(value)) {
                place = savePlace;
                break;
            }
            index++;
        }

        if (place != null) {
            from = place.getLatLng();
            stateFrom = true;
            if (markerFrom != null) {
                markerFrom.remove();

            }

            MarkerOptions markerOptions = new MarkerOptions();
            from = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);

            markerOptions.position(from);
            markerOptions.title(getResources().getString(R.string.from));
            markerOptions.draggable(true);
            markerFrom = mGoogleMap.addMarker(markerOptions);
            markerFrom.showInfoWindow();
        }

    }

    public void notifyToFill(String tag) {

        int index = 0;
        SavePlace place = new SavePlace();
        for (SavePlace savePlace : TransferEleganceApplication.get(this).getFavourite()) {
            if (savePlace.getName().equals(tag)) {
                place = savePlace;
                break;
            }
            index++;
        }

        if (place != null) {
            to = place.getLatLng();
            stateFrom = true;
            if (markerTo != null) {
                markerTo.remove();

            }

            MarkerOptions markerOptions = new MarkerOptions();
            to = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);

            markerOptions.position(to);
            markerOptions.title(getResources().getString(R.string.to));
            markerOptions.draggable(true);
            markerTo = mGoogleMap.addMarker(markerOptions);
            markerTo.showInfoWindow();

            //переместить маркер
        }


    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    private void moveMapCamera(LatLngBounds latLngBounds) {
        int size = getResources().getDisplayMetrics().widthPixels;

        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);

        mGoogleMap.moveCamera(track);

    }

    private void moveMapCamera(LatLng latLng) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private void calculateDistance() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if  (TransferEleganceApplication.get(this).isDriverStatus())
            inflater.inflate(R.menu.menu_online, menu);
        else{
            inflater.inflate(R.menu.menu_offline, menu);
        }
        return true;
    }

    public void updateTitle(){
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.drawer_header, null);


        TextView textView = (TextView) v.findViewById(R.id.nurse_name);
        textView.setText(TransferEleganceApplication.get(this).getUser().getName());

        drawer.setHeader(textView);

    }


}



