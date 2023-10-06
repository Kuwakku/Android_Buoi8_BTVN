package com.example.android_buoi8_btvn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    DummyServices dummyServices;
    public static final String BASE_URL = "https://dummyjson.com/";
    private RecyclerView rvProduct;
    private Retrofit mRetrofit;
    private ProductAdapter mProductAdapter;
    private List<Product> mListProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListProducts = new ArrayList<>();
        initData();
        initView();

    }

    private void initView() {
        rvProduct = findViewById(R.id.rvProduct);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvProduct.setLayoutManager(gridLayoutManager);
    }

    private void initData() {
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        dummyServices = mRetrofit.create(DummyServices.class);

        dummyServices.getProducts().enqueue(new Callback<ProductsRespondse>() {
            @Override
            public void onResponse(Call<ProductsRespondse> call, Response<ProductsRespondse> response) {
                if(response.isSuccessful()) {
                    if (response.code() == 200) {
                        ProductsRespondse productsRespondse = response.body();
                        mListProducts = productsRespondse.getProducts();
                        mProductAdapter = new ProductAdapter(mListProducts);
                        rvProduct.setAdapter(mProductAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductsRespondse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Faiure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}