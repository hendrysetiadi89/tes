package com.combintech.baojili.model;

/**
 * Created by server02 on 17/08/2017.
 */

public class ItemOut {
    private String itemOutId;
    private String userId;
    private String outType;
    private String code;
    private String size;
    private String quantity;
    private String locationName;

    public ItemOut(String itemOutId, String userId, String outType, String code, String size, String quantity, String locationName) {
        this.setItemOutId(itemOutId);
        this.setUserId(userId);
        this.setOutType(outType);
        this.setCode(code);
        this.setSize(size);
        this.setQuantity(quantity);
        this.setLocationName(locationName);
    }

    public ItemOut() {

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOutType() {
        return outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getItemOutId() {
        return itemOutId;
    }

    public void setItemOutId(String itemOutId) {
        this.itemOutId = itemOutId;
    }
}
