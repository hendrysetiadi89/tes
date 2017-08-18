package com.combintech.baojili.model;

/**
 * Created by server02 on 17/08/2017.
 */

public class ItemIn {
    private String itemInId;
    private String userId;
    private String inType;
    private String code;
    private String size;
    private String quantity;
    private String locationName;

    public ItemIn (String itemInId, String userId, String inType, String code, String size, String quantity, String locationName) {
        this.setItemInId(itemInId);
        this.setUserId(userId);
        this.setInType(inType);
        this.setCode(code);
        this.setSize(size);
        this.setQuantity(quantity);
        this.setLocationName(locationName);
    }

    public ItemIn() {

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInType() {
        return inType;
    }

    public void setInType(String inType) {
        this.inType = inType;
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

    public String getItemInId() {
        return itemInId;
    }

    public void setItemInId(String itemInId) {
        this.itemInId = itemInId;
    }
}
