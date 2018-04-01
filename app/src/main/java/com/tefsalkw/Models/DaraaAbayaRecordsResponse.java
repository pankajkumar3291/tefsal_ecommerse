package com.tefsalkw.Models;

import java.util.List;

/**
 * Created by Dell on 19-03-2018.
 */

public class DaraaAbayaRecordsResponse {

    private List<DaraAbayaCategoriesModel> categories;

    public List<DaraAbayaCategoriesModel> getCategories ()
    {
        return categories;
    }

    public void setCategories (List<DaraAbayaCategoriesModel> categories)
    {
        this.categories = categories;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [categories = "+categories+"]";
    }

}
