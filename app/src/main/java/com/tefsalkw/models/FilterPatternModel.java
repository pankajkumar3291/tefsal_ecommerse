package com.tefsalkw.models;

/**
 * Created by Rituparna Khadka on 11/25/2017.
 */

public class FilterPatternModel
{
    private String pattern_name;

    private String is_popular;

    private String pattern_image;

    private String pattern_id;

    public String getPattern_name ()
    {
        return pattern_name;
    }

    public void setPattern_name (String pattern_name)
    {
        this.pattern_name = pattern_name;
    }

    public String getIs_popular ()
    {
        return is_popular;
    }

    public void setIs_popular (String is_popular)
    {
        this.is_popular = is_popular;
    }

    public String getPattern_image ()
    {
        return pattern_image;
    }

    public void setPattern_image (String pattern_image)
    {
        this.pattern_image = pattern_image;
    }

    public String getPattern_id ()
    {
        return pattern_id;
    }

    public void setPattern_id (String pattern_id)
    {
        this.pattern_id = pattern_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pattern_name = "+pattern_name+", is_popular = "+is_popular+", pattern_image = "+pattern_image+", pattern_id = "+pattern_id+"]";
    }


}
