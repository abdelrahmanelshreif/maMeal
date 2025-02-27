package com.example.mameal.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("idIngredient")
    String idIngredient;
    @SerializedName("strIngredient")
    String strIngredient;
    @SerializedName("strDescription")
    String strDescription;
    @SerializedName("strType")
    String strType;
    String ingThumbnail;

    public Ingredient() {
    }

    public Ingredient(String idIngredient, String strIngredient, String strDescription, String strType) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
        this.strType = strType;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }


    public String getIngThumbnail() {
        return ingThumbnail;
    }

    public void setIngThumbnail(String ingThumbnail) {
        this.ingThumbnail = ingThumbnail;
    }
}
