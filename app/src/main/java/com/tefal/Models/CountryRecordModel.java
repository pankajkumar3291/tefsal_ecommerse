package com.tefal.Models;

/**
 * Created by Rituparna Khadka on 11/29/2017.
 */

public class CountryRecordModel
{
    private String iso;

    private String name;

    private String image;

    public String getIso ()
    {
        return iso;
    }

    public void setIso (String iso)
    {
        this.iso = iso;
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

    @Override
    public String toString()
    {
        return "ClassPojo [iso = "+iso+", name = "+name+", image = "+image+"]";
    }
}
