package com.example.mynewsfeed.UIcontroller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mynewsfeed.R;
import com.example.mynewsfeed.ViewModel.NewsViewModel;

import java.util.List;

public class ListOfIgnoredSourceActivity extends AppCompatActivity {
    ListView mListView;
    NewsViewModel mNewsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_ignored_source);
      //yetiştiremedim

      //  mListView = findViewById(R.id.ignored_source_listView);
      //  mNewsViewModel.getAllNonValidCategory().observe(this, new Observer<List<String>>() {
      //      @Override
      //      public void onChanged(List<String> strings) {
      //          ArrayAdapter adapter = new ArrayAdapter<List<String>>(android.R.layout.simple_list_item_1, android.R.id.text1, strings.get(1).toString());
      //          mListView.setAdapter(adapter);
//
      //      }
      //  });
    }
}
