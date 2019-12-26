package com.example.mynewsfeed.RoomDb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "build_table")
public class Build {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Btitle")
    private String Btitle;

    @ColumnInfo(name = "lBuildDate")
    private String lBuildDate;

    public Build(@NonNull String Btitle, String lBuildDate) {
        this.Btitle = Btitle;
        this.lBuildDate = lBuildDate;
    }

    @NonNull
    public String getBtitle() {
        return Btitle;
    }

    public String getLBuildDate() {
        return lBuildDate;
    }
}
