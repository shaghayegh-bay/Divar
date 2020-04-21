package com.example.getdatausingretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
        String BASE_URL = "http://androidpro.kimiawebsite.ir/retrofit/";
        @GET("/get_data.php")
        Call<Model> getStatus();

}
