package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by Dell on 10-02-2018.
 */

public class CustomBundle implements Serializable {

    public String getString() {
        return string;
    }

    public void putString(String string) {
        this.string = string;
    }

    public Serializable getSerializable() {
        return serializable;
    }

    public void putSerializable(Serializable serializable) {
        this.serializable = serializable;
    }

    public Integer getInteger() {
        return integer;
    }

    public void putInteger(Integer integer) {
        this.integer = integer;
    }

    private String string;

    private Serializable serializable;

    private Integer integer;

}
