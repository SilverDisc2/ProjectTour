package com.example.project;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClass {
    private static final String BASE_URL="https://maps.googleapis.com/maps/api/place/";

    private  static Retrofit retrofit;

    public static Retrofit getRetrofitInstance(){


        if(retrofit==null){
            retrofit= new retrofit2.Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }




return  retrofit;
    }



}
