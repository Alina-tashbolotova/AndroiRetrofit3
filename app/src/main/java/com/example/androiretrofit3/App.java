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

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {

}
