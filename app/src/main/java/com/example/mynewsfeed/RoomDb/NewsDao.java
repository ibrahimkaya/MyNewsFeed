package com.example.mynewsfeed.RoomDb;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(News news);

    @Query("DELETE FROM news_table")
    void deleteAll();

    @Query("DELETE FROM news_table WHERE title  = :title")
    void deleteWhereTitle (String title);

    @Query("SELECT * FROM news_table")
    LiveData<List<News>> getAllNews();

    @Query("SELECT valid_category FROM news_table WHERE title = :title")
    boolean getCategoryValidnes (String title);

    @Query("UPDATE news_table SET valid_category = :categoryValidnes WHERE category =:category")
    void setCategoryValidnes(String categoryValidnes, String category);

    @Query("SELECT category FROM news_table WHERE valid_category = 'false'")
    LiveData<List<String>> getAllNonVaildCategory ();

    @Query("SELECT pubDate FROM news_table WHERE title =:title")
    String getPubDate(String title);
}
