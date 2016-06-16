package com.hmarka.kloop1996.transferelegance.ui.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmarka.kloop1996.transferelegance.R;

/**
 * Created by kloop1996 on 13.06.2016.
 */
public class OfflineMessageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.offline_fragment,container,false);

    }
}
