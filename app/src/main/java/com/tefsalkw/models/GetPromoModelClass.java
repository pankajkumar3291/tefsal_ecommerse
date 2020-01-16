package com.tefsalkw.models;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPromoModelClass implements Serializable {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("payload")
    @Expose
    private List<Payload> payload = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Payload> getPayload() {
        return payload;
    }

    public void setPayload(List<Payload> payload) {
        this.payload = payload;
    }

}