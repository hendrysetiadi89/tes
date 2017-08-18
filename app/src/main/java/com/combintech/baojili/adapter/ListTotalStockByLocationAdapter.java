package com.combintech.baojili.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.combintech.baojili.R;
import com.combintech.baojili.model.ListTotalStockByLocation;

import java.util.List;

/**
 * Created by server02 on 18/08/2017.
 */

public class ListTotalStockByLocationAdapter extends RecyclerView.Adapter<ListTotalStockByLocationAdapter.RecyclerHolder> {
    private List<ListTotalStockByLocation> totalStockByLocationList;
    private Context context;

    public ListTotalStockByLocationAdapter(Context context, List<ListTotalStockByLocation> totalStockByLocationList) {
        this.context = context;
        this.totalStockByLocationList = totalStockByLocationList;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_total_stock_by_location, parent,false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {
        final ListTotalStockByLocation listTotalStock = totalStockByLocationList.get(position);
        holder.txtLocationName.setText("Nama Lokasi: "+listTotalStock.getNameLocation());
        holder.txtTotalStock.setText("Total Stok: "+listTotalStock.getTotalStock());
    }

    @Override
    public int getItemCount() {
        return totalStockByLocationList.size();
    }


    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView txtLocationName, txtTotalStock;
        CardView cardview;
        public RecyclerHolder(View itemView) {
            super(itemView);
            txtLocationName =(TextView)itemView.findViewById(R.id.nameLocation);
            txtTotalStock =(TextView)itemView.findViewById(R.id.totalStok);
            cardview = (CardView) itemView.findViewById(R.id.cardList_totalStockByLocation);
        }
    }
}
