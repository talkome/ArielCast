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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShowCourse extends AppCompatActivity {
    private ImageView imageView;
    TextView title, description;
    Button deleteButton;
    DatabaseReference ref;
    int position;
    String lecId,email,cID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_course);

        imageView = findViewById(R.id.lecture_image);
        title = findViewById(R.id.textViewMain);
        description = findViewById(R.id.textViewSub);
        deleteButton = findViewById(R.id.delete_button);
        ref = FirebaseDatabase.getInstance().getReference().child("Courses");

         email= getIntent().getExtras().getString("Email");
         lecId=getIntent().getExtras().getString("lecID");
         cID=getIntent().getExtras().getString("CourseId");

       // Toast.makeText(ShowCourse.this,"Course ID : "+cID+" !",Toast.LENGTH_LONG).show();

        ref.child(String.valueOf(cID)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String courseName = snapshot.child("courseName").getValue(String.class);
                    String lecturerID = snapshot.child("lecturerId").getValue(String.class);

                    // get lecturer name
                    DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("Lecturers").child(lecId);

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String lecName=snapshot.child("fullname").getValue(String.class);

                            description.setText(lecName);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    title.setText(courseName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

/*
        // get lectures list
        Query q=FirebaseDatabase.getInstance().getReference().child("Videos").orderByChild("lecturerId").equalTo(lecturerID);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    lecturername = data.child("fullname").getValue(String.class);
                    description.setText("Lecturer : "+lecturername);
                }
            });*/
    }
}