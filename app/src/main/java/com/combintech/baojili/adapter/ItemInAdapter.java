package com.combintech.baojili.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
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
import com.bumptech.glide.Glide;
import com.combintech.baojili.R;
import com.combintech.baojili.activity.ListAnggota;
import com.combintech.baojili.activity.ListItemIn;
import com.combintech.baojili.activity.UpdateItemIn;
import com.combintech.baojili.app.AppVar;
import com.combintech.baojili.model.ItemIn;

import java.util.List;

/**
 * Created by server02 on 17/08/2017.
 */

public class ItemInAdapter extends RecyclerView.Adapter<ItemInAdapter.RecyclerHolder> {
    private List<ItemIn> itemInList;
    private Context context;

    public ItemInAdapter(Context context, List<ItemIn> itemInList) {
        this.context = context;
        this.itemInList = itemInList;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_in, parent,false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {
        final ItemIn itemIn = itemInList.get(position);
        holder.txtItemInId.setText(itemIn.getItemInId());
        holder.txtUserId.setText("UserID: "+itemIn.getUserId());
        holder.txtInType.setText("In Type: "+itemIn.getInType());
        holder.txtCode.setText("Code: "+itemIn.getCode());
        holder.txtSize.setText("Size: "+itemIn.getSize());
        holder.txtQuantity.setText("Quantity: "+itemIn.getQuantity());
        holder.txtLocationName.setText("Name: "+itemIn.getLocationName());

        holder.txtHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Yakin ingin menghapus data ini ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {
                                final String itemInId = holder.txtItemInId.getText().toString().trim();
                                StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                                        AppVar.DELETE_ITEM_IN+itemInId,
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
                                                                    new ListItemIn()
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
                bundle.putString("ItemInID", itemInList.get(position).getItemInId());
                bundle.putString("UserID", itemInList.get(position).getUserId());
                bundle.putString("InType", itemInList.get(position).getInType());
                bundle.putString("Code", itemInList.get(position).getCode());
                bundle.putString("Size", itemInList.get(position).getSize());
                bundle.putString("Quantity", itemInList.get(position).getQuantity());
                bundle.putString("LocationName", itemInList.get(position).getLocationName());
                UpdateItemIn nextFragment = new UpdateItemIn();
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemInList.size();
    }


    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView txtItemInId, txtUserId, txtInType, txtCode, txtSize, txtQuantity, txtLocationName, txtHapus;
        CardView cardview;
        public RecyclerHolder(View itemView) {
            super(itemView);
            txtItemInId =(TextView)itemView.findViewById(R.id.txtItemInID);
            txtUserId=(TextView)itemView.findViewById(R.id.userID);
            txtInType =(TextView)itemView.findViewById(R.id.inType);
            txtCode =(TextView)itemView.findViewById(R.id.code);
            txtSize =(TextView)itemView.findViewById(R.id.size);
            txtQuantity =(TextView)itemView.findViewById(R.id.quantity);
            txtLocationName = (TextView)itemView.findViewById(R.id.locationName);
            txtHapus = (TextView)itemView.findViewById(R.id.Hapus);
            cardview = (CardView) itemView.findViewById(R.id.cardList_itemIn);
        }
    }

}
