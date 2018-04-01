package com.tefsalkw.Models;

/**
 * Created by Rituparna Khadka on 1/9/2018.
 */

public class BadgeRecordModel
{

    private String total_badge;
    private String mails_badge;
    private String orders_badge;

    public String getCart_badge() {
        return cart_badge;
    }

    public void setCart_badge(String cart_badge) {
        this.cart_badge = cart_badge;
    }

    private String cart_badge;

    public String getTotal_badge ()
    {
        return total_badge;
    }

    public void setTotal_badge (String total_badge)
    {
        this.total_badge = total_badge;
    }

    public String getMails_badge ()
    {
        return mails_badge;
    }

    public void setMails_badge (String mails_badge)
    {
        this.mails_badge = mails_badge;
    }

    public String getOrders_badge ()
    {
        return orders_badge;
    }

    public void setOrders_badge (String orders_badge)
    {
        this.orders_badge = orders_badge;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total_badge = "+total_badge+", mails_badge = "+mails_badge+", orders_badge = "+orders_badge+"]";
    }
}
