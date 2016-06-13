package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hmarka.kloop1996.transferelegance.Constants;
import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.core.TransferEleganceService;
import com.hmarka.kloop1996.transferelegance.databinding.ActivityMainBinding;
import com.hmarka.kloop1996.transferelegance.model.ResponseToken;
import com.hmarka.kloop1996.transferelegance.ui.fragment.BookingFragment;
import com.hmarka.kloop1996.transferelegance.ui.fragment.HistoryFragment;
import com.hmarka.kloop1996.transferelegance.ui.fragment.SettingsFragment;
import com.hmarka.kloop1996.transferelegance.viewmodel.MainViewModel;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private ActivityMainBinding activityMainBinding;

    private Drawer drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        new PrimaryDrawerItem().withName(R.string.history).withIcon(R.drawable.ic_menu).withTag(Constants.HISTORY_FRAGMENT),
                        new PrimaryDrawerItem().withName(R.string.settings).withIcon(R.drawable.ic_menu).withTag(Constants.SETTINGS_FRAGMENT)

                )
                .withSavedInstance(savedInstanceState)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        changeFragment((String)drawerItem.getTag());
                        return false;
                    }
                })
                .build();



        if (savedInstanceState == null) {

            FragmentTransaction tx = getFragmentManager()
                    .beginTransaction();

            tx.add(R.id.container_layout, new BookingFragment(), Constants.BOOKING_FRAGMENT);

            tx.commit();
        }



    }
    private void changeFragment(String tag){
        FragmentTransaction tx;
        Fragment fragment;

        switch (tag){
            case Constants.BOOKING_FRAGMENT:
                BookingFragment bookingFragment = (BookingFragment) getFragmentManager().findFragmentByTag(Constants.BOOKING_FRAGMENT);

                if (bookingFragment != null && bookingFragment.isVisible()) {
                    break;
                }


                tx = getFragmentManager()
                        .beginTransaction();

                fragment = new BookingFragment();

                tx.replace(R.id.container_layout, fragment, Constants.BOOKING_FRAGMENT);
                tx.addToBackStack(null);

                tx.commit();

                break;
            case Constants.HISTORY_FRAGMENT:

                HistoryFragment historyFragment = (HistoryFragment) getFragmentManager().findFragmentByTag(Constants.HISTORY_FRAGMENT);

                if (historyFragment != null && historyFragment.isVisible()) {
                    break;
                }


                tx = getFragmentManager()
                        .beginTransaction();

                fragment = new HistoryFragment();

                tx.replace(R.id.container_layout, fragment, Constants.HISTORY_FRAGMENT);
                tx.addToBackStack(null);

                tx.commit();

                break;
            case Constants.SETTINGS_FRAGMENT:

                SettingsFragment settingsFragment = (SettingsFragment) getFragmentManager().findFragmentByTag(Constants.SETTINGS_FRAGMENT);

                if (settingsFragment != null && settingsFragment.isVisible()) {
                    break;
                }


                tx = getFragmentManager()
                        .beginTransaction();

                fragment = new SettingsFragment();

                tx.replace(R.id.container_layout, fragment, Constants.SETTINGS_FRAGMENT);
                tx.addToBackStack(null);

                tx.commit();

                break;

        }
    }


}
