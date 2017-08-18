package com.combintech.baojili.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by server02 on 08/08/2017.
 */

public class UpdateItem extends Fragment {
    Bitmap bitmap;
    private static final int CAMERA_PIC_REQUEST = 22;
    private TextView textItemID;
    private EditText editTextDesc;
    private EditText editTextCode;
    private EditText editTextSize;
    private EditText editTextPrice;
    private EditText editTextCost;
    private Button buttonUpdateItem;
    private Button buttonAddPhoto;
    private String type = "";
    private ImageView itemPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.update_item, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle("Update Barang");
        String itemid = this.getArguments().getString("ItemID");
        String desc = this.getArguments().getString("Description");
        String code = this.getArguments().getString("Code");
        String size = this.getArguments().getString("Size");
        String price = this.getArguments().getString("Price");
        String cost = this.getArguments().getString("Cost");
        String photo = this.getArguments().getString("Photo");
        textItemID = (TextView) rootView.findViewById(R.id.txtItemID);
        textItemID.setText(itemid);
        editTextDesc = (EditText) rootView.findViewById(R.id.deskripsi);
        editTextDesc.setText(desc);
        editTextCode = (EditText) rootView.findViewById(R.id.kode);
        editTextCode.setText(code);
        editTextSize = (EditText) rootView.findViewById(R.id.size);
        editTextSize.setText(size);
        editTextPrice = (EditText) rootView.findViewById(R.id.price);
        editTextPrice.setText(price);
        editTextCost = (EditText) rootView.findViewById(R.id.cost);
        editTextCost.setText(cost);
        itemPhoto = (ImageView) rootView.findViewById(R.id.imagePhoto);
        Glide
                .with(this)
                .load(photo)
                .into(itemPhoto);

        itemPhoto = (ImageView) rootView.findViewById(R.id.imagePhoto);
        buttonUpdateItem = (Button) rootView.findViewById(R.id.btnUpdateItem);
        buttonAddPhoto = (Button) rootView.findViewById(R.id.btnAddPhoto);
        buttonUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem();
            }
        });

        buttonAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Couldn't load photo", Toast.LENGTH_LONG).show();
                }
            }
        });
        checkFieldsForEmptyValues();
        editTextDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextCode.addTextChangedListener(new TextWatcher() {
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

        editTextSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPrice.addTextChangedListener(new TextWatcher() {
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

        editTextCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return rootView;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void updateItem() {
        final String itemid = textItemID.getText().toString().trim();
        final String description = editTextDesc.getText().toString().trim();
        final String code = editTextCode.getText().toString().trim();
        final String size = editTextSize.getText().toString().trim();
        final String price = editTextPrice.getText().toString().trim();
        final String cost = editTextCost.getText().toString().trim();
        final RadioGroup radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroupTipe);
        final RadioButton produksi = (RadioButton) getActivity().findViewById(R.id.radioProduksi);
        final RadioButton pasokan = (RadioButton) getActivity().findViewById(R.id.radioPasokan);

        if (radioGroup.getCheckedRadioButtonId() == produksi.getId()) {
            type = "Produksi";
        } else if (radioGroup.getCheckedRadioButtonId() == pasokan.getId()) {
            type = "Pasokan";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.UPDATE_ITEM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        editTextDesc.setText("");
                        editTextCode.setText("");
                        editTextSize.setText("");
                        editTextPrice.setText("");
                        editTextCost.setText("");
                        itemPhoto.setImageResource(0);
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
                params.put(AppVar.KEY_ITEMID, itemid);
                params.put(AppVar.KEY_DESCRIPTION, description);
                params.put(AppVar.KEY_TYPE, type);
                params.put(AppVar.KEY_CODE, code);
                params.put(AppVar.KEY_SIZE, size);
                params.put(AppVar.KEY_PRICE, price);
                params.put(AppVar.KEY_COST, cost);
                params.put(AppVar.KEY_PHOTO, getStringImage(bitmap));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case CAMERA_PIC_REQUEST:
                    if (resultCode == getActivity().RESULT_OK) {
                        try {
                            bitmap = (Bitmap) data.getExtras().get("data");
                            itemPhoto.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Couldn't load photo", Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }


    private  void checkFieldsForEmptyValues(){
        String code = editTextCode.getText().toString();
        String size = editTextSize.getText().toString();
        String price = editTextPrice.getText().toString();

        if (code.length() > 0 && size.length() > 0 && price.length() > 0) {
            buttonUpdateItem.setEnabled(true);
        } else {
            buttonUpdateItem.setEnabled(false);
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
                            new ListItem()).commit();
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
                            new ListItem()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
