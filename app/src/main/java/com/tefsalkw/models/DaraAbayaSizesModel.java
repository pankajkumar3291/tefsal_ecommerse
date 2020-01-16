package com.tefsalkw.models;

/**
 * Created by Dell on 19-03-2018.
 */

public class DaraAbayaSizesModel {
    private Sizes[] sizes;

    private String sub_color;

    private String color;

    private String[] images;

    public Sizes[] getSizes ()
    {
        return sizes;
    }

    public void setSizes (Sizes[] sizes)
    {
        this.sizes = sizes;
    }

    public String getSub_color ()
    {
        return sub_color;
    }

    public void setSub_color (String sub_color)
    {
        this.sub_color = sub_color;
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
    }

    public String[] getImages ()
    {
        return images;
    }

    public void setImages (String[] images)
    {
        this.images = images;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sizes = "+sizes+", sub_color = "+sub_color+", color = "+color+", images = "+images+"]";
    }
}
