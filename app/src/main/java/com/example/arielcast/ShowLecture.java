package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowLecture extends AppCompatActivity {
    String lectureID;
    String videoPath;
    EditText lecture_name;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lecture);

        lecture_name = findViewById(R.id.et_video_name);
        lectureID = getIntent().getExtras().getString("lecID");
        lecture_name.setText(lectureID);

        dataRef = FirebaseDatabase.getInstance().getReference().child("Lectures").child(lectureID);

        VideoView videoView = findViewById(R.id.lecture_view);
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot data : snapshot.getChildren()) {
                    videoPath = snapshot.child("video_url").getValue(String.class);
                    Uri uri = Uri.parse(videoPath);
                    videoView.setVideoURI(uri);
                    videoView.requestFocus();
                    videoView.start();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

    }
}