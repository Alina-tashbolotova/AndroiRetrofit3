package com.example.androiretrofit3;

import android.app.Application;

import com.example.androiretrofit3.data.db.RoomClient;
import com.example.androiretrofit3.data.db.daos.CharacterDao;
import com.example.androiretrofit3.data.db.daos.EpisodeDao;
import com.example.androiretrofit3.data.db.daos.LocationDao;
import com.example.androiretrofit3.data.network.RetrofitClient;
import com.example.androiretrofit3.data.network.apiservice.CharacterApiService;
import com.example.androiretrofit3.data.network.apiservice.EpisodeApiService;
import com.example.androiretrofit3.data.network.apiservice.LocationApiService;

public class App extends Application {

    public static CharacterApiService characterApiService;
    public static EpisodeApiService episodeApiService;
    public static LocationApiService locationApiService;
    public static CharacterDao characterDao;
    public static EpisodeDao episodeDao;
    public static LocationDao locationDao;

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitClient retrofitClient = new RetrofitClient();

        characterApiService = retrofitClient.provideCharacterApiService();
        episodeApiService = retrofitClient.provideEpisodeApiService();
        locationApiService = retrofitClient.provideLocationAPiService();

        RoomClient roomClient = new RoomClient();

        characterDao = roomClient.provideCharacterDao(roomClient.provideDataBase(this));
        episodeDao = roomClient.provideEpisodeDao(roomClient.provideDataBase(this));
        locationDao = roomClient.provideLocationDao(roomClient.provideDataBase(this));
    }
}
