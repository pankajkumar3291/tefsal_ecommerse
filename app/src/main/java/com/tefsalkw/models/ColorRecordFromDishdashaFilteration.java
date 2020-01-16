package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by Rituparna Khadka on 1/17/2018.
 */

public class ColorRecordFromDishdashaFilteration implements Serializable
{

    private String color_id;
    private String color_name;
    private String hexa_value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String id;

    public String getName_arabic() {
        return name_arabic;
    }

    public void setName_arabic(String name_arabic) {
        this.name_arabic = name_arabic;
    }

    private String name;
    private String name_arabic;

    public String getColor_id() {
        return color_id;
    }

    public void setColor_id(String color_id) {
        this.color_id = color_id;
    }

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    public String getHexa_value() {
        return hexa_value;
    }

    public void setHexa_value(String hexa_value) {
        this.hexa_value = hexa_value;
    }
}
