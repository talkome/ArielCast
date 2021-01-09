package com.example.arielcast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    DatabaseReference myRef;
    RecyclerView coursesListView;
    MyAdapter myAdapter;
    ArrayList<Course> courses ;
    Button logout;
    String email ,lecId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        myAdapter =new MyAdapter (this, getMyList(),lecId);

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
                    Intent i = new  Intent(MainActivity.this,AddCourseActivity.class);
                    i.putExtra("Email",email);
                    i.putExtra("ID",lecId);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout: {
                logOut();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
