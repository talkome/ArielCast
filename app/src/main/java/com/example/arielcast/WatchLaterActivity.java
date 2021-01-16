package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.example.arielcast.firebase.model.dataObject.Lecture;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WatchLaterActivity extends AppCompatActivity {
    RecyclerView watchLaterView;
    MyLecturesAdapter myLecturesAdapter;
    DatabaseReference DataRef;
    ArrayList<Lecture> lectures ;
    String email,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_later);

        watchLaterView = findViewById(R.id.watch_later_recycleView);
        watchLaterView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        watchLaterView.setHasFixedSize(true);

        DataRef = FirebaseDatabase.getInstance().getReference().child("WatchLaterLec");

        // get student's email from MainActivity
        Intent intent = getIntent();
        email = intent.getExtras().getString("Email");
        id = intent.getExtras().getString("ID");

        myLecturesAdapter = new MyLecturesAdapter(this, getMyLecturesList(), id);
        watchLaterView.setAdapter(myLecturesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logOut();
            return true;
        }
        if(item.getItemId() == R.id.myCourses) {
            Intent intent=new Intent(WatchLaterActivity.this,StudentCoursesList.class);
            intent.putExtra("ID",id);
            intent.putExtra("Email",email);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(WatchLaterActivity.this, LoginActivity.class));
    }

    private ArrayList<Lecture> getMyLecturesList(){
        lectures = new ArrayList<>();

        Query q = FirebaseDatabase.getInstance().getReference().child("WatchLaterLec")
                .orderByChild("studentID").equalTo(id);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String lectureID = data.child("lectureID").getValue(String.class);
                    DatabaseReference dr = FirebaseDatabase.getInstance().getReference()
                            .child("Lectures").child(lectureID);
                    dr.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Lecture lecture = snapshot.getValue(Lecture.class);
                            lectures.add(lecture);
                            myLecturesAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return lectures;
    }
}