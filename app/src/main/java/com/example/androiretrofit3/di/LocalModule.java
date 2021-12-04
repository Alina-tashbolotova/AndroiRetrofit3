package com.example.androiretrofit3.di;

import android.content.Context;

import com.example.androiretrofit3.data.db.AppDatabase;
import com.example.androiretrofit3.data.db.RoomClient;
import com.example.androiretrofit3.data.db.daos.CharacterDao;
import com.example.androiretrofit3.data.db.daos.EpisodeDao;
import com.example.androiretrofit3.data.db.daos.LocationDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LocalModule {

    @Singleton
    RoomClient roomClient = new RoomClient();

    @Singleton
    @Provides
    public AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return roomClient.provideDataBase(context);
    }

    @Singleton
    @Provides
    public CharacterDao provideCharacterDao(AppDatabase appDatabase) {
        return roomClient.provideCharacterDao(appDatabase);
    }

    @Singleton
    @Provides
    public EpisodeDao provideEpisodeDao(AppDatabase appDatabase) {
        return roomClient.provideEpisodeDao(appDatabase);
    }

    @Singleton
    @Provides
    public LocationDao provideLocationDao(AppDatabase appDatabase) {
        return roomClient.provideLocationDao(appDatabase);
    }
}
