package com.example.mynewsfeed.UIcontroller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mynewsfeed.R;
import com.example.mynewsfeed.ViewModel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListOfIgnoredSourceActivity extends AppCompatActivity {

    ArrayList<String> bannedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_ignored_source);
        Intent intent = getIntent();
        bannedList = intent.getStringArrayListExtra("list_of_banned");

        ListView listView = findViewById(R.id.ignored_source_listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,bannedList);
        listView.setAdapter(arrayAdapter);

    }
}
