package com.hmarka.kloop1996.transferelegance.ui.activtity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hmarka.kloop1996.transferelegance.R;
import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;
import com.hmarka.kloop1996.transferelegance.model.HistoryEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class HistoryActivity extends AppCompatActivity {

    List<HistoryEntity> historyEntities;
    ImageView mImageView;
    ListView lvSimple;
    String [] data;

    final String ATTRIBUTE_NAME_FROM_TEXT = "from";
    final String ATTRIBUTE_NAME_TO_TEXT = "to";
    final String ATTRIBUTE_NAME_PRICE = "price";
    final String ATTRIBUTE_NAME_DATE = "date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_history);

                //@drawable/abc_ic_ab_back_mtrl_am_alpha

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity.this.onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        historyEntities = TransferEleganceApplication.get(this).getHistories();
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                historyEntities.size());
        Map<String, Object> m;
        int index = 1;
        for (HistoryEntity historyEntity:historyEntities) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_FROM_TEXT, historyEntity.getFromName());
            m.put(ATTRIBUTE_NAME_TO_TEXT, historyEntity.getToName());
            m.put(ATTRIBUTE_NAME_PRICE, historyEntity.getPrice());
            m.put(ATTRIBUTE_NAME_DATE, historyEntity.getDate());
            data.add(m);
            index++;
        }


        String[] from = { ATTRIBUTE_NAME_FROM_TEXT, ATTRIBUTE_NAME_TO_TEXT,
                ATTRIBUTE_NAME_PRICE,ATTRIBUTE_NAME_DATE};

        int[] to = { R.id.from_text, R.id.to_text, R.id.price_text,R.id.date_text};

        final SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.history_item,
                from, to);

        //sAdapter.setViewBinder(new());


        lvSimple = (ListView) findViewById(R.id.list_history);
        lvSimple.setAdapter(sAdapter);

        ((Button)findViewById(R.id.clear_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvSimple.setAdapter(null);
                TransferEleganceApplication.get(HistoryActivity.this).setHistories(new ArrayList<HistoryEntity>());
                TransferEleganceApplication.get(HistoryActivity.this).updateHistory();
            }
        });


    }
}


