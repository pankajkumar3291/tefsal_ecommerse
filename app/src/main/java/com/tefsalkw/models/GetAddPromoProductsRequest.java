package com.tefsalkw.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAddPromoProductsRequest {

    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("appSecret")
    @Expose
    private String appSecret;
    @SerializedName("appUser")
    @Expose
    private String appUser;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("promo_id")
    @Expose
    private String promoId;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

}
