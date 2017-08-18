package com.combintech.baojili.model;

/**
 * Created by server02 on 08/08/2017.
 */

public class Item {
    private String itemid;
    private String desc;
    private String photo;
    private String type;
    private String code;
    private String size;
    private String price;
    private String cost;

    public Item (String itemid, String desc, String photo, String type, String code, String size, String price, String cost) {
        this.setItemid(itemid);
        this.setDesc(desc);
        this.setPhoto(photo);
        this.setType(type);
        this.setCode(code);
        this.setSize(size);
        this.setPrice(price);
    }

    public Item() {

    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
