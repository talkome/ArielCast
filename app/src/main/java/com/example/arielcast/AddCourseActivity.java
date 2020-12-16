package com.example.arielcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arielcast.firebase.model.dataObject.CourseObj;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Random;

public class AddCourseActivity extends AppCompatActivity {
    EditText courseName,semester,year,credits;
    Button button;
    ImageButton imageButton;
    ImageView imagelogo;
    TextView textlogo;
    DatabaseReference databaseReference;
    ProgressBar progressBar;

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
        progressBar=findViewById(R.id.progressBar2);

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                byte[] array = new byte[7]; // length is bounded by 7
                new Random().nextBytes(array);
                String generatedString = new String(array, Charset.forName("UTF-8")); // Course id

                CourseObj course=new CourseObj(courseName.getText().toString().trim(), lecId, semester.getText().toString().trim(),
                        year.getText().toString().trim(), credits.getText().toString().trim(),generatedString);
                FirebaseDatabase.getInstance().getReference().child("Courses").child(generatedString).setValue(course)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(AddCourseActivity.this,
                                    "your new course added successfully!",
                                    Toast.LENGTH_LONG).show();

                            // back to Main Screen - lecturer activity
                            Intent intent = new Intent(v.getContext(),LecturerActivity.class);
                            intent.putExtra("Email",email);
                            intent.putExtra("ID",lecId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AddCourseActivity.this,
                                    "Failed to add this course! try again",
                                    Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                });
            }
        });

    }
}
