package com.hmarka.kloop1996.transferelegance.ui.adapter;

import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hmarka.kloop1996.transferelegance.R;

/**
 * Created by kloop1996 on 11.06.2016.
 */
public class HistoryViewBinder implements SimpleAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {

        switch (view.getId()){
            case R.id.from_text:
                ((TextView)view).setText((String)data);
                return true;
            case R.id.to_text:
                ((TextView)view).setText((String)data);
                return true;
            case R.id.price_text:
                ((TextView)view).setText((String)data);
                return true;
            case R.id.date_text:
                ((TextView)view).setText((String)data);
                return true;

        }
        return false;
    }
}
