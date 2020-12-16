package com.example.arielcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.arielcast.firebase.model.dataObject.CourseObj;
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
    ListView coursesListView;
    ArrayList<String> coursesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ListView lv = findViewById(R.id.listView);
        setSupportActionBar(toolbar);

        // get lecturer's email from MainActivity
        Intent intent = getIntent();
        String email = intent.getExtras().getString("Email");
        String lecId=intent.getExtras().getString("ID");

        //show my courses
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(LecturerActivity.this, android.R.layout.simple_list_item_1, coursesList);

        coursesListView = findViewById(R.id.listView);
        coursesListView.setAdapter(myArrayAdapter);
        coursesList.add("");
        myArrayAdapter.notifyDataSetChanged();
        

        myRef = FirebaseDatabase.getInstance().getReference().child("Courses");

                    Query myOrderPostsQuery = myRef.orderByChild("lecturerId").equalTo(lecId);

                    myOrderPostsQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                String value = data.child("courseName").getValue(String.class);
                                coursesList.add(value);
                                myArrayAdapter.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });





        // add lecture Button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.fab) {

                    // save lecturer's email and start AddLectureActivity
                    Intent i = new  Intent(LecturerActivity.this,AddCourseActivity.class);
                    i.putExtra("Email",email);
                    i.putExtra("ID",lecId);
                    startActivity(i);
                }
            }
        });
    }
}