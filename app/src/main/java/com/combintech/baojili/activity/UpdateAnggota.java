package com.combintech.baojili.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.combintech.baojili.R;
import com.combintech.baojili.app.AppVar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by server02 on 07/08/2017.
 */

public class UpdateAnggota extends Fragment {
    private EditText editTextUsername;
    private EditText editTextName;
    private EditText editTextEmail;
    private Context context;
    private RadioButton radioButtonKaryawan, radioButtonManajer;
    private EditText editTextPassword;
    private EditText editTextRePassword;
    private Button buttonAddAnggota;
    private Button buttonAddPhotoUser;
    Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView userPhoto;
    private String role = "";

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.update_anggota, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle("Update Pengguna");
        String username= this.getArguments().getString("UserID");
        String name = this.getArguments().getString("Name");
        String email = this.getArguments().getString("Email");
        String role = this.getArguments().getString("Role");
        String password = this.getArguments().getString("Password");
        String photo = this.getArguments().getString("Photo");
        editTextUsername = (EditText) rootView.findViewById(R.id.username);
        editTextUsername.setText(username);
        editTextName = (EditText) rootView.findViewById(R.id.name);
        editTextName.setText(name);
        editTextEmail = (EditText) rootView.findViewById(R.id.email);
        editTextEmail.setText(email);
        editTextPassword = (EditText) rootView.findViewById(R.id.password);
        editTextPassword.setText(password);
        editTextRePassword =(EditText) rootView.findViewById(R.id.rePassword);
        userPhoto = (ImageView) rootView.findViewById(R.id.imageViewUser);
        Glide
                .with(this)
                .load(photo)
                .into(userPhoto);
        radioButtonKaryawan = (RadioButton)rootView.findViewById(R.id.radioKaryawan);
        radioButtonManajer = (RadioButton)rootView.findViewById(R.id.radioManajer);
        buttonAddAnggota = (Button) rootView.findViewById(R.id.btnAdd);
        buttonAddPhotoUser = (Button) rootView.findViewById(R.id.btnAddPhotoUser);
        buttonAddAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    updateAnggota();
                }
            }
        });

        buttonAddPhotoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        checkFieldsForEmptyValues();

        editTextUsername.addTextChangedListener(new TextWatcher() {
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

        editTextName.addTextChangedListener(new TextWatcher() {
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

        editTextPassword.addTextChangedListener(new TextWatcher() {
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

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void updateAnggota() {
        final String username = editTextUsername.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.rg);
        final RadioButton manajer = (RadioButton) getActivity().findViewById(R.id.radioManajer);
        final RadioButton karyawan = (RadioButton) getActivity().findViewById(R.id.radioKaryawan);

        if (radioGroup.getCheckedRadioButtonId() == manajer.getId())
        {
            role = "Manajer";
        }
        else if (radioGroup.getCheckedRadioButtonId() == karyawan.getId())
        {
            role = "Karyawan";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.UPDATE_ANGGOTA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                        editTextUsername.setText("");
                        editTextName.setText("");
                        editTextEmail.setText("");
                        editTextPassword.setText("");
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
                Map<String,String> params = new HashMap<String, String>();
                params.put(AppVar.KEY_USERNAME, username);
                params.put(AppVar.KEY_NAME, name);
                params.put(AppVar.KEY_EMAIL, email);
                params.put(AppVar.KEY_PASSWORD, password);
                params.put(AppVar.KEY_ROLE, role);
                params.put(AppVar.KEY_PHOTO_USER, getStringImage(bitmap));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private  void checkFieldsForEmptyValues(){
        String username = editTextUsername.getText().toString();
        String name = editTextName.getText().toString();
        String password = editTextPassword.getText().toString();

        if (username.length() > 0 && name.length() > 0 && password.length() > 0) {
            buttonAddAnggota.setEnabled(true);
        } else {
            buttonAddAnggota.setEnabled(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                userPhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
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
                            new ListAnggota()).commit();
                    return true;
                }
                return false;
            }
        });
    }

    private boolean validate() {
        boolean temp=true;
        String checkemail = editTextEmail.getText().toString();
        String password=editTextPassword.getText().toString();
        String repassword=editTextRePassword.getText().toString();
        if(!EMAIL_ADDRESS_PATTERN.matcher(checkemail).matches()){
            Toast.makeText(getActivity(),"Alamat email tidak valid",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        else if(!password.equals(repassword)){
            Toast.makeText(getActivity(),"Password tidak sesuai",Toast.LENGTH_SHORT).show();
            temp=false;
        }
        return temp;
    }

    private void backToPreviousFragment() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame,
                            new ListAnggota()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
