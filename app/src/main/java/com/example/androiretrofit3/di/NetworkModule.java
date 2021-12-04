package com.example.androiretrofit3.di;

import com.example.androiretrofit3.data.network.RetrofitClient;
import com.example.androiretrofit3.data.network.apiservice.CharacterApiService;
import com.example.androiretrofit3.data.network.apiservice.EpisodeApiService;
import com.example.androiretrofit3.data.network.apiservice.LocationApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Singleton
    RetrofitClient retrofitClient = new RetrofitClient();

    @Singleton
    @Provides
    public CharacterApiService provideCharacterApiService() {
        return  retrofitClient.provideCharacterApiService();
    }

    @Singleton
    @Provides
    public EpisodeApiService provideEpisodeApiService(){
        return retrofitClient.provideEpisodeApiService();
    }

    @Singleton
    @Provides
    public LocationApiService provideLocationApiService(){
        return retrofitClient.provideLocationAPiService();
    }


}
