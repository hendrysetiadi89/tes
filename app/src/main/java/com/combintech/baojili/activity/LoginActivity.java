package com.combintech.baojili.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.combintech.baojili.R;
import com.combintech.baojili.app.AppVar;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by server02 on 03/08/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    public static EditText hasilScan;
    private Context context;
    private Button buttonLogin;
    private ProgressDialog pDialog;
    SurfaceView cameraView;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo
                .SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        context = LoginActivity.this;
        pDialog = new ProgressDialog(context);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        hasilScan = (EditText) findViewById(R.id.hasilscan);
        buttonLogin = (Button) findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        cameraView = (SurfaceView) findViewById(R.id.surfaceView);
        LoginActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource
                .Builder(getApplicationContext(), barcodeDetector)
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
                    hasilScan.post(new Runnable() {
                        @Override
                        public void run() {
                            hasilScan.setText(barcodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });

         hasilScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loginQrCode();
            }
        });
    }

    private void loginQrCode() {
        final String qrcode = hasilScan.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_QRCODE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
                            hideDialog();
                            gotoMainActivity();
                            SharedPreferences sp = getSharedPreferences("key", 0);
                            SharedPreferences.Editor sedt = sp.edit();
                            sedt.putString("valueCode", hasilScan.getText().toString());
                            sedt.commit();

                        } else {
                            hideDialog();
                            Toast.makeText(context, "Gagal", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(AppVar.KEY_QRCODE, qrcode);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void login() {
        final String email = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        pDialog.setMessage("Login Process...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
                            hideDialog();
                            gotoMainActivity();
                            SharedPreferences sp = getSharedPreferences("key", 0);
                            SharedPreferences.Editor sedt = sp.edit();
                            sedt.putString("valueUserID", editTextUsername.getText().toString());
                            sedt.commit();
                        } else {
                            hideDialog();
                            Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(AppVar.KEY_EMAIL, email);
                params.put(AppVar.KEY_PASSWORD, password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
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
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
        barcodeDetector.release();
    }
}
