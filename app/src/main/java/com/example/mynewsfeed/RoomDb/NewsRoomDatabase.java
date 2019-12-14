package com.example.mynewsfeed.RoomDb;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mynewsfeed.Parser.NetworkActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {News.class}, version = 1,exportSchema = false)
public abstract class NewsRoomDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();
    private static NewsRoomDatabase INSTANCE;

    public static NewsRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (NewsRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),NewsRoomDatabase.class, "news_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback) //bu kısmı tekrar editle
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // açılışta call back metodu
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    //db yi oluşturma
    private static class PopulateDbAsync extends AsyncTask<Void, Void,Void>{
        private final NewsDao mDao;

        //bilgiyi çektiğin yer

        PopulateDbAsync(NewsRoomDatabase db){
            mDao = db.newsDao();
        }
        @Override
        protected Void doInBackground(final Void... params){
            //açılışta ne yapacağın değişim kontrol sınıfları buraya gelmesi gerek
            mDao.deleteAll();
        return null;
        }

    }
}
