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
import com.combintech.baojili.activity.ListItemOut;
import com.combintech.baojili.activity.UpdateItemOut;
import com.combintech.baojili.app.AppVar;
import com.combintech.baojili.model.ItemOut;

import java.util.List;

/**
 * Created by server02 on 17/08/2017.
 */

public class ItemOutAdapter extends RecyclerView.Adapter<ItemOutAdapter.RecyclerHolder> {
    private List<ItemOut> itemOutList;
    private Context context;

    public ItemOutAdapter(Context context, List<ItemOut> itemOutList) {
        this.context = context;
        this.itemOutList = itemOutList;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_out, parent,false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {
        final ItemOut itemOut = itemOutList.get(position);
        holder.txtItemOutId.setText(itemOut.getItemOutId());
        holder.txtUserId.setText("UserID: "+itemOut.getUserId());
        holder.txtOutType.setText("Out Type: "+itemOut.getOutType());
        holder.txtCode.setText("Code: "+itemOut.getCode());
        holder.txtSize.setText("Size: "+itemOut.getSize());
        holder.txtQuantity.setText("Quantity: "+itemOut.getQuantity());
        holder.txtLocationName.setText("Name: "+itemOut.getLocationName());

        holder.txtHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Yakin ingin menghapus data ini ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {
                                final String itemOutId = holder.txtItemOutId.getText().toString().trim();
                                StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                                        AppVar.DELETE_ITEM_OUT+itemOutId,
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
                                                                    new ListItemOut()
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
                bundle.putString("ItemOutID", itemOutList.get(position).getItemOutId());
                bundle.putString("UserID", itemOutList.get(position).getUserId());
                bundle.putString("OutType", itemOutList.get(position).getOutType());
                bundle.putString("Code", itemOutList.get(position).getCode());
                bundle.putString("Size", itemOutList.get(position).getSize());
                bundle.putString("Quantity", itemOutList.get(position).getQuantity());
                bundle.putString("LocationName", itemOutList.get(position).getLocationName());
                UpdateItemOut nextFragment = new UpdateItemOut();
                nextFragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemOutList.size();
    }


    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView txtItemOutId, txtUserId, txtOutType, txtCode, txtSize, txtQuantity, txtLocationName, txtHapus;
        CardView cardview;
        public RecyclerHolder(View itemView) {
            super(itemView);
            txtItemOutId =(TextView)itemView.findViewById(R.id.txtItemOutID);
            txtUserId=(TextView)itemView.findViewById(R.id.userID);
            txtOutType =(TextView)itemView.findViewById(R.id.outType);
            txtCode =(TextView)itemView.findViewById(R.id.code);
            txtSize =(TextView)itemView.findViewById(R.id.size);
            txtQuantity =(TextView)itemView.findViewById(R.id.quantity);
            txtLocationName = (TextView)itemView.findViewById(R.id.locationName);
            txtHapus = (TextView)itemView.findViewById(R.id.Hapus);
            cardview = (CardView) itemView.findViewById(R.id.cardList_itemOut);
        }
    }

}
