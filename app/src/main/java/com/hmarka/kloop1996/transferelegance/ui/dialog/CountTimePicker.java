package com.hmarka.kloop1996.transferelegance.ui.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.hmarka.kloop1996.transferelegance.model.TimeEntity;
import com.hmarka.kloop1996.transferelegance.ui.activtity.MainActivity;
import com.hmarka.kloop1996.transferelegance.ui.fragment.BookingFragment;

import java.util.Calendar;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class CountTimePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        int hour = MainActivity.appointmentTime.getHour();
        int minute = MainActivity.appointmentTime.getHour();

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

        timePickerDialog.setTitle("Wait until");
        return timePickerDialog;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        MainActivity.countTime = new TimeEntity(hourOfDay,minute);

    }
}
