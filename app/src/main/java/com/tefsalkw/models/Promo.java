package com.tefsalkw.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class Promo implements Serializable {

    @SerializedName("promo_id")
    @Expose
    private Integer promoId;
    @SerializedName("promo_name")
    @Expose
    private String promoName;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("amount_spent")
    @Expose
    private String amountSpent;
    @SerializedName("voucher_amount")
    @Expose
    private String voucherAmount;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("voucher_amount_valid")
    @Expose
    private String voucherAmountValid;

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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(String amountSpent) {
        this.amountSpent = amountSpent;
    }

    public String getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(String voucherAmount) {
        this.voucherAmount = voucherAmount;
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

    public String getVoucherAmountValid() {
        return voucherAmountValid;
    }

    public void setVoucherAmountValid(String voucherAmountValid) {
        this.voucherAmountValid = voucherAmountValid;
    }

}