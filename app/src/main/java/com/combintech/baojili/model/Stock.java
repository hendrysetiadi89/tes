package com.combintech.baojili.model;

/**
 * Created by server02 on 15/08/2017.
 */

public class Stock {
    private String stockId;
    private String itemId;
    private String locationId;
    private String stock;

    public Stock (String stockId, String itemId, String locationId, String stock) {
        this.setStockId(stockId);
        this.setItemId(itemId);
        this.setLocationId(locationId);
        this.setStock(stock);
    }

    public Stock() {

    }


    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
