package com.example.androiretrofit3.data.db;

import android.content.Context;

import androidx.room.Room;

import com.example.androiretrofit3.data.db.daos.CharacterDao;
import com.example.androiretrofit3.data.db.daos.EpisodeDao;
import com.example.androiretrofit3.data.db.daos.LocationDao;

public class RoomClient {

    public AppDatabase provideDataBase(Context context) {
        return Room
                .databaseBuilder(context, AppDatabase.class, "rick_and_morty")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() // для тестинга
                .build();
    }
    public CharacterDao provideCharacterDao(AppDatabase appDatabase){
       return appDatabase.characterDao();
    }
    public EpisodeDao provideEpisodeDao(AppDatabase appDatabase){
        return appDatabase.episodeDao();
    }
    public LocationDao provideLocationDao(AppDatabase appDatabase){
        return appDatabase.locationDao();
    }
}
