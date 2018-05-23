package com.tefsalkw.models;

import java.io.Serializable;

/**
 * Created by Hp on 16-10-2017.
 */

public class TextileProductModel  implements Serializable
{

    private String feel;
    private String sub_color;
    private String dishdasha_country;
    private String product_name;
    private String tefsal_product_id;
    private String attribute_id;
    private String country_image;
    private String brand_name;
    private String material;
    private String brand_image;
    private String price;
    private String pattern;
    private String color;
    private String pattern_image;
    private String[] product_image;
    private String product_discount;
    private String dishdasha_qty_meters;
    private String dishdasha_season;
    private String min_delivery_days;
    private String max_delivery_days;


    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getDefault_image() {
        return default_image;
    }

    public void setDefault_image(String default_image) {
        this.default_image = default_image;
    }

    private String store_id;
    private String store_name;
    private String default_image;

    public String getPrice_pr_miter() {
        return price_pr_miter;
    }

    public void setPrice_pr_miter(String price_pr_miter) {
        this.price_pr_miter = price_pr_miter;
    }

    private String price_pr_miter;

    public String getStock_in_meters() {
        return stock_in_meters;
    }

    public void setStock_in_meters(String stock_in_meters) {
        this.stock_in_meters = stock_in_meters;
    }

    private String stock_in_meters;


    private String season;

    private String color_id;

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getColor_id() {
        return color_id;
    }

    public void setColor_id(String color_id) {
        this.color_id = color_id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getPattern_id() {
        return pattern_id;
    }

    public void setPattern_id(String pattern_id) {
        this.pattern_id = pattern_id;
    }

    private String country_id;

    private String brand_id;

    private String pattern_id;
    private String sub_color_id;

    public String getSub_color_id() {
        return sub_color_id;
    }

    public void setSub_color_id(String sub_color_id) {
        this.sub_color_id = sub_color_id;
    }

    public String getDishdasha_brand_id() {
        return dishdasha_brand_id;
    }

    public void setDishdasha_brand_id(String dishdasha_brand_id) {
        this.dishdasha_brand_id = dishdasha_brand_id;
    }

    private String dishdasha_brand_id;


    public String getDishdasha_color_id() {
        return dishdasha_color_id;
    }

    public void setDishdasha_color_id(String dishdasha_color_id) {
        this.dishdasha_color_id = dishdasha_color_id;
    }

    public String getDishdasha_sub_color_id() {
        return dishdasha_sub_color_id;
    }

    public void setDishdasha_sub_color_id(String dishdasha_sub_color_id) {
        this.dishdasha_sub_color_id = dishdasha_sub_color_id;
    }

    public String getDishdasha_country_id() {
        return dishdasha_country_id;
    }

    public void setDishdasha_country_id(String dishdasha_country_id) {
        this.dishdasha_country_id = dishdasha_country_id;
    }



    private String dishdasha_color_id;
    private String dishdasha_sub_color_id;
    private String dishdasha_country_id;

    public String getDishdasha_season_id() {
        return dishdasha_season_id;
    }

    public void setDishdasha_season_id(String dishdasha_season_id) {
        this.dishdasha_season_id = dishdasha_season_id;
    }

    private String dishdasha_season_id;


    public String getDishdasha_product_name() {
        return dishdasha_product_name;
    }

    public void setDishdasha_product_name(String dishdasha_product_name) {
        this.dishdasha_product_name = dishdasha_product_name;
    }

    private String dishdasha_product_name;


    public String getFeel ()
    {
        return feel;
    }

    public void setFeel (String feel)
    {
        this.feel = feel;
    }

    public String getSub_color ()
    {
        return sub_color;
    }

    public void setSub_color (String sub_color)
    {
        this.sub_color = sub_color;
    }

    public String getDishdasha_country ()
    {
        return dishdasha_country;
    }

    public void setDishdasha_country (String dishdasha_country)
    {
        this.dishdasha_country = dishdasha_country;
    }

    public String getProduct_name ()
    {
        return product_name;
    }

    public void setProduct_name (String product_name)
    {
        this.product_name = product_name;
    }

    public String getTefsal_product_id ()
    {
        return tefsal_product_id;
    }

    public void setTefsal_product_id (String tefsal_product_id)
    {
        this.tefsal_product_id = tefsal_product_id;
    }

    public String getDishdasha_attribute_id ()
    {
        return attribute_id;
    }

    public void setDishdasha_attribute_id (String attribute_id)
    {
        this.attribute_id = attribute_id;
    }

    public String getCountry_image ()
    {
        return country_image;
    }

    public void setCountry_image (String country_image)
    {
        this.country_image = country_image;
    }

    public String getBrand_name ()
    {
        return brand_name;
    }

    public void setBrand_name (String brand_name)
    {
        this.brand_name = brand_name;
    }

    public String getMaterial ()
    {
        return material;
    }

    public void setMaterial (String material)
    {
        this.material = material;
    }

    public String getBrand_image ()
    {
        return brand_image;
    }

    public void setBrand_image (String brand_image)
    {
        this.brand_image = brand_image;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getPattern ()
    {
        return pattern;
    }

    public void setPattern (String pattern)
    {
        this.pattern = pattern;
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
    }

    public String getPattern_image ()
    {
        return pattern_image;
    }

    public void setPattern_image (String pattern_image)
    {
        this.pattern_image = pattern_image;
    }

    public String[] getProduct_image ()
    {
        return product_image;
    }

    public void setProduct_image (String[] product_image)
    {
        this.product_image = product_image;
    }

    public String getDishdasha_qty_meters ()
    {
        return dishdasha_qty_meters;
    }

    public void setDishdasha_qty_meters (String dishdasha_qty_meters)
    {
        this.dishdasha_qty_meters = dishdasha_qty_meters;
    }

    public String getDishdasha_season ()
    {
        return dishdasha_season;
    }

    public void setDishdasha_season (String dishdasha_season)
    {
        this.dishdasha_season = dishdasha_season;
    }


    public String getMin_delivery_days() {
        return min_delivery_days;
    }

    public void setMin_delivery_days(String min_delivery_days) {
        this.min_delivery_days = min_delivery_days;
    }

    public String getMax_delivery_days() {
        return max_delivery_days;
    }

    public void setMax_delivery_days(String max_delivery_days) {
        this.max_delivery_days = max_delivery_days;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [feel = "+feel+", sub_color = "+sub_color+", dishdasha_country = "+dishdasha_country+", product_name = "+product_name+", tefsal_product_id = "+tefsal_product_id+", attribute_id = "+attribute_id+", country_image = "+country_image+", brand_name = "+brand_name+", material = "+material+", brand_image = "+brand_image+", price = "+price+", pattern = "+pattern+", color = "+color+", pattern_image = "+pattern_image+", product_image = "+product_image+", dishdasha_qty_meters = "+dishdasha_qty_meters+", dishdasha_season = "+dishdasha_season+",min_delivery_days ="+min_delivery_days+",max_delivery_days="+max_delivery_days+",product_discount="+product_discount+"]";
    }
}

