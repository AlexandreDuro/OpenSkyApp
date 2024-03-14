package com.example.opensky.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Plane plane);

    @Query("SELECT * FROM plane")
    LiveData<List<Plane>> getAllPlanes();
}
