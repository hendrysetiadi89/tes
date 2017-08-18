package com.combintech.baojili.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.combintech.baojili.R;
import com.combintech.baojili.model.ListTotalStockByItem;
import com.combintech.baojili.model.ListTotalStockByLocation;

import java.util.List;

/**
 * Created by server02 on 18/08/2017.
 */

public class ListTotalStockByItemAdapter extends RecyclerView.Adapter<ListTotalStockByItemAdapter.RecyclerHolder> {
    private List<ListTotalStockByItem> totalStockByItemList;
    private Context context;

    public ListTotalStockByItemAdapter(Context context, List<ListTotalStockByItem> totalStockByItemList) {
        this.context = context;
        this.totalStockByItemList = totalStockByItemList;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_total_stock_by_item, parent,false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {
        final ListTotalStockByItem listTotalStock = totalStockByItemList.get(position);
        holder.txtItemId.setText("Item ID: "+listTotalStock.getItemId());
        holder.txtTotalStock.setText("Total Stok: "+listTotalStock.getTotalStock());
    }

    @Override
    public int getItemCount() {
        return totalStockByItemList.size();
    }


    public static class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView txtItemId, txtTotalStock;
        CardView cardview;
        public RecyclerHolder(View itemView) {
            super(itemView);
            txtItemId =(TextView)itemView.findViewById(R.id.itemID);
            txtTotalStock =(TextView)itemView.findViewById(R.id.totalStok);
            cardview = (CardView) itemView.findViewById(R.id.cardList_totalStockByItem);
        }
    }
}
