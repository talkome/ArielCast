package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    RecyclerView studentListView;
    EditText inputSearch;
    MyAdapter myAdapter;
    FirebaseRecyclerOptions<Course> options;
    FirebaseRecyclerAdapter<Course,MyViewHolder> adapter;
    DatabaseReference DataRef;
    ArrayList<Course> courses ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        inputSearch = findViewById(R.id.inputSearch);
        studentListView = findViewById(R.id.recycleView);
        studentListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        studentListView.setHasFixedSize(true);

        DataRef = FirebaseDatabase.getInstance().getReference().child("Courses");

        myAdapter = new MyAdapter(this, getMyList());
        studentListView.setAdapter(myAdapter);


        // get student's email from MainActivity
        Intent intent = getIntent();
        String email= intent.getExtras().getString("Email");

        // LoadData();
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
        startActivity(new Intent(StudentActivity.this, LoginActivity.class));
    }

    private void LoadData() {
       options = new FirebaseRecyclerOptions.Builder<Course>().
               setQuery(DataRef,Course.class).build();


        adapter = new FirebaseRecyclerAdapter<Course, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Course model) {
                holder.textView.setText(model.getCourseName());
                holder.imageView.setImageURI(Uri.parse(model.getImage()));
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(),ShowCourse.class);
                        intent.putExtra("CourseId",model.getCourseId());
                        startActivity(intent);
                    }
                });


            }


            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.single_course,parent,false);
                return new MyViewHolder(view);
            }
        };
    }

    private ArrayList<Course> getMyList(){
        courses = new ArrayList<>();

               Query q = FirebaseDatabase.getInstance().getReference().child("Courses");


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