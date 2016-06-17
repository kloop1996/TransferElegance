package com.hmarka.kloop1996.transferelegance.ui.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import com.hmarka.kloop1996.transferelegance.model.TimeEntity;
import com.hmarka.kloop1996.transferelegance.ui.activtity.MainActivity;

import java.util.Calendar;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class EndTimePickerDialog extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog =  new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.setCanceledOnTouchOutside(false);
        timePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                timePickerDialog.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
            }
        });
        timePickerDialog.setTitle("Appointment time");
        return timePickerDialog;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        MainActivity.appointmentTime = new TimeEntity(hourOfDay,minute);
        DialogFragment dialogFragment = new CountTimePicker();
        dialogFragment.show(getFragmentManager(),"");
    }
}