package com.tefsalkw.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tailor_services implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("meter")
    @Expose
    private String meter;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("cart_id")
    @Expose
    private Integer cartId;
    @SerializedName("dishdasha_tailor_product_id")
    @Expose
    private Integer dishdashaTailorProductId;
    @SerializedName("tailor_id")
    @Expose
    private Object tailorId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("product_id")
    @Expose
    private Object productId;
    @SerializedName("item_id")
    @Expose
    private Object itemId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("service_price")
    @Expose
    private String servicePrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getDishdashaTailorProductId() {
        return dishdashaTailorProductId;
    }

    public void setDishdashaTailorProductId(Integer dishdashaTailorProductId) {
        this.dishdashaTailorProductId = dishdashaTailorProductId;
    }

    public Object getTailorId() {
        return tailorId;
    }

    public void setTailorId(Object tailorId) {
        this.tailorId = tailorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

    public Object getItemId() {
        return itemId;
    }

    public void setItemId(Object itemId) {
        this.itemId = itemId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }
}
