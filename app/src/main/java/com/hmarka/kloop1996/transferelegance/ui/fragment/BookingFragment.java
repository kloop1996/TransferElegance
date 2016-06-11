package com.hmarka.kloop1996.transferelegance.ui.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.databinding.BookingFragmentBinding;
import com.hmarka.kloop1996.transferelegance.viewmodel.BookingViewModel;

/**
 * Created by kloop1996 on 11.06.2016.
 */
public class BookingFragment extends Fragment {
    private BookingFragmentBinding bookingFragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        bookingFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.booking_fragment,container,false);
//        bookingFragmentBinding.setViewModel(new BookingViewModel(getActivity()));
//
//        return bookingFragmentBinding.getRoot();

        return inflater.inflate(R.layout.booking_fragment,container,false);

    }
}
