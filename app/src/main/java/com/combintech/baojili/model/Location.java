package com.combintech.baojili.model;

/**
 * Created by server02 on 10/08/2017.
 */

public class Location {
    private String locationid;
    private String nameLocation;
    private String typeLocation;
    private String address;
    private String latitude;
    private String longitude;

    public Location (String locationid, String nameLocation, String typeLocation, String address, String latitude, String longitude) {
        this.setLocationid(locationid);
        this.setNamelocation(nameLocation);
        this.setTypelocation(typeLocation);
        this.setAddress(address);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public Location() {

    }

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getNamelocation() {
        return nameLocation;
    }

    public void setNamelocation(String namelocation) {
        this.nameLocation = namelocation;
    }

    public String getTypelocation() {
        return typeLocation;
    }

    public void setTypelocation(String typelocation) {
        this.typeLocation = typelocation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
