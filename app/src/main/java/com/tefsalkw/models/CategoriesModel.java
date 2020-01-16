package com.tefsalkw.models;

/**
 * Created by new on 9/26/2017.
 */

class CategoriesModel {


    String cat_id;
    String name;

    public String getId() {
        return cat_id;
    }

    public String getAccessToken() {
        return name;
    }


    public void setId(String cat_id) {
        this.cat_id = cat_id;
    }

    public void setAccessToken(String name) {
        this.name = name;
    }

}
