package com.example.androiretrofit3.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androiretrofit3.data.db.daos.CharacterDao;
import com.example.androiretrofit3.data.db.daos.EpisodeDao;
import com.example.androiretrofit3.data.db.daos.LocationDao;
import com.example.androiretrofit3.data.network.dtos.character.CharacterModel;
import com.example.androiretrofit3.data.network.dtos.episode.EpisodeModel;
import com.example.androiretrofit3.data.network.dtos.location.LocationModel;

@Database(entities = {CharacterModel.class, EpisodeModel.class, LocationModel.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CharacterDao characterDao();
    public abstract EpisodeDao episodeDao();
    public abstract LocationDao locationDao();
}
