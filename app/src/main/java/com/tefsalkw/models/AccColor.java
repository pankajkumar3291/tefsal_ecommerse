package com.tefsalkw.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccColor {

    @SerializedName("attribute_id")
    @Expose
    private Integer attributeId;

    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("sub_color")
    @Expose
    private String subColor;


    @SerializedName("color_arabic")
    @Expose
    private String color_arabic;

    public String getColor_arabic() {
        return color_arabic;
    }

    public void setColor_arabic(String color_arabic) {
        this.color_arabic = color_arabic;
    }

    public String getSubColorArabic() {
        return subColorArabic;
    }

    public void setSubColorArabic(String subColorArabic) {
        this.subColorArabic = subColorArabic;
    }

    @SerializedName("sub_color_arabic")
    @Expose
    private String subColorArabic;


    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("sizes")
    @Expose
    private List<AccSizes> sizes = null;

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSubColor() {
        return subColor;
    }

    public void setSubColor(String subColor) {
        this.subColor = subColor;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<AccSizes> getSizes() {
        return sizes;
    }

    public void setSizes(List<AccSizes> sizes) {
        this.sizes = sizes;
    }

}
