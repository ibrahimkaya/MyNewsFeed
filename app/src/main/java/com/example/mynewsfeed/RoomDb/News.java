package com.example.mynewsfeed.RoomDb;

import com.example.mynewsfeed.Parser.NewsParser;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class News {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title_c")
    String title;

    @ColumnInfo(name = "description_c")
    String description;

    @ColumnInfo(name = "link_c")
    String link;

    @ColumnInfo(name = "pubDate_c")
    String pubDate;

    @ColumnInfo(name = "creator_c")
    String creator;

    @ColumnInfo(name = "category_C")
    String category ;

    @ColumnInfo(name = "valid_category_c")
    boolean isValidCategory = true;

    public News(@NonNull String title, String description, String link, String pubDate, String creator,String category){
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.creator = creator;
        this.category = category;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getCreator() {
        return creator;
    }

    public String getCategory() {
        return category;
    }

    public boolean isValidCategory() {
        return isValidCategory;
    }

}
