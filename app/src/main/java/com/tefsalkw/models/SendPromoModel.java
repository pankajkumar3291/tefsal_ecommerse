package com.tefsalkw.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendPromoModel {

    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("items")
    @Expose
    private List<SendItemPromo> items = null;
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("appSecret")
    @Expose
    private String appSecret;
    @SerializedName("appUser")
    @Expose
    private String appUser;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<SendItemPromo> getItems() {
        return items;
    }

    public void setItems(List<SendItemPromo> items) {
        this.items = items;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

}