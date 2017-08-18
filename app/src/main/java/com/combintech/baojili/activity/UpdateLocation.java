package com.combintech.baojili.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.combintech.baojili.R;
import com.combintech.baojili.app.AppVar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by server02 on 10/08/2017.
 */

public class UpdateLocation extends Fragment implements GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemSelectedListener {
    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    private TextView txtLocationID;
    private EditText editTextNameLocation;
    private EditText editTextAddress;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private Button buttonLocation;
    private Button buttonUpdateLocation;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.update_location, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle("Update Lokasi");
        String locationid = this.getArguments().getString("LocationID");
        String nameLocation = this.getArguments().getString("Name");
        String typeLocation = this.getArguments().getString("Type");
        String address = this.getArguments().getString("Address");
        String latitude = this.getArguments().getString("Latitude");
        String longitude = this.getArguments().getString("Longitude");

        buttonLocation = (Button) rootView.findViewById(R.id.btnGetLocation);
        buttonUpdateLocation = (Button) rootView.findViewById(R.id.btnUpdateLocation);
        txtLocationID = (TextView) rootView.findViewById(R.id.txtLocationID);
        txtLocationID.setText(locationid);
        editTextNameLocation = (EditText) rootView.findViewById(R.id.nameLocation);
        editTextNameLocation.setText(nameLocation);
        editTextAddress = (EditText) rootView.findViewById(R.id.address);
        editTextAddress.setText(address);
        editTextLatitude = (EditText) rootView.findViewById(R.id.latitude);
        editTextLatitude.setText(latitude);
        editTextLatitude.setEnabled(false);
        editTextLongitude = (EditText) rootView.findViewById(R.id.longitude);
        editTextLongitude.setText(longitude);
        editTextLongitude.setEnabled(false);
        spinner = (Spinner) rootView.findViewById(R.id.sp_type_location);
        spinner.setPrompt(typeLocation);

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage((FragmentActivity) getActivity(), this)
                .build();

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonUpdateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocation();
            }
        });

        addValuesType();
        checkFieldsForEmptyValues();

        editTextAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextNameLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return rootView;
    }

    private void updateLocation() {
        final String locationID = txtLocationID.getText().toString().trim();
        final String nameLocation = editTextNameLocation.getText().toString().trim();
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.sp_type_location);
        final String typeLocation = spinner.getSelectedItem().toString();
        final String address = editTextAddress.getText().toString().trim();
        final String latitude = editTextLatitude.getText().toString().trim();
        final String longitude = editTextLongitude.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.UPDATE_LOCATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        editTextNameLocation.setText("");
                        editTextAddress.setText("");
                        editTextLatitude.setText("");
                        editTextLongitude.setText("");
                        backToPreviousFragment();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(AppVar.KEY_LOCATIONID, locationID);
                params.put(AppVar.KEY_NAME_LOCATION, nameLocation);
                params.put(AppVar.KEY_TYPE_LOCATION, typeLocation);
                params.put(AppVar.KEY_ADDRESS, address);
                params.put(AppVar.KEY_LATITUDE, latitude);
                params.put(AppVar.KEY_LONGITUDE, longitude);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void addValuesType() {
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("Select Type");
        List<String> type = new ArrayList<String>();
        type.add("Pabrik");
        type.add("Gudang");
        type.add("Toko");
        type.add("Pemasok");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void checkFieldsForEmptyValues() {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(buttonLocation, connectionResult.getErrorMessage() + "", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                StringBuilder stBuilderLatitude = new StringBuilder();
                StringBuilder stBuilderLongitude = new StringBuilder();
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                stBuilderLatitude.append(latitude);
                stBuilderLongitude.append(longitude);
                editTextLatitude.setText(stBuilderLatitude.toString());
                editTextLongitude.setText(stBuilderLongitude.toString());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame,
                            new ListLocation()).commit();
                    return true;
                }
                return false;
            }
        });
    }

    private void backToPreviousFragment() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame,
                            new ListLocation()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
