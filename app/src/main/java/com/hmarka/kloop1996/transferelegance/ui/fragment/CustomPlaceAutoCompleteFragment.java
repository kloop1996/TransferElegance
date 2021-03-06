package com.hmarka.kloop1996.transferelegance.ui.fragment;


 import android.content.Intent;
 import android.os.Bundle;
 import android.support.annotation.Nullable;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.util.Log;
 import android.view.KeyEvent;
 import android.view.LayoutInflater;
 import android.view.MotionEvent;
 import android.view.View;
 import android.view.ViewGroup;
 import android.view.inputmethod.EditorInfo;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.AutoCompleteTextView;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.TextView;

 import com.google.android.gms.common.GoogleApiAvailability;
 import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
 import com.google.android.gms.common.GooglePlayServicesRepairableException;
 import com.google.android.gms.common.api.Status;
 import com.google.android.gms.location.places.AutocompleteFilter;
 import com.google.android.gms.location.places.Place;
 import com.google.android.gms.location.places.ui.PlaceAutocomplete;
 import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
 import com.google.android.gms.location.places.ui.PlaceSelectionListener;
 import com.google.android.gms.maps.model.LatLngBounds;
 import com.hmarka.kloop1996.transferelegance.R;
 import com.hmarka.kloop1996.transferelegance.TransferEleganceApplication;
 import com.hmarka.kloop1996.transferelegance.model.SavePlace;
 import com.hmarka.kloop1996.transferelegance.ui.activtity.MainActivity;

 import java.util.List;


public class CustomPlaceAutoCompleteFragment extends PlaceAutocompleteFragment {
    private String tag;
    private CharSequence hint;
    private AutoCompleteTextView editSearch;
    private ImageButton searchButton;

     public void setEnable(boolean state){
         editSearch.setEnabled(state);
         if (!state)
             editSearch.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     ;
                 }
             });
     }

    private View zzaRh;
    private View zzaRi;
    private EditText zzaRj;
    @Nullable
    private LatLngBounds zzaRk;
    @Nullable
    private AutocompleteFilter zzaRl;
    @Nullable
    private PlaceSelectionListener zzaRm;

    public String getTextValue(){
        return editSearch.getText().toString();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View var4 = inflater.inflate(R.layout.layout_place_autocomplete, container, false);

        searchButton = (ImageButton) var4.findViewById(R.id.button_search) ;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zzzG();
            }
        });
        editSearch = (AutoCompleteTextView) var4.findViewById(R.id.editWorkLocation);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchButton.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                if (s.length()==1){
                    zzzG();
                }
                MainActivity.getInstance().setStateFrom(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                zzzG();
                return false;
            }
        });

        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.showDropDown();
            }
        });

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String username = editSearch.getText().toString();
                    if (username.length() > 0) zzzG();
                    return true;
                }
                return false;
            }
        });



//        editSearch.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                editSearch.showDropDown();
//                return true;
//            }
//        });

//        editSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    editSearch.showDropDown();
//                }
//            }
//        });
        editSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (tag.equals("from")){
                    MainActivity.getInstance().notifyFromFill(((TextView)view).getText().toString());
                }else{
                    MainActivity.getInstance().notifyToFill(((TextView)view).getText().toString());
                }

                MainActivity.getInstance().notifySelectPlace();
                //position = (int)id;
            }
        });

        List<SavePlace> places = TransferEleganceApplication.get(getActivity()).getFavourite();
        String [] items= new String[places.size()];
        int index = 0;
        for (SavePlace savePlace :places){
            items[index]= savePlace.getName();
            index++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,items);

        editSearch.setAdapter(adapter);
        editSearch.setHint(hint);
        return var4;
    }


    public void onDestroyView() {
        this.zzaRh = null;
        this.zzaRi = null;
        this.editSearch = null;
        super.onDestroyView();
    }

    public void setBoundsBias(@Nullable LatLngBounds bounds) {
        this.zzaRk = bounds;
    }

    public void setFilter(@Nullable AutocompleteFilter filter) {
        this.zzaRl = filter;
    }

    public void setText(CharSequence text) {
        this.editSearch.setText(text);

    }

    public void setHint(CharSequence hint) {
        this.hint = hint;
        if (editSearch!=null)
        this.editSearch.setHint(hint);
        this.zzaRh.setContentDescription(hint);
    }

    public void setOnPlaceSelectedListener(PlaceSelectionListener listener) {
        this.zzaRm = listener;
    }

    private void zzzF() {
        boolean var1 = !this.editSearch.getText().toString().isEmpty();
        //this.zzaRi.setVisibility(var1?0:8);
    }

    private void zzzG() {
        int var1 = -1;

        try {

            Intent var2 = (new PlaceAutocomplete.IntentBuilder(2)).setBoundsBias(this.zzaRk).setFilter(this.zzaRl).zzeR(this.editSearch.getText().toString()).zziH(1).build(this.getActivity());
            this.startActivityForResult(var2, 1);
        } catch (GooglePlayServicesRepairableException var3) {
            var1 = var3.getConnectionStatusCode();
            Log.e("Places", "Could not open autocomplete activity", var3);
        } catch (GooglePlayServicesNotAvailableException var4) {
            var1 = var4.errorCode;
            Log.e("Places", "Could not open autocomplete activity", var4);
        }

        if (var1 != -1) {
            GoogleApiAvailability var5 = GoogleApiAvailability.getInstance();
            var5.showErrorDialogFragment(this.getActivity(), var1, 2);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == -1) {
                Place var4 = PlaceAutocomplete.getPlace(this.getActivity(), data);
                if (this.zzaRm != null) {
                    this.zzaRm.onPlaceSelected(var4);
                }

                this.setText(var4.getName().toString());
            } else if (resultCode == 2) {
                Status var5 = PlaceAutocomplete.getStatus(this.getActivity(), data);
                if (this.zzaRm != null) {
                    this.zzaRm.onError(var5);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setTag(String tag){

        if (tag.equals("from")){
            editSearch.setHint("Your location");

        }else{
            editSearch.setHint("Destination point");
        }

        this.tag=tag;
    }

}
