package com.example.arielcast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arielcast.firebase.model.dataObject.Course;
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

public class StudentCoursesList extends AppCompatActivity {
    RecyclerView studentListView;
    EditText inputSearch;
    MyAdapter myAdapter;
    FirebaseRecyclerOptions<Course> options;
    FirebaseRecyclerAdapter<Course,MyViewHolder> adapter;
    DatabaseReference DataRef;
    ArrayList<Course> courses ;
    String email,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentcourseslist);

        studentListView = findViewById(R.id.recycleView);
        studentListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        studentListView.setHasFixedSize(true);
        inputSearch = findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        DataRef = FirebaseDatabase.getInstance().getReference().child("Courses");

        // get student's email from MainActivity
        Intent intent = getIntent();
        email= intent.getExtras().getString("Email");
        id= intent.getExtras().getString("ID");

        myAdapter = new MyAdapter(this, getMyList(),id);
        studentListView.setAdapter(myAdapter);



        // LoadData();
    }

    private void filter(String text) {
        ArrayList<Course> filterList = new ArrayList<>();
        for (Course course: courses){
            if (course.getCourseName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(course);
            }
        }
        myAdapter.filterList(filterList);
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
        if(item.getItemId()==R.id.myCourses)
        {
            Intent intent=new Intent(StudentCoursesList.this,StudentCoursesList.class);
            intent.putExtra("ID",id);
            intent.putExtra("Email",email);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(StudentCoursesList.this, LoginActivity.class));
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

        Query q1 = FirebaseDatabase.getInstance().getReference().child("StudentCourses").orderByChild("studentId").equalTo(id);

        q1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {

                    String c1=data.child("courseId").getValue(String.class);
                    Query q = FirebaseDatabase.getInstance().getReference().child("Courses").orderByChild("courseId").equalTo(c1);

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return courses;
    }
}
