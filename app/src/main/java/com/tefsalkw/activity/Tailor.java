package com.tefsalkw.activity;

/**
 * Created by AC 101 on 11-10-2017.
 */


public class Tailor {
    private String title,heading,subheading,qty,price ;

    public Tailor() {
    }

    public Tailor(String title, String heading, String subheading, String qty, String price) {
        this.title = title;
        this.heading = heading;
        this.subheading=subheading;
        this.qty= qty;
        this.price= price;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading;
    }
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

