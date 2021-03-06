package com.tefsalkw.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rituparna Khadka on 11/14/2017.
 */

public class ProductRecord extends BaseModel implements Serializable
{
    private List<ProductSizes> sizes;
    private Integer item_id;
    public Integer getItem_id() {
        return item_id;
    }
    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }
    private String item_type;
    public String getItem_type() {
        return item_type;
    }
    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }
    private String measurements;
    private int position;
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public void setPromo(List<Promo> promo) {
        this.promo = promo;
    }
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    private String brand_image;

    private String sub_color;

    private String color;
    public List<Promo> getPromo() {
        return promo;
    }
    public String getDiscount(){
        return discount.isEmpty() ? "0.0" : discount;
    }
    private List<Promo> promo;

    private String discount ;

    public List<Colors> getColors() {
        return colors;
    }
    public void setColors(List<Colors> colors) {
        this.colors = colors;
    }

    private List<Colors> colors;

    private String product_name;

    private String product_name_arabic;

    private String product_desc;

    private List<String> product_images;

    private String tefsal_product_id;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    private String store_id;

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }

    private String flag;

    private String attribute_id;

    private String store_name;

    public String getProduct_name_arabic() {
        return product_name_arabic;
    }

    public void setProduct_name_arabic(String product_name_arabic) {
        this.product_name_arabic = product_name_arabic;
    }
    public String getStore_name_arabic() {
        return store_name_arabic;
    }

    public void setStore_name_arabic(String store_name_arabic) {
        this.store_name_arabic = store_name_arabic;
    }

    public String getBrand_name_arabic() {
        return brand_name_arabic;
    }

    public void setBrand_name_arabic(String brand_name_arabic) {
        this.brand_name_arabic = brand_name_arabic;
    }

    private String store_name_arabic;
    private String brand_name;
    private String brand_name_arabic;

    private String min_delivery_days;
    private String max_delivery_days;

    private  String product_discount;

    private  String thumb_url;

    public String getDefault_price() {

        return default_price.isEmpty() ? "0.0" : default_price;

    }

    public void setDefault_price(String default_price) {
        this.default_price = default_price;
    }

    public String getDefault_product_image() {
        return default_product_image;
    }

    public void setDefault_product_image(String default_product_image) {
        this.default_product_image = default_product_image;
    }

    private  String default_price;
    private  String default_product_image;

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public List<ProductSizes>  getSizes ()
    {
        return sizes;
    }

    public void setSizes (List<ProductSizes>  sizes)
    {
        this.sizes = sizes;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }


    public String getBrand_image ()
    {
        return brand_image;
    }

    public void setBrand_image (String brand_image)
    {
        this.brand_image = brand_image;
    }

    public String getSub_color ()
    {
        return sub_color;
    }

    public void setSub_color (String sub_color)
    {
        this.sub_color = sub_color;
    }

    public String getColor ()
    {
        return color;
    }

    public void setColor (String color)
    {
        this.color = color;
    }

    public String getProduct_name ()
    {
        return product_name;
    }

    public void setProduct_name (String product_name)
    {
        this.product_name = product_name;
    }

    public String getProduct_desc ()
    {
        return product_desc;
    }

    public void setProduct_desc (String product_desc)
    {
        this.product_desc = product_desc;
    }

    public List<String> getProduct_images ()
    {
        return product_images;
    }

    public void setProduct_images (List<String> product_images)
    {
        this.product_images = product_images;
    }

    public String getTefsal_product_id ()
    {
        return tefsal_product_id;
    }

    public void setTefsal_product_id (String tefsal_product_id)
    {
        this.tefsal_product_id = tefsal_product_id;
    }

    public String getAttribute_id ()
    {
        return attribute_id;
    }

    public void setAttribute_id (String attribute_id)
    {
        this.attribute_id = attribute_id;
    }

    public String getStore_name ()
    {
        return store_name;
    }

    public void setStore_name (String store_name)
    {
        this.store_name = store_name;
    }

    public String getBrand_name ()
    {
        return brand_name;
    }

    public void setBrand_name (String brand_name)
    {
        this.brand_name = brand_name;
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

        if (product_discount==null){
            return "0.0";
        }
        else
        {
            return product_discount.isEmpty() ? "0.0" : product_discount;
        }


    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }

    @Override
    public float getDisCountedAmount() {
        float totalAmount = Float.parseFloat(getDefault_price());
        float discountRate = Float.parseFloat(getProduct_discount());
        return totalAmount - (((discountRate * totalAmount) / 100));
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sizes = "+sizes+",measurements ="+measurements+", brand_image = "+brand_image+", sub_color = "+sub_color+", color = "+color+", product_name = "+product_name+", product_desc = "+product_desc+", product_images = "+product_images+", tefsal_product_id = "+tefsal_product_id+", attribute_id = "+attribute_id+", store_name = "+store_name+", brand_name = "+brand_name+",min_delivery_days ="+min_delivery_days+",max_delivery_days="+max_delivery_days+", product_discount="+product_discount+"]";
    }
}

