package com.example.androiretrofit3.data.network.apiservice;

import com.example.androiretrofit3.data.models.RickAndMortyResponse;
import com.example.androiretrofit3.data.models.episode.EpisodeModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EpisodeApiService {

    @GET("/api/episode")
    Call<RickAndMortyResponse<EpisodeModel>> fetchEpisodes(
            @Query("page") int page
    );

    @GET("/api/episode/{id}")
    Call<EpisodeModel> fetchEpisode(
            @Path("id") int id

    );
}
