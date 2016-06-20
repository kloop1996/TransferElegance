package com.hmarka.kloop1996.transferelegance.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.ui.activtity.MainActivity;

/**
 * Created by kloop1996 on 20.06.2016.
 */
public class DialogFactory {

    public static Dialog createCongratsDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle("Congrats!")
                .setMessage("Alex will pick you up in "+message)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.getInstance().finish();
                    }
                });
        return alertDialog.create();
    }
}
