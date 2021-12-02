package com.example.androiretrofit3.data.db.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androiretrofit3.data.network.dtos.episode.EpisodeModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAll(ArrayList<EpisodeModel> episodeList);

    @Query("SELECT * FROM episodeModel")
    List<EpisodeModel>  getAll();
}
