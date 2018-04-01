package com.tefsalkw.Models;

/**
 * Created by Hp on 16-10-2017.
 */

public class AccessoriesModel {
    private String sub_cat_id;

    private String sub_cat_name;

    private String image;

    public String getSub_cat_id ()
    {
        return sub_cat_id;
    }

    public void setSub_cat_id (String sub_cat_id)
    {
        this.sub_cat_id = sub_cat_id;
    }

    public String getSub_cat_name ()
    {
        return sub_cat_name;
    }

    public void setSub_cat_name (String sub_cat_name)
    {
        this.sub_cat_name = sub_cat_name;
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
        return "ClassPojo [sub_cat_id = "+sub_cat_id+", sub_cat_name = "+sub_cat_name+", image = "+image+"]";
    }
}

