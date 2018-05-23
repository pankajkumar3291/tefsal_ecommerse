package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;

public class AccessoriesListResponseNew implements Serializable {

    private String message;

    private List<AccessoriesRecord> record;

    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", record = " + record + ", status = " + status + "]";
    }
}
