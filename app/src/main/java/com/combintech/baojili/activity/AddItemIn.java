package com.combintech.baojili.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.combintech.baojili.R;
import com.combintech.baojili.adapter.GetLocationAdapter;
import com.combintech.baojili.app.AppVar;
import com.combintech.baojili.app.NetworkController;
import com.combintech.baojili.model.Location;
import com.combintech.baojili.model.Stock;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by server02 on 14/08/2017.
 */

public class AddItemIn extends Fragment implements AdapterView.OnItemSelectedListener  {
    private static final String TAG = AddItemIn.class.getSimpleName();
    ProgressDialog pDialog;
    Spinner spinner;
    TextView txt_location;
    TextView txt_time;
    TextView txt_userID;
    TextView txt_barcode;
    TextView textStockId;
    TextView textLocationId;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceView cameraView;
    Spinner spinner_location;
    GetLocationAdapter adapter;
    List<Location> listLocation = new ArrayList<Location>();
    List<Stock> listStock = new ArrayList<Stock>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_item_in, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle("Tambah Barang Masuk");
        txt_location = (TextView) rootView.findViewById(R.id.location);
        textLocationId =(TextView) rootView.findViewById(R.id.locationID);
        txt_barcode = (TextView) rootView.findViewById(R.id.textItemID);
        textStockId = (TextView) rootView.findViewById(R.id.stockID);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        txt_time = (TextView) rootView.findViewById(R.id.timeNow);
        txt_time.setEnabled(false);
        txt_time.setText(currentDateandTime);
        spinner = (Spinner) rootView.findViewById(R.id.sp_type_item);
        spinner_location = (Spinner) rootView.findViewById(R.id.sp_location_name);

        txt_userID = (TextView) rootView.findViewById(R.id.userID);
        txt_userID.setEnabled(false);
        addValuesType();
        adapter = new GetLocationAdapter(getActivity(), listLocation);
        spinner_location.setAdapter(adapter);

        cameraView = (SurfaceView) rootView.findViewById(R.id.scanBarcode);
        getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        barcodeDetector = new BarcodeDetector.Builder(getActivity().getApplicationContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource
                .Builder(getActivity().getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(35.0f)
                .setRequestedPreviewSize(960, 960)
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder
                .Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(cameraView
                            .getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie
                            .getMessage());
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txt_barcode.post(new Runnable() {
                        @Override
                        public void run() {
                            txt_barcode.setText(barcodes.valueAt(0).displayValue);
                            final String itemId = txt_barcode.getText().toString().trim();
                            JsonArrayRequest anggotaReq = new
                                    JsonArrayRequest(AppVar.LIST_STOCKID_URL+itemId+"&"+"locationid",
                                    new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            Log.d(TAG, response.toString());
                                            for (int i = 0; i < response.length(); i++) {
                                                try {
                                                    JSONObject obj = response.getJSONObject(i);
                                                    String stockId = obj.getString("StockID");
                                                    textStockId.setText(stockId);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                            adapter.notifyDataSetChanged();
                                            hideDialog();
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                                    hideDialog();
                                }
                            });
                            NetworkController.getInstance().addToRequestQueue(anggotaReq);
                        }
                    });
                }
            }
        });

        txt_barcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        callLocation();
        return rootView;
    }

    private void callLocation() {
        listLocation.clear();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();
        JsonArrayRequest anggotaReq = new JsonArrayRequest(AppVar.LIST_LOCATION_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Location location = new Location();
                                location.setNamelocation(obj.getString("Name"));
                                listLocation.add(location);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter.notifyDataSetChanged();
                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideDialog();
            }
        });
        NetworkController.getInstance().addToRequestQueue(anggotaReq);
    }

    public void addValuesType() {
        spinner.setOnItemSelectedListener(this);
        List<String> type = new ArrayList<String>();
        type.add("Pilih Tipe");
        type.add("Penambahan");
        type.add("Pemindahan");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
                            new StockInfo()).commit();
                    return true;
                }
                return false;
            }
        });
    }
}
