package com.tefsalkw.models;

import java.io.Serializable;

public class Item_details implements Serializable {

    private Tailor_services tailor_services;

    private String order_type;

    public Tailor_services getTailor_services ()
    {
        return tailor_services;
    }

    public void setTailor_services (Tailor_services tailor_services)
    {
        this.tailor_services = tailor_services;
    }

    public String getOrder_type ()
    {
        return order_type;
    }

    public void setOrder_type (String order_type)
    {
        this.order_type = order_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tailor_services = "+tailor_services+", order_type = "+order_type+"]";
    }
}
