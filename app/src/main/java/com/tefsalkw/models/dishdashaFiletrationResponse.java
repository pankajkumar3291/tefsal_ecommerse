package com.tefsalkw.models;

import java.util.ArrayList;

/**
 * Created by Rituparna Khadka on 1/16/2018.
 */

public class dishdashaFiletrationResponse
{
    private String message;
    private String status;
    private ArrayList<FilterBrandModel> brands;

    private ArrayList<FilterPatternModel> patterns;



    private ArrayList<ColorRecordFromDishdashaFilteration> colors;

    private ArrayList<FilterCountryModel> countries;

    private ArrayList<TextileProductModel> products;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<FilterBrandModel> getBrands ()
    {
        return brands;
    }

    public void setBrands (ArrayList<FilterBrandModel> brands)
    {
        this.brands = brands;
    }

    public ArrayList<FilterPatternModel> getPatterns ()
    {
        return patterns;
    }

    public void setPatterns (ArrayList<FilterPatternModel> patterns)
    {
        this.patterns = patterns;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public  ArrayList<ColorRecordFromDishdashaFilteration> getColors ()
    {
        return colors;
    }

    public void setColors ( ArrayList<ColorRecordFromDishdashaFilteration> colors)
    {
        this.colors = colors;
    }

    public ArrayList<FilterCountryModel> getCountries ()
    {
        return countries;
    }

    public void setCountries (ArrayList<FilterCountryModel> countries)
    {
        this.countries = countries;
    }

    public ArrayList<TextileProductModel> getProducts ()
    {
        return products;
    }

    public void setProducts (ArrayList<TextileProductModel> products)
    {
        this.products = products;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", brands = "+brands+", patterns = "+patterns+", status = "+status+", colors = "+colors+", countries = "+countries+", products = "+products+"]";
    }
}
