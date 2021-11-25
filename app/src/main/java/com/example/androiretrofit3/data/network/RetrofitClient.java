package com.example.androiretrofit3.data.network;

import com.example.androiretrofit3.data.network.apiservice.CharacterApiService;
import com.example.androiretrofit3.data.network.apiservice.EpisodeApiService;
import com.example.androiretrofit3.data.network.apiservice.LocationApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private final OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .addInterceptor(provideLoggingInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    private final Retrofit provideRetrofit = new Retrofit
            .Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    private HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public CharacterApiService provideCharacterApiService() {
        return provideRetrofit.create(CharacterApiService.class);
    }

    public EpisodeApiService provideEpisodeApiService() {
        return provideRetrofit.create(EpisodeApiService.class);
    }

    public LocationApiService provideLocationAPiService() {
        return provideRetrofit.create(LocationApiService.class);
    }
}
