package com.tefsalkw.models;

import java.io.Serializable;

public class Tailor_services implements Serializable {
    private String total_amount;

    private String dishdasha_tailor_product_id;

    private String meter;

    private String price;

    private String store_id;

    private String qty;

    private String service_name;

    private String tailor_id;

    public double getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(double discounted_price) {
        this.discounted_price = discounted_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    private  double discounted_price;

    private  double discount;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getDishdasha_tailor_product_id() {
        return dishdasha_tailor_product_id;
    }

    public void setDishdasha_tailor_product_id(String dishdasha_tailor_product_id) {
        this.dishdasha_tailor_product_id = dishdasha_tailor_product_id;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getTailor_id() {
        return tailor_id;
    }

    public void setTailor_id(String tailor_id) {
        this.tailor_id = tailor_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [total_amount = " + total_amount + ", dishdasha_tailor_product_id = " + dishdasha_tailor_product_id + ", meter = " + meter + ", price = " + price + ", store_id = " + store_id + ", qty = " + qty + ", service_name = " + service_name + ", tailor_id = " + tailor_id + "]";
    }
}
