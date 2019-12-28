package com.example.mynewsfeed.UIcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mynewsfeed.R;

public class DescriptionActivity extends AppCompatActivity {
    TextView headerTV;
    TextView descriptionTV;
    TextView linkTV;
    TextView pubDateTv;
    TextView creatorTv;
    TextView categoryTv;
    TextView typeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        headerTV = findViewById(R.id.desc_head_tv);
        descriptionTV = findViewById(R.id.desc_desc_tv);
        linkTV = findViewById(R.id.desc_link_tv);
        pubDateTv = findViewById(R.id.desc_pubdate_tv);
        creatorTv = findViewById(R.id.desc_creator_tv);
        categoryTv = findViewById(R.id.des_category_tv);
        typeTv = findViewById(R.id.des_type_tv);


        Intent intent = getIntent();
        headerTV.setText(intent.getStringExtra("title"));
        descriptionTV.setText(intent.getStringExtra("description"));
        linkTV.setText(intent.getStringExtra("link"));
        pubDateTv.setText(intent.getStringExtra("pubDate"));
        creatorTv.setText(intent.getStringExtra("creator"));
        categoryTv.setText(intent.getStringExtra("category"));
        typeTv.setText(intent.getStringExtra("type"));

    }
}
