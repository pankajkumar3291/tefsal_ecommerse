package com.tefsalkw.models;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductDetails implements Serializable{

    @SerializedName("Dishdasha Textile")
    @Expose
    private List<TextileProductModel> dishdashaTextile = null;
    @SerializedName("Accessories")
    @Expose
    private List<AccessoriesRecord> accessories = null;
    @SerializedName("Daraa And Abaya")
    @Expose
    private List<ProductRecord> daraaAndAbaya = null;

    public List<TextileProductModel> getDishdashaTextile(){
        return dishdashaTextile;
    }

    public void setDishdashaTextile(List<TextileProductModel> dishdashaTextile){
        this.dishdashaTextile = dishdashaTextile;
    }

    public List<AccessoriesRecord> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<AccessoriesRecord> accessories){
        this.accessories = accessories;
    }

    public List<ProductRecord> getDaraaAndAbaya() {
        return daraaAndAbaya;
    }

    public void setDaraaAndAbaya(List<ProductRecord> daraaAndAbaya){
        this.daraaAndAbaya = daraaAndAbaya;
    }

}