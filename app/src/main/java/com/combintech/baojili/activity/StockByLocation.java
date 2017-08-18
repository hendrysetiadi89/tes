package com.combintech.baojili.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.combintech.baojili.R;

/**
 * Created by dekikurnia on 13/08/17.
 */

public class StockByLocation extends Fragment {

    private Button btnTotalStokLocation;

    public StockByLocation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_by_location, container, false);
        btnTotalStokLocation = (Button) rootView.findViewById(R.id.totalStokLocation);

        btnTotalStokLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,
                        new ListTotalStockByLocationFragment()).commit();
            }
        });
        return rootView;
    }
}
