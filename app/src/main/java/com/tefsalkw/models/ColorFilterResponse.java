package com.tefsalkw.models;

import java.util.ArrayList;

public class ColorFilterResponse {

    private String message;
    private String status;

    public ArrayList<ColorRecordFromDishdashaFilteration> getRecord() {
        return record;
    }

    public void setRecord(ArrayList<ColorRecordFromDishdashaFilteration> record) {
        this.record = record;
    }

    private ArrayList<ColorRecordFromDishdashaFilteration> record;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ColorRecordFromDishdashaFilteration> getColors ()
    {
        return colors;
    }

    public void setColors ( ArrayList<ColorRecordFromDishdashaFilteration> colors)
    {
        this.colors = colors;
    }
    private ArrayList<ColorRecordFromDishdashaFilteration> colors;
}
