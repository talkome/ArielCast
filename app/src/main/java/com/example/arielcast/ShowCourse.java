package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowCourse extends AppCompatActivity {
    private ImageView imageView;
    TextView title, description;
    Button deleteButton;
    DatabaseReference ref;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_course);

        imageView = findViewById(R.id.lecture_image);
        title = findViewById(R.id.textViewMain);
        description = findViewById(R.id.textViewSub);
        deleteButton = findViewById(R.id.delete_button);
        ref = FirebaseDatabase.getInstance().getReference().child("Courses");

        String email= getIntent().getExtras().getString("Email");
        Toast.makeText(ShowCourse.this,
                "Welcome "+email+" !",
                Toast.LENGTH_LONG).show();

        ref.child("Email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String courseName = snapshot.child("courseName").getKey();
                    String lecturerID = snapshot.child("lecturerId").getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}