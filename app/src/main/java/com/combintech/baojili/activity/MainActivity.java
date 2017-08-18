package com.combintech.baojili.activity;

import android.annotation.TargetApi;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.combintech.baojili.R;
import com.combintech.baojili.app.AppVar;
import com.combintech.baojili.app.NetworkController;
import com.combintech.baojili.model.Anggota;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;
    private TextView nameUser;
    private TextView emailUser;
    private TextView welcome;
    private TextView qrcode;
    private TextView role;
    private CircleImageView photoUser;
    private NavigationView navigationView;
    List<Anggota> anggotaList = new ArrayList<Anggota>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MainActivity.this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        welcome = (TextView) findViewById(R.id.helloworld);
        qrcode =(TextView) findViewById(R.id.qrcode);
        role = (TextView) findViewById(R.id.role);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header= navigationView.getHeaderView(0);
        nameUser = (TextView)header.findViewById(R.id.loginUser);
        emailUser = (TextView)header.findViewById(R.id.emailUser);
        photoUser = (CircleImageView)header.findViewById(R.id.profile_image);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,
                new StockInfo()).commit();

        SharedPreferences sp = getSharedPreferences("key", 0);
        String userID = sp.getString("valueUserID","");
        String code = sp.getString("valueCode", "");
        welcome.setText(userID);
        qrcode.setText(code);

        final String logger = welcome.getText().toString().trim();
        JsonArrayRequest anggotaReq = new JsonArrayRequest(AppVar.LOGGER_URL+logger,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                String username = obj.getString("UserID");
                                String name = obj.getString("Name");
                                String email = obj.getString("Email");
                                String photo = obj.getString("Photo");
                                String userRole = obj.getString("Role");
                                welcome.setText(username);
                                role.setText(userRole);
                                nameUser.setText(name);
                                emailUser.setText(email);
                                Glide.with(context).load(photo)
                                        .error(R.drawable.placeholder)
                                        .placeholder(R.drawable.placeholder)
                                        .dontAnimate()
                                        .into(photoUser);
                                hideMenu();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        NetworkController.getInstance().addToRequestQueue(anggotaReq);

        final String loggerQrCode = qrcode.getText().toString().trim();
        JsonArrayRequest anggotaReqQrCode = new JsonArrayRequest(AppVar.LOGGER_QR_URL+loggerQrCode,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                String username = obj.getString("UserID");
                                String name = obj.getString("Name");
                                String email = obj.getString("Email");
                                String photo = obj.getString("Photo");
                                String userRole = obj.getString("Role");
                                welcome.setText(username);
                                role.setText(userRole);
                                nameUser.setText(name);
                                emailUser.setText(email);
                                Glide.with(context).load(photo)
                                        .error(R.drawable.placeholder)
                                        .placeholder(R.drawable.placeholder)
                                        .dontAnimate()
                                        .into(photoUser);
                                hideMenu();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        NetworkController.getInstance().addToRequestQueue(anggotaReqQrCode);
    }

    @Override
    public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Yakin keluar aplikasi ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {
                                finishAffinity();
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.user_list) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,
                    new ListAnggota()).commit();
        } else if (id == R.id.barang_info) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,
                    new ListItem()).commit();
        } else if (id == R.id.location_list) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,
                    new ListLocation()).commit();
        } else if (id == R.id.stock_info) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,
                    new StockInfo()).commit();
        } else if (id == R.id.item_in) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,
                    new ListItemIn()).commit();
        } else if (id == R.id.item_out) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,
                    new ListItemOut()).commit();
        } else if (id == R.id.logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            ActivityCompat.finishAffinity(this);
            SharedPreferences sp = getSharedPreferences("key", 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideMenu() {
        if (role.getText().toString().equals("Karyawan")) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.getMenu().setGroupVisible(R.id.group_manajer, false);
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
