package com.combintech.baojili.model;

/**
 * Created by server02 on 18/08/2017.
 */

public class ListTotalStockByItem {
    private String itemId;
    private String totalStock;

    public ListTotalStockByItem(String itemId, String totalStock) {
        this.setItemId(itemId);
        this.setTotalStock(totalStock);
    }

    public ListTotalStockByItem() {

    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(String totalStock) {
        this.totalStock = totalStock;
    }
}

