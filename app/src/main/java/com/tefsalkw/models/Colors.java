package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dell on 03/13/2018.
 */

public class Colors implements Serializable {
    private String sub_color;

    public String getSub_color_arabic() {
        return sub_color_arabic;
    }

    public void setSub_color_arabic(String sub_color_arabic) {
        this.sub_color_arabic = sub_color_arabic;
    }

    private String sub_color_arabic;
    private String price;

    private String color;

    public String getColor_arabic() {
        return color_arabic;
    }

    public void setColor_arabic(String color_arabic) {
        this.color_arabic = color_arabic;
    }

    private String color_arabic;

    private String[] images;

    private String qty;

    private String info;
private  String subcategory_id;
    public String getSubcategory_id() {
        return subcategory_id;
    }
    public void setSubcategory_id(String subcategory_id) {
        this.subcategory_id = subcategory_id;
    }
    private String attribute_id;


    public List<ZaraDaraSizeModel> getSizes() {
        return sizes;
    }

    public void setSizes(List<ZaraDaraSizeModel> sizes) {
        this.sizes = sizes;
    }

    private List<ZaraDaraSizeModel> sizes;


    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    private String feel;

    public String getFeel_arabic() {
        return feel_arabic;
    }

    public void setFeel_arabic(String feel_arabic) {
        this.feel_arabic = feel_arabic;
    }

    private String feel_arabic;

    public String getPattern_name() {
        return pattern_name;
    }

    public void setPattern_name(String pattern_name) {
        this.pattern_name = pattern_name;
    }

    private String pattern_name;

    public String getPattern_name_arabic() {
        return pattern_name_arabic;
    }

    public void setPattern_name_arabic(String pattern_name_arabic) {
        this.pattern_name_arabic = pattern_name_arabic;
    }

    private String pattern_name_arabic;
    private String pattern_id;

    private String default_image;

    private String material;

    public String getMaterial_arabic() {
        return material_arabic;
    }

    public void setMaterial_arabic(String material_arabic) {
        this.material_arabic = material_arabic;
    }

    private String material_arabic;

    private String is_sold_out;

    private String stock_in_meters;

    private String price_pr_miter;


    private String color_id;

    private String[] product_image;

    private String sub_color_id;

    private String quantity;

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public String getSub_color() {
        return sub_color;
    }

    public void setSub_color(String sub_color) {
        this.sub_color = sub_color;
    }

    public String getPattern_id() {
        return pattern_id;
    }

    public void setPattern_id(String pattern_id) {
        this.pattern_id = pattern_id;
    }

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getDefault_image() {
        return default_image;
    }

    public void setDefault_image(String default_image) {
        this.default_image = default_image;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getIs_sold_out() {
        return is_sold_out;
    }

    public void setIs_sold_out(String is_sold_out) {
        this.is_sold_out = is_sold_out;
    }

    public String getStock_in_meters() {
        return stock_in_meters;
    }

    public void setStock_in_meters(String stock_in_meters) {
        this.stock_in_meters = stock_in_meters;
    }

    public String getPrice_pr_miter() {
        return price_pr_miter;
    }

    public void setPrice_pr_miter(String price_pr_miter) {
        this.price_pr_miter = price_pr_miter;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor_id() {
        return color_id;
    }

    public void setColor_id(String color_id) {
        this.color_id = color_id;
    }

    public String[] getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String[] product_image) {
        this.product_image = product_image;
    }

    public String getSub_color_id() {
        return sub_color_id;
    }

    public void setSub_color_id(String sub_color_id) {
        this.sub_color_id = sub_color_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ClassPojo [sub_color = " + sub_color + ", price = " + price + ", color = " + color + ", images = " + images + ", qty = " + qty + ", info = " + info + "]";
    }
}
