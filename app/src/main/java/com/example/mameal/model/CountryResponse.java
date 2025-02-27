package com.example.mameal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryResponse {

    @SerializedName("meals")
    List<Country> countryList;


    public CountryResponse() {
    }

    public CountryResponse(List<Country> countryList) {
        this.countryList = countryList;
    }


    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}
