package com.example.mameal.model;

import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("strArea")
    String strArea;
    String countryThumbnail;
    public Country(){}

    public Country(String strArea) {
        this.strArea = strArea;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getCountryThumbnail() {
        return countryThumbnail;
    }

    public void setCountryThumbnail(String countryThumbnail) {
        this.countryThumbnail = countryThumbnail;
    }
}
