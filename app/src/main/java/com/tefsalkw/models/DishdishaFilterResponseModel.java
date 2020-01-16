package com.tefsalkw.models;

import java.util.ArrayList;


public class DishdishaFilterResponseModel
{
    private String message;

    private ArrayList<FilterBrandModel> brands;

    private ArrayList<FilterPatternModel> patterns;

    private String status;

    private ArrayList<FilterCountryModel> countries;

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

    public ArrayList<FilterCountryModel> getCountries ()
    {
        return countries;
    }

    public void setCountries (ArrayList<FilterCountryModel> countries)
    {
        this.countries = countries;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", brands = "+brands+", patterns = "+patterns+", status = "+status+", countries = "+countries+"]";
    }
}
