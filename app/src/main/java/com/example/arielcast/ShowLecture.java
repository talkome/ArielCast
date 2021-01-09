package com.example.arielcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ShowLecture extends AppCompatActivity {
    String lectureID;
    EditText lecture_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lecture);

        Intent i=getIntent();
        lecture_name=findViewById(R.id.et_video_name);
        String name=i.getExtras().getString("lecID");
        lecture_name.setText(name);


    }
}