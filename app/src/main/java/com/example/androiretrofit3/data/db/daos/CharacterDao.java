package com.example.androiretrofit3.data.db.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androiretrofit3.data.network.dtos.character.CharacterModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // для замены одинаковых item
    void InsertAll(ArrayList<CharacterModel> characterList);

    @Query("SELECT * FROM charactermodel")
    List<CharacterModel> getAll();
}
