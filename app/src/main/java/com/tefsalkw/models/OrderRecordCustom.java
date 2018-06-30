package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;

public class OrderRecordCustom implements Serializable {


    private String itemType;
    private String storeId;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public List<Order_items> getTextileItems() {
        return textileItems;
    }

    public void setTextileItems(List<Order_items> textileItems) {
        this.textileItems = textileItems;
    }

    public List<Order_items> getTailorItems() {
        return tailorItems;
    }

    public void setTailorItems(List<Order_items> tailorItems) {
        this.tailorItems = tailorItems;
    }


    private String storeName;

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage;
    }

    private String storeImage;
    private String styleName;

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    private float totalAmount;

    private List<Order_items> textileItems;

    private List<Order_items> tailorItems;

    public List<Order_items> getOtherItems() {
        return otherItems;
    }

    public void setOtherItems(List<Order_items> otherItems) {
        this.otherItems = otherItems;
    }

    private List<Order_items> otherItems;





    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    private String delivery_status;

    public String getExpected_delivery_date() {
        return expected_delivery_date;
    }

    public void setExpected_delivery_date(String expected_delivery_date) {
        this.expected_delivery_date = expected_delivery_date;
    }

    private String expected_delivery_date;
}
