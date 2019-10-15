package com.example.neardeal.ApiServices;

import com.example.neardeal.ApiResponse.CheckoutResponse;
import com.example.neardeal.ApiResponse.DealResponse;
import com.example.neardeal.ApiResponse.LoginResponse;
import com.example.neardeal.ApiResponse.ProductDetailResponse;
import com.example.neardeal.ApiResponse.ProductResponse;
import com.example.neardeal.ApiResponse.StoreResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiEndpoint {
    @GET("get_store.php")
    Call<StoreResponse> getStore();
    @GET("get_product.php")
    Call<ProductResponse> getProduct(@Query("store_id") String storeId);
    @GET("get_product_detail.php")
    Call<ProductDetailResponse> getProductDetail(@Query("product_id") String productId);
    @GET("get_deal.php")
    Call<DealResponse> getDeal(@Query("store_id") String storeId);
    @GET("get_store_by_loc.php")
    Call<StoreResponse> getStoreByLoc(@Query("lat") double lat, @Query("lng") double lng);
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);
    @FormUrlEncoded
    @POST("checkout.php")
    Call<CheckoutResponse> checkout(@Field("name") String name,
                                            @Field("no_hp") String noHp,
                                            @Field("address") String address,
                                            @Field("productIds") String productIds,
                                            @Field("prices") String prices,
                                            @Field("user_id") String user_id);
}
