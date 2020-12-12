package com.example.arielcast;

import android.content.Intent;
import android.os.Bundle;

import com.example.arielcast.firebase.model.dataObject.LectureObj;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;

public class LecturerActivity extends AppCompatActivity {

DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ListView lv=findViewById(R.id.listView);
        setSupportActionBar(toolbar);

        // get lecturer's email from MainActivity
        Intent intent = getIntent();
        String email = intent.getExtras().getString("Email");

        //
        myRef = FirebaseDatabase.getInstance().getReference().child("video");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()) {
                    LectureObj p = data.getValue(LectureObj.class);
//                    lectures.add(p);
                }
//                allPostAdapter = new AllPostAdapter(AllPostActivity.this,0,0,posts);
//               lv.setAdapter(allPostAdapter);
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
                if(view.getId() == R.id.fab)
                {
                    // save lecturer's email and start AddLectureActivity
                    Intent i = new  Intent(LecturerActivity.this, AddLectureActivity.class);
                    i.putExtra("Email",email);
                    startActivity(i);
                }
            }
        });
    }
}