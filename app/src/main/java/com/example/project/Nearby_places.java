package com.example.project;

import com.example.project.NearByPlaces.NearbyPlacesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Nearby_places {

    @GET
    Call<NearbyPlacesResponse> getNearByData(@Url String url);
}
