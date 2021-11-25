package com.example.androiretrofit3.data.network.apiservice;

import com.example.androiretrofit3.data.models.RickAndMortyResponse;
import com.example.androiretrofit3.data.models.character.CharacterModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CharacterApiService {

    @GET("/api/character/")
    Call<RickAndMortyResponse<CharacterModel>> fetchCharacters(
            @Query("page") int page
    );

    @GET("/api/character/{id}")
    Call<CharacterModel> fetchCharacter(
            @Path("id") int id

    );
}
