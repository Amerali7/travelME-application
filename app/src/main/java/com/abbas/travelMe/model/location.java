package com.abbas.travelMe.model;

/**
 * Created by abbas on 22/03/2018.
 */

public class location {

    private int id;
    private String name;
    private String lat;
    private String lon;
    private String placeid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLon() {
        return lon;
    }

    public void setplaceid(String placeid) {
        this.placeid = placeid;
    }

    public String getPlaceid() {
        return placeid;
    }
}
