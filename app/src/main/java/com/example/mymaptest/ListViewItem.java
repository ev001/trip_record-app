package com.example.mymaptest;

import com.google.android.gms.maps.model.LatLng;

public class ListViewItem {

    private String titleStr;
    private String descStr;
    private LatLng latLng;

    public ListViewItem(String titleStr, String descStr, LatLng latLng) {
        this.titleStr = titleStr;
        this.descStr = descStr;
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getDescStr() {
        return descStr;
    }

    public void setDescStr(String descStr) {
        this.descStr = descStr;
    }
}
