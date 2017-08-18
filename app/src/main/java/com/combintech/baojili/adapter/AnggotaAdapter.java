package com.combintech.baojili.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
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
import com.combintech.baojili.activity.ListLocation;
import com.combintech.baojili.activity.UpdateAnggota;
import com.combintech.baojili.app.AppVar;
import com.combintech.baojili.model.Anggota;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DekiKurnia on 8/4/2017.
 */

public class AnggotaAdapter extends RecyclerView.Adapter<AnggotaAdapter.RecyclerHolder> {
    private List<Anggota> anggotaList;
    private Context context;

    public AnggotaAdapter(Context context, List<Anggota> anggotaList) {
        this.context = context;
        this.anggotaList = anggotaList;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anggota,parent,false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {
        final Anggota anggota = anggotaList.get(position);
        holder.txtUsername.setText(anggota.getUsername());
        holder.txtName.setText(anggota.getName());
        holder.txtEmail.setText(anggota.getEmail());
        holder.txtPassword.setText(anggota.getPassword());
        holder.txtRole.setText(anggota.getRole());
        Glide.with(context).load(anggota.getPhoto())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .dontAnimate()
                .into(holder.photoUser);

        holder.txtHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Yakin ingin menghapus data ini ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {
                                final String userid = holder.txtUsername.getText().toString().trim();
                                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, AppVar.DELETE_ANGGOTA+userid,
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
                                                                    new ListAnggota()
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
                bundle.putString("UserID", anggotaList.get(position).getUsername());
                bundle.putString("Name", anggotaList.get(position).getName());
                bundle.putString("Email", anggotaList.get(position).getEmail());
                bundle.putString("Password", anggotaList.get(position).getPassword());
                bundle.putString("Role", anggotaList.get(position).getRole());
                bundle.putString("Photo", anggotaList.get(position).getPhoto());
                UpdateAnggota nextFragment = new UpdateAnggota();
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return anggotaList.size();
    }


    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView txtUsername,txtName,txtEmail,txtPassword,txtRole, txtHapus;
        CircleImageView photoUser;
        CardView cardview;
        public RecyclerHolder(View itemView) {
            super(itemView);
            txtUsername = (TextView)itemView.findViewById(R.id.txtUsername);
            txtName=(TextView)itemView.findViewById(R.id.txtName);
            txtEmail=(TextView)itemView.findViewById(R.id.txtEmail);
            txtPassword =(TextView)itemView.findViewById(R.id.txtPassword);
            txtRole = (TextView)itemView.findViewById(R.id.txtRole);
            photoUser = (CircleImageView) itemView.findViewById(R.id.imgUser);
            txtHapus = (TextView)itemView.findViewById(R.id.Hapus);
            cardview = (CardView) itemView.findViewById(R.id.cardlist_anggota);

        }
    }
}
