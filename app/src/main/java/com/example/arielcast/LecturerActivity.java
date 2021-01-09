package com.example.arielcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LecturerActivity extends AppCompatActivity {

    DatabaseReference myRef;
   RecyclerView coursesListView;
    // ArrayList<String> coursesList = new ArrayList<>();
    MyAdapter myAdapter;
    ArrayList<Course> courses ;
    String email ,lecId;
    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        coursesListView = findViewById(R.id.recycleView);
        coursesListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        coursesListView.setHasFixedSize(true);

        setSupportActionBar(toolbar);

        // get lecturer's email from MainActivity
        Intent intent = getIntent();
         email = intent.getExtras().getString("Email");
         lecId = intent.getExtras().getString("ID");

        //show my courses
        UserID=lecId;
        myAdapter = new MyAdapter(this, getMyList(),UserID);

        coursesListView = findViewById(R.id.recycleView);
        coursesListView.setAdapter(myAdapter);

        myAdapter.notifyDataSetChanged();


        // add lecture Button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.fab) {

                    // save lecturer's email and start AddLectureActivity
                    Intent i = new  Intent(LecturerActivity.this,AddCourseActivity.class);
                    i.putExtra("Email",email);
                    i.putExtra("ID",UserID);
                    startActivity(i);
                }
            }
        });
    }


    private ArrayList<Course> getMyList(){
        courses = new ArrayList<>();

        Query q = FirebaseDatabase.getInstance().getReference().child("Courses").child("").orderByChild("lecturerId").equalTo(lecId);


        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Course c = data.getValue(Course.class);
                    courses.add(c);
                    myAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return courses;
    }
}