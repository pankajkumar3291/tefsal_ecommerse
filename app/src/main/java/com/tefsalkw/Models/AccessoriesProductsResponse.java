package com.tefsalkw.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hp on 20-11-2017.
 */

public class AccessoriesProductsResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("record")
    @Expose
    private List<AccessoriesRecord> record = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AccessoriesRecord> getRecord() {
        return record;
    }

    public void setRecord(List<AccessoriesRecord> record) {
        this.record = record;
    }

}
