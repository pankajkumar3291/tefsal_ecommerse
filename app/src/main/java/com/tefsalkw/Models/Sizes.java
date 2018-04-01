package com.tefsalkw.Models;

import android.graphics.Color;

import java.util.List;

/**
 * Created by Dell on 03/13/2018.
 */

public class Sizes {
    private List<Colors> colors;

    private String size;

    public List<Colors> getColors ()
    {
        return colors;
    }

    public void setColors (List<Colors> colors)
    {
        this.colors = colors;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [colors = "+colors+", size = "+size+"]";
    }
}
