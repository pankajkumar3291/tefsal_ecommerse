package com.tefal.utils;

import com.google.gson.JsonObject;
import com.tefal.Models.AddressResponseModel;
import com.tefal.Models.AreaResponseModel;
import com.tefal.Models.CategorisResponseModel;
import com.tefal.Models.CountriesResponseModel;
import com.tefal.Models.MailResponseModel;
import com.tefal.Models.NormalResponseModel;
import com.tefal.Models.ProvincesResponseModel;
import com.tefal.Models.RegisterResponseModel;
import com.tefal.Models.StoreResponseModel;
import com.tefal.Models.TailorProductsResponseModel;
import com.tefal.Models.TailorStoresResponseModel;
import com.tefal.Models.TextileStoresResponseModel;
import com.tefal.Models.UserResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by new on 9/26/2017.
 */

public interface API_InterfaceTefsal {

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("customerRegister")
    Call<RegisterResponseModel> customerRegister(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("customerAddtInfo")
    Call<NormalResponseModel> customerAddtInfo(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("customerLogin")
    Call<RegisterResponseModel> customerLogin(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("forgotPassword")
    Call<NormalResponseModel> forgotPassword(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("changePassword")
    Call<NormalResponseModel> changePassword(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("deviceInfo")
    Call<NormalResponseModel> deviceInfo(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("getCustomerProfile")
    Call<UserResponseModel> getCustomerProfile(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("updateCustomerProfile")
    Call<NormalResponseModel> updateCustomerProfile(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("getCategories")
    Call<CategorisResponseModel> getCategories(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("getSubCategories")
    Call<CategorisResponseModel> getSubCategories(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("getCountries")
    Call<CountriesResponseModel> getCountries(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("getProvinces")
    Call<ProvincesResponseModel> getProvinces(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("getAreas")
    Call<AreaResponseModel> getAreas(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("customerSaveAddress")
    Call<NormalResponseModel> customerSaveAddress(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("getCustomerSavedAddresses")
    Call<AddressResponseModel> getCustomerSavedAddresses(@Field("body") JsonObject body);

    @Headers("App-User: customer_0")
    @FormUrlEncoded
    @POST("updateCustomerSavedAddresses")
    Call<NormalResponseModel> updateCustomerSavedAddresses(@Field("body") JsonObject body);






/*

    @POST("deleteCustomerSavedAddresses")
    Call<OfferResponseModel> deleteCustomerSavedAddresses(@Field("body") JsonObject body);

    @POST("customerSendMail")
    Call<OfferResponseModel> customerSendMail(@Field("body") JsonObject body);
*/






    @POST("customerGetMails")
    Call<MailResponseModel> customerGetMails(@Field("body") JsonObject body);

    @POST("getDishdashaStores")
    Call<StoreResponseModel> getDishdashaStores(@Field("body") JsonObject body);

    @POST("getDishdashaTextileStores")
    Call<TextileStoresResponseModel> getDishdashaTextileStores(@Field("body") JsonObject body);

    @POST("getDishdashaTailorStores")
    Call<TailorStoresResponseModel> getDishdashaTailorStores(@Field("body") JsonObject body);

    @POST("getDishdashaTailorProducts")
    Call<TailorProductsResponseModel> getDishdashaTailorProducts(@Field("body") JsonObject body);




/*    @POST("getDishdashaTextileProducts")
    Call<OfferResponseModel> getDishdashaTextileProducts(@Field("body") JsonObject body);

    @POST("getStores")
    Call<OfferResponseModel> getStores(@Field("body") JsonObject body);

    @POST("getAccessoriesSubCats")
    Call<OfferResponseModel> getAccessoriesSubCats(@Field("body") JsonObject body);*/



}
