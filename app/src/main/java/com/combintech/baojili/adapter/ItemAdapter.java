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
import android.widget.ImageView;
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
import com.combintech.baojili.activity.ListItem;
import com.combintech.baojili.activity.UpdateItem;
import com.combintech.baojili.app.AppVar;
import com.combintech.baojili.model.Item;

import java.util.List;

/**
 * Created by server02 on 08/08/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.RecyclerHolder> {
    private List<Item> itemList;
    private Context context;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {
        final Item item = itemList.get(position);
        holder.txtItemID.setText(item.getItemid());
        holder.txtDesc.setText(item.getDesc());
        holder.txtType.setText("Type: "+item.getType());
        holder.txtCode.setText("Code: "+item.getCode());
        holder.txtSize.setText("Size: "+item.getSize());
        holder.txtPrice.setText("Price: Rp. "+item.getPrice());
        holder.txtCost.setText("Cost: Rp. "+item.getCost());
        Glide.with(context).load(item.getPhoto())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.imgPhoto);

        holder.txtHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Yakin ingin menghapus data ini ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {
                                final String itemid = holder.txtItemID.getText().toString().trim();
                                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, AppVar.DELETE_ITEM+itemid,
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
                                                                    new ListItem()
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
                bundle.putString("ItemID", itemList.get(position).getItemid());
                bundle.putString("Description", itemList.get(position).getDesc());
                bundle.putString("Photo", itemList.get(position).getPhoto());
                bundle.putString("Type", itemList.get(position).getType());
                bundle.putString("Code", itemList.get(position).getCode());
                bundle.putString("Size", itemList.get(position).getSize());
                bundle.putString("Price", itemList.get(position).getPrice());
                bundle.putString("Cost", itemList.get(position).getCost());
                UpdateItem nextFragment = new UpdateItem();
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView txtItemID, txtDesc, txtType,txtCode,txtSize,txtPrice,txtCost, txtHapus;
        ImageView imgPhoto;
        CardView cardview;
        public RecyclerHolder(View itemView) {
            super(itemView);
            imgPhoto = (ImageView)itemView.findViewById(R.id.imgPhoto);
            txtItemID=(TextView)itemView.findViewById(R.id.txtItemID);
            txtDesc=(TextView)itemView.findViewById(R.id.txtDeskripsi);
            txtType=(TextView)itemView.findViewById(R.id.txtType);
            txtCode=(TextView)itemView.findViewById(R.id.txtCode);
            txtSize =(TextView)itemView.findViewById(R.id.txtSize);
            txtPrice = (TextView)itemView.findViewById(R.id.txtPrice);
            txtCost = (TextView)itemView.findViewById(R.id.txtCost);
            txtHapus = (TextView)itemView.findViewById(R.id.Hapus);
            cardview = (CardView) itemView.findViewById(R.id.cardList_item);
        }
    }
}
