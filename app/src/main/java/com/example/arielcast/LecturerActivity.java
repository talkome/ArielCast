package com.example.arielcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.arielcast.firebase.model.dataObject.LectureObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LecturerActivity extends AppCompatActivity {

    DatabaseReference myRef;
    ListView lecturesListView;
    ArrayList<String> lecturesList = new ArrayList<>();

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

        //show my lectures
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(LecturerActivity.this, android.R.layout.simple_list_item_1, lecturesList);

        lecturesListView = findViewById(R.id.listView);
        lecturesListView.setAdapter(myArrayAdapter);

        myRef = FirebaseDatabase.getInstance().getReference().child("video");



                    Query myOrderPostsQuery = myRef.orderByChild("lecturerEmail").equalTo(email);

                    myOrderPostsQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                View v=new Button(getApplicationContext());
                                String value = data.getValue(LectureObj.class).getName();
                                lecturesList.add(value);
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
                    Intent i = new  Intent(LecturerActivity.this, AddLectureActivity.class);
                    i.putExtra("Email",email);
                    startActivity(i);
                }
            }
        });
    }
}