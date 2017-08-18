package com.combintech.baojili.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.combintech.baojili.R;
import com.combintech.baojili.activity.ListAnggota;
import com.combintech.baojili.activity.ListLocation;
import com.combintech.baojili.activity.UpdateLocation;
import com.combintech.baojili.app.AppVar;
import com.combintech.baojili.model.Location;

import java.util.List;

/**
 * Created by server02 on 10/08/2017.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.RecyclerHolder> {

    private List<Location> locationList;
    private Context context;

    public LocationAdapter(Context context, List<Location> locationList) {
        this.locationList = locationList;
        this.context = context;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_location, parent,false);
        LocationAdapter.RecyclerHolder recyclerHolder = new LocationAdapter.RecyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {
        final Location location = locationList.get(position);
        holder.txtLocationID.setText(location.getLocationid());
        holder.txtLatitude.setText(location.getLatitude());
        holder.txtLongitude.setText(location.getLongitude());
        holder.txtTypeLocation.setText("Type: "+location.getTypelocation());
        holder.txtNameLocation.setText("Name: "+location.getNamelocation());
        holder.txtAddress.setText("Address : "+location.getAddress());

        holder.txtHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Yakin ingin menghapus data ini ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {
                                final String locationid = holder.txtLocationID.getText().toString().trim();
                                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, AppVar.DELETE_LOCATION+locationid,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast.makeText(v.getContext(),response,Toast.LENGTH_LONG).show();
                                                Thread thread = new Thread(){
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            Thread.sleep(3500);
                                                            FragmentManager fragmentManager = ((AppCompatActivity)
                                                                    context).getSupportFragmentManager();
                                                            fragmentManager.beginTransaction().replace(R.id.content_frame,
                                                                    new ListLocation()
                                                            ).commit();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };
                                                thread.start();
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(v.getContext(),error.toString(),Toast.LENGTH_LONG).show();
                                            }
                                        }){
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                                requestQueue.add(stringRequest);
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("LocationID", locationList.get(position).getLocationid());
                bundle.putString("Latitude", locationList.get(position).getLatitude());
                bundle.putString("Longitude", locationList.get(position).getLongitude());
                bundle.putString("Type", locationList.get(position).getTypelocation());
                bundle.putString("Name", locationList.get(position).getNamelocation());
                bundle.putString("Address", locationList.get(position).getAddress());
                UpdateLocation nextFragment = new UpdateLocation();
                nextFragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView txtLocationID, txtNameLocation, txtTypeLocation,txtAddress,txtLatitude,txtLongitude, txtHapus;
        CardView cardview;
        public RecyclerHolder(View itemView) {
            super(itemView);
            txtLocationID =(TextView) itemView.findViewById(R.id.txtLocationID);
            txtNameLocation =(TextView) itemView.findViewById(R.id.txtNameLocation);
            txtTypeLocation =(TextView) itemView.findViewById(R.id.txtTypeLocation);
            txtAddress =(TextView) itemView.findViewById(R.id.txtAddress);
            txtLatitude =(TextView) itemView.findViewById(R.id.txtLatitude);
            txtLongitude =(TextView) itemView.findViewById(R.id.txtLongitude);
            txtHapus = (TextView)itemView.findViewById(R.id.Hapus);
            cardview = (CardView) itemView.findViewById(R.id.cardList_location);
        }
    }
}
