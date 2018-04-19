package com.tefsalkw.models;

import java.util.List;

public class SizesNew {

    private ColorsNew colors;

    private String size;

    public ColorsNew getColors ()
    {
        return colors;
    }

    public void setColors (ColorsNew colors)
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
