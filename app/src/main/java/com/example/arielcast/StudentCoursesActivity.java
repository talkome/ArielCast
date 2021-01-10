package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.example.arielcast.firebase.model.dataObject.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentCoursesActivity extends AppCompatActivity {
    DatabaseReference dataRef;
    String cId , lecturerId,userId;
    ListView studentsListView;
    ArrayList<String> studentsList = new ArrayList<>();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses);



        Intent intent = getIntent();
        cId=intent.getExtras().getString("CourseId");
        lecturerId=intent.getExtras().getString("lecID");
        userId=intent.getExtras().getString("ID");

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(StudentCoursesActivity.this, android.R.layout.simple_list_item_1, studentsList);

        studentsListView = findViewById(R.id.listView);
        textView=findViewById(R.id.textView2);

        Query query = FirebaseDatabase.getInstance().getReference().child("Courses").orderByChild("courseId").equalTo(cId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String courseName=data.child("courseName").getValue(String.class);
                   textView.setText(courseName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        studentsListView.setAdapter(myArrayAdapter);
        studentsList.add("");
        myArrayAdapter.notifyDataSetChanged();

        Query q = FirebaseDatabase.getInstance().getReference("StudentCourses").orderByChild("courseId").equalTo(cId);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot data:snapshot.getChildren()) {
                        String id = snapshot.child(data.getKey()).child("studentId").getValue(String.class);

                        dataRef = FirebaseDatabase.getInstance().getReference().child("Students").child(id);
                        dataRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String fullname= snapshot.child("fullname").getValue(String.class);
                                String email = snapshot.child("email").getValue(String.class);
                                studentsList.add(fullname + "  -  " + email);

                                myArrayAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}