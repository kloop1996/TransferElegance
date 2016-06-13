package com.hmarka.kloop1996.transferelegance.ui.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.databinding.SettingsFragmentBinding;
import com.hmarka.kloop1996.transferelegance.viewmodel.HistoryViewModel;
import com.hmarka.kloop1996.transferelegance.viewmodel.SettingsViewModel;

/**
 * Created by kloop1996 on 11.06.2016.
 */
public class SettingsFragment extends Fragment {

    private SettingsFragmentBinding settingsFragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment,container,false);

        settingsFragmentBinding.setViewModel(new SettingsViewModel(getActivity()));

        return settingsFragmentBinding.getRoot();

    }
}
