package com.tefsalkw.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class GetCartPromo implements Serializable {

    @SerializedName("promo_id")
    @Expose
    private Integer promoId;
    @SerializedName("promo_name")
    @Expose
    private String promoName;
    @SerializedName("product_id")
    @Expose
    private Object productId;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("amount_spent")
    @Expose
    private Object amountSpent;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("product_details")
    @Expose
    private ProductDetails productDetails;

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Object getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(Object amountSpent) {
        this.amountSpent = amountSpent;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

}