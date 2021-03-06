package com.example.mynewsfeed.RoomDb;


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

    @Query("SELECT * FROM news_table WHERE type = 'Sports' AND valid_category = 'true' ")
    LiveData<List<News>> getAllNewsSport ();

    @Query("SELECT * FROM news_table WHERE type = 'World' AND valid_category= 'true' ")
    LiveData<List<News>> getAllNewsWorld ();

    @Query("SELECT * FROM news_table WHERE type = 'Science' AND valid_category = 'true' ")
    LiveData<List<News>> getAllNewsScience ();

    @Query("SELECT valid_category FROM news_table WHERE title = :title")
    String getCategoryValidnes (String title);

    @Query("UPDATE news_table SET valid_category = 'false' WHERE category =:category")
    void setCategoryFalse ( String category);

    @Query("UPDATE news_table SET valid_category = 'true' WHERE category =:category")
    void setCategoryTrue ( String category);

    @Query("SELECT category FROM news_table WHERE valid_category = 'false'")
    LiveData<List<String>> getAllNonVaildCategory ();

    @Query("SELECT pubDate FROM news_table WHERE title =:title")
    String getPubDate(String title);

    @Query(("SELECT type FROM news_table WHERE title =:title"))
    String getType (String title);

}
