package com.combintech.baojili.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.combintech.baojili.R;
import com.combintech.baojili.adapter.ItemOutAdapter;
import com.combintech.baojili.app.AppVar;
import com.combintech.baojili.app.NetworkController;
import com.combintech.baojili.model.ItemOut;
import com.robertlevonyan.views.customfloatingactionbutton.CustomFloatingActionButton;
import com.robertlevonyan.views.customfloatingactionbutton.OnFabClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by server02 on 17/08/2017.
 */

public class ListItemOut extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ItemOutAdapter adapter;
    private ProgressDialog pDialog;
    private RecyclerView.LayoutManager layoutManager;
    List<ItemOut> itemOutList = new ArrayList<ItemOut>();
    private CustomFloatingActionButton customFab;
    private SwipeRefreshLayout swipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.adapter_item_out, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle("Daftar Barang Keluar");
        customFab = (CustomFloatingActionButton) rootView.findViewById(R.id.custom_fab);
        customFab.setOnFabClickListener(new OnFabClickListener() {
            @Override
            public void onFabClick(View v) {
                //FragmentManager fragmentManager = getFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.content_frame,
                  //      new AddItemOut()).commit();
            }
        });
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerItemOut);
        swipe   = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_item);
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemOutList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ItemOutAdapter(getActivity(), itemOutList);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private void callVolley() {
        itemOutList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);
        JsonArrayRequest anggotaReq = new JsonArrayRequest(AppVar.LIST_ITEMOUT_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                ItemOut itemOut = new ItemOut();
                                itemOut.setItemOutId(obj.getString("ItemOutID"));
                                itemOut.setUserId(obj.getString("UserID"));
                                itemOut.setOutType(obj.getString("OutType"));
                                itemOut.setCode(obj.getString("Code"));
                                itemOut.setSize(obj.getString("Size"));
                                itemOut.setQuantity(obj.getString("Quantity"));
                                itemOut.setLocationName(obj.getString("Name"));
                                itemOutList.add(itemOut);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });
        NetworkController.getInstance().addToRequestQueue(anggotaReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onRefresh() {
        itemOutList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
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
