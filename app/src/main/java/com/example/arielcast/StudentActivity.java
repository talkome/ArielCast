package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
//    ArrayList<String> coursesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        inputSearch = findViewById(R.id.inputSearch);
        studentListView = findViewById(R.id.student_listview);
        studentListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        studentListView.setHasFixedSize(true);

        DataRef = FirebaseDatabase.getInstance().getReference().child("Courses");

        myAdapter = new MyAdapter(this, getMyList());
        studentListView.setAdapter(myAdapter);

//        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(StudentActivity.this,
//                android.R.layout.simple_list_item_1, coursesList);
//        studentListView.setAdapter(myArrayAdapter);

        // get student's email from MainActivity
        Intent intent = getIntent();
        String email= intent.getExtras().getString("Email");
        Toast.makeText(StudentActivity.this,
                "Welcome "+email+" !",
                Toast.LENGTH_LONG).show();

        LoadData();
    }

    private void LoadData() {
        options = new FirebaseRecyclerOptions.Builder<Course>().
                setQuery(DataRef,Course.class).build();
        adapter = new FirebaseRecyclerAdapter<Course, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Course model) {
                holder.textView.setText(model.getCourseName());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StudentActivity.this, ShowCourse.class);
                        intent.putExtra("CarKey",getRef(position).getKey());
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
        ArrayList<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setCourseName("New Course");
        course.setDescription("Description");
        course.setImage(R.drawable.ariel_lec);
        courses.add(course);
        return courses;
    }
}
