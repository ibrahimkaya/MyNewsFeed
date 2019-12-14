package com.example.mynewsfeed.RoomDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {News.class}, version = 1,exportSchema = false)
public abstract class NewsRoomDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();
    private static NewsRoomDatabase INSTANCE;

    static NewsRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (NewsRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),NewsRoomDatabase.class, "news_database")
                            .fallbackToDestructiveMigration()
                           // .addCallback(sRoomDatabaseCallBack) //bu kısmı tekrar editle
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
