package com.example.mynewsfeed.RoomDb;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BuildDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (Build build);

    @Query("SELECT * FROM build_table")
    LiveData<List<Build>> getAllBuildFeature();
}
