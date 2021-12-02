package com.example.androiretrofit3.data.db.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androiretrofit3.data.network.dtos.location.LocationModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAll(ArrayList<LocationModel> locationList);

    @Query("SELECT * FROM locationmodel")
    List<LocationModel> getAll();
}
