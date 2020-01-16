package com.tefsalkw.models;

/**
 * Created by Rituparna Khadka on 12/27/2017.
 */

public class ProductCountryRecordModel
{

    private String id;

    private String name;

    private String name_arabic;

    public String getName_arabic() {
        return name_arabic;
    }

    public void setName_arabic(String name_arabic) {
        this.name_arabic = name_arabic;
    }

    private String image;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected = false;
    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", name = "+name+", image = "+image+"]";
    }
}
