package com.tefsalkw.models;

/**
 * Created by Dell on 03/16/2018.
 */

public class PromoCodeRecordsModel {
    private String created_by;

    private String end_date;

    private String category;

    private String updated_at;

    private String promo_id;

    private String amount_spent;

    private String status;

    private String voucher_amount;

    private String deleted_at;

    private String created_at;

    private String voucher_amount_valid;

    private String start_date;

    private String promo_name;

    public String getCreated_by ()
    {
        return created_by;
    }

    public void setCreated_by (String created_by)
    {
        this.created_by = created_by;
    }

    public String getEnd_date ()
    {
        return end_date;
    }

    public void setEnd_date (String end_date)
    {
        this.end_date = end_date;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getPromo_id ()
    {
        return promo_id;
    }

    public void setPromo_id (String promo_id)
    {
        this.promo_id = promo_id;
    }

    public String getAmount_spent ()
    {
        return amount_spent;
    }

    public void setAmount_spent (String amount_spent)
    {
        this.amount_spent = amount_spent;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getVoucher_amount ()
    {
        return voucher_amount;
    }

    public void setVoucher_amount (String voucher_amount)
    {
        this.voucher_amount = voucher_amount;
    }

    public String getDeleted_at ()
    {
        return deleted_at;
    }

    public void setDeleted_at (String deleted_at)
    {
        this.deleted_at = deleted_at;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getVoucher_amount_valid ()
    {
        return voucher_amount_valid;
    }

    public void setVoucher_amount_valid (String voucher_amount_valid)
    {
        this.voucher_amount_valid = voucher_amount_valid;
    }

    public String getStart_date ()
    {
        return start_date;
    }

    public void setStart_date (String start_date)
    {
        this.start_date = start_date;
    }

    public String getPromo_name ()
    {
        return promo_name;
    }

    public void setPromo_name (String promo_name)
    {
        this.promo_name = promo_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [created_by = "+created_by+", end_date = "+end_date+", category = "+category+", updated_at = "+updated_at+", promo_id = "+promo_id+", amount_spent = "+amount_spent+", status = "+status+", voucher_amount = "+voucher_amount+", deleted_at = "+deleted_at+", created_at = "+created_at+", voucher_amount_valid = "+voucher_amount_valid+", start_date = "+start_date+", promo_name = "+promo_name+"]";
    }
}
