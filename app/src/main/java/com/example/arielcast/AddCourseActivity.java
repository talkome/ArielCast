package com.example.arielcast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.arielcast.firebase.model.dataObject.Lecturer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddCourseActivity extends AppCompatActivity {
    EditText courseName,startDate,endDate,image;
    Button button;
    ImageView imagelogo;
    TextView textlogo;
    DatabaseReference databaseReference;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcourse);

        courseName=findViewById(R.id.courseName);
        startDate=findViewById(R.id.startDate);
        endDate=findViewById(R.id.endDate);
        image=findViewById(R.id.image);
        button=findViewById(R.id.button);
        textlogo=findViewById(R.id.viewlogo);
        imagelogo=findViewById(R.id.imagelogo);
        progressBar=findViewById(R.id.progressBar2);

        // get lecturer's email from MainActivity
        Intent intent = getIntent();
        String email = intent.getExtras().getString("Email");
        String lecId=intent.getExtras().getString("ID");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
               DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Lecturers");

                Query myOrderPostsQuery = myRef.orderByChild("lecturerId").equalTo(lecId);

                myOrderPostsQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            if(data.getKey().equals(lecId)) {
                                Lecturer lec = (data.getValue(Lecturer.class));
                                Context t=AddCourseActivity.this;
                                lec.addCourse(t,progressBar, v, courseName, startDate, endDate, image);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }


}
