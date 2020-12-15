package com.example.arielcast;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.arielcast.firebase.model.dataObject.CourseObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.app.AppCompatActivity;

public class AddCourseActivity extends AppCompatActivity {
    EditText courseName,semester,year,credits;
    Button button;
    ImageButton imageButton;
    ImageView imagelogo;
    TextView textlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcourse);

        courseName=findViewById(R.id.courseName);
        semester=findViewById(R.id.semester);
        year=findViewById(R.id.year);
        credits=findViewById(R.id.credits);
        button=findViewById(R.id.button);
        textlogo=findViewById(R.id.viewlogo);
        imagelogo=findViewById(R.id.imagelogo);

        // get lecturer's email from MainActivity
        Intent intent = getIntent();
        String email = intent.getExtras().getString("Email");
        String lecId=intent.getExtras().getString("ID");

        imageButton=findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddCourseActivity.this, LecturerActivity.class);
                i.putExtra("Email",email);
                i.putExtra("ID",lecId);
                startActivity(i);
            }
        });


    }
}
