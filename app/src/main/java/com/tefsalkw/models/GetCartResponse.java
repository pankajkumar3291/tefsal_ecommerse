package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;



public class GetCartResponse implements Serializable {
    private Integer status;
    private Integer totalQty;
    private String message;
    private String message_ar;
    private List<GetCartRecord> record;
    private List<Integer> promo;
    private int cart_count;
    private  String cart_id;
    private  String total_amount_cart;
    private  String delivery_charge;
    private List<Integer> selected_promo = null;
    public List<Integer> getSelectedPromo() {
        return selected_promo;
    }
    public void setSelectedPromo(List<Integer> selectedPromo) {
        this.selected_promo = selectedPromo;
    }
    private IndividualDiscount individual_discount;


    public IndividualDiscount getIndividual_discount() {
        return individual_discount;
    }
    public void setIndividual_discount(IndividualDiscount individual_discount) {
        this.individual_discount = individual_discount;
    }
    public List<Integer> getPromo() {
        return promo;
    }
    public void setPromo(List<Integer> promo) {
        this.promo = promo;
    }


    public Integer getTotalQty(){
        return totalQty;
    }
    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }


    public int getCart_count() {
        return cart_count;
    }
    public void setCart_count(int cart_count) {
        this.cart_count = cart_count;
    }


    public String getMessage_ar() {
        return message_ar;
    }

    public void setMessage_ar(String message_ar) {
        this.message_ar = message_ar;
    }



    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<GetCartRecord> getRecord ()
    {
        return record;
    }

    public void setRecord (List<GetCartRecord> record)
    {
        this.record = record;
    }

    public Integer getStatus ()
    {
        return status;
    }

    public void setStatus (Integer status)
    {
        this.status = status;
    }

    public String getTotal_amount_cart() {
        return total_amount_cart;
    }

    public void setTotal_amount_cart(String total_amount_cart) {
        this.total_amount_cart = total_amount_cart;
    }



    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }



    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", record = "+record+", status = "+status+"]";
    }
}

