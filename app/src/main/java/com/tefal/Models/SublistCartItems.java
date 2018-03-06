package com.tefal.Models;

import java.io.Serializable;

/**
 * Created by Dell on 05-03-2018.
 */

public class SublistCartItems implements Serializable {

    private int itemPosition;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String productId;


    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    private String itemName;
}
