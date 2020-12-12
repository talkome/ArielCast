package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    ListView studentListView;
    ArrayList<String> coursesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(StudentActivity.this, android.R.layout.simple_list_item_1, coursesList);

        studentListView = findViewById(R.id.student_listview);
        studentListView.setAdapter(myArrayAdapter);

        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Lecturers");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DatabaseReference mRef1 = mRef.child("courseName");
                String value = snapshot.getValue(String.class);
                Toast.makeText(StudentActivity.this, value, Toast.LENGTH_LONG).show();
                coursesList.add(value);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}