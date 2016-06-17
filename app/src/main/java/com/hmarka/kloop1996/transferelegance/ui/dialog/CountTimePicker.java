package com.hmarka.kloop1996.transferelegance.ui.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import com.hmarka.kloop1996.transferelegance.model.TimeEntity;
import com.hmarka.kloop1996.transferelegance.ui.activtity.MainActivity;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class CountTimePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        int hour = MainActivity.appointmentTime.getHour();
        int minute = MainActivity.appointmentTime.getMinute();

        final TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, 0, 0,
                DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.setCancelable(false);
        timePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                timePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
            }
        });
        timePickerDialog.setCanceledOnTouchOutside(false);
        timePickerDialog.setTitle("Count time");
        return timePickerDialog;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        MainActivity.countTime = new TimeEntity(hourOfDay,minute);
        ((MainActivity)MainActivity.getInstance()).notifySetTime();
    }
}
