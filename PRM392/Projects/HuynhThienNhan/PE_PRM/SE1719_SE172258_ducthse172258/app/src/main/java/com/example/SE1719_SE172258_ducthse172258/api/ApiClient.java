package com.example.SE1719_SE172258_ducthse172258.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    private static final String BASE_URl = "https://6691604326c2a69f6e8f9167.mockapi.io/api/v1/";
    private static final String BASE_URl = "https://6721a7e898bbb4d93ca9281e.mockapi.io/api/v1/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new UnixTimestampDateAdapter())
                    .create();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
