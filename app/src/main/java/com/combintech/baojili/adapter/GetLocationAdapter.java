package com.combintech.baojili.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.combintech.baojili.R;
import com.combintech.baojili.model.Location;

import java.util.List;

/**
 * Created by server02 on 14/08/2017.
 */

public class GetLocationAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Location> item;

    public GetLocationAdapter (Activity activity, List<Location> item) {
        this.activity = activity;
        this.item = item;
    }
    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_location_name, null);

        TextView pendidikan = (TextView) convertView.findViewById(R.id.location);

        Location data;
        data = item.get(position);

        pendidikan.setText(data.getNamelocation());

        return convertView;
    }
}
