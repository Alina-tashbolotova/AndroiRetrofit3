package com.example.androiretrofit3.data.network.apiservice;

import com.example.androiretrofit3.data.models.RickAndMortyResponse;
import com.example.androiretrofit3.data.models.location.LocationModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LocationApiService {


    @GET("/api/location")
    Call<RickAndMortyResponse<LocationModel>> fetchLocations(
            @Query("page") int page
    );

    @GET("/api/location/{id}")
    Call<LocationModel> fetchLocation(
            @Path("id") int id
    );
}
