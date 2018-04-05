package com.tefsalkw.models;

/**
 * Created by Rituparna Khadka on 12/26/2017.
 */

public class ColorsRecordModel
{
    private String id;


    private String name;

    private String image;

    private String hexa_value;



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

    public String getHexa_value() {
        return hexa_value;
    }

    public void setHexa_value(String hexa_value) {
        this.hexa_value = hexa_value;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", name = "+name+", image = "+image+", hexa_value ="+hexa_value+"]";
    }
}
