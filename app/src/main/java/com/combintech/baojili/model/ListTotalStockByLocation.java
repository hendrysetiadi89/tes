package com.combintech.baojili.model;

/**
 * Created by server02 on 18/08/2017.
 */

public class ListTotalStockByLocation {
    private String nameLocation;
    private String totalStock;

    public ListTotalStockByLocation (String nameLocation, String totalStock) {
        this.setNameLocation(nameLocation);
        this.setTotalStock(totalStock);
    }

    public ListTotalStockByLocation() {

    }

    public String getNameLocation() {
        return nameLocation;
    }

    public void setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }

    public String getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(String totalStock) {
        this.totalStock = totalStock;
    }
}

