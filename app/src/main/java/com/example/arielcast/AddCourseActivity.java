package com.example.arielcast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    EditText courseName,semester,year,credits;
    Button button;
    ImageButton imageButton;
    ImageView imagelogo;
    TextView textlogo;
    DatabaseReference databaseReference;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcourse);

        courseName=findViewById(R.id.courseName);
        semester=findViewById(R.id.semester);
        year=findViewById(R.id.year);
        credits=findViewById(R.id.credits);
        button=findViewById(R.id.button);
        textlogo=findViewById(R.id.viewlogo);
        imagelogo=findViewById(R.id.imagelogo);
        progressBar=findViewById(R.id.progressBar2);

        // get lecturer's email from MainActivity
        Intent intent = getIntent();
        String email = intent.getExtras().getString("Email");
        String lecId=intent.getExtras().getString("ID");

        imageButton=findViewById(R.id.imageButton);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCourseActivity.this, LecturerActivity.class);
                i.putExtra("Email",email);
                i.putExtra("ID",lecId);
                startActivity(i);
            }
        });

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
                                lec.addCourse(t,progressBar, v, courseName, semester, year, credits);
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
