package com.tefsalkw.models;

/**
 * Created by Rituparna Khadka on 1/9/2018.
 */

public class BadgeRecordModel
{

    public int getTotal_badge() {
        return total_badge;
    }

    public void setTotal_badge(int total_badge) {
        this.total_badge = total_badge;
    }

    public int getMails_badge() {
        return mails_badge;
    }

    public void setMails_badge(int mails_badge) {
        this.mails_badge = mails_badge;
    }

    public int getOrders_badge() {
        return orders_badge;
    }

    public void setOrders_badge(int orders_badge) {
        this.orders_badge = orders_badge;
    }

    public int getCart_badge() {
        return cart_badge;
    }

    public void setCart_badge(int cart_badge) {
        this.cart_badge = cart_badge;
    }

    private int total_badge = 0;
    private int mails_badge = 0;
    private int orders_badge = 0;
    private int cart_badge= 0;



    @Override
    public String toString()
    {
        return "ClassPojo [total_badge = "+total_badge+", mails_badge = "+mails_badge+", orders_badge = "+orders_badge+"]";
    }
}
