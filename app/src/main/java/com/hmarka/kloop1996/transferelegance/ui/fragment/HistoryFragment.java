package com.hmarka.kloop1996.transferelegance.ui.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;
import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.databinding.HistoryFragmentBinding;
import com.hmarka.kloop1996.transferelegance.viewmodel.HistoryViewModel;

/**
 * Created by kloop1996 on 11.06.2016.
 */
public class HistoryFragment extends Fragment {

    private HistoryFragmentBinding historyFragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        historyFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.history_fragment,container,false);

        historyFragmentBinding.setViewModel(new HistoryViewModel(getActivity()));

        return historyFragmentBinding.getRoot();

    }
}
