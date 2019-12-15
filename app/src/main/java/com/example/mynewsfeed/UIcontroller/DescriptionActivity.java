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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        headerTV = findViewById(R.id.desc_head_tv);
        descriptionTV = findViewById(R.id.desc_desc_tv);
        linkTV = findViewById(R.id.desc_link_tv);

        Intent intent = getIntent();
        headerTV.setText(intent.getStringExtra("title"));
        descriptionTV.setText(intent.getStringExtra("description"));
        linkTV.setText(intent.getStringExtra("link"));
    }
}
