package com.example.android_buoi8_btvn;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DummyServices {

    @GET("products")
    Call<ProductsRespondse> getProducts();
}
