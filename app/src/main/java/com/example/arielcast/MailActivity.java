package com.example.arielcast;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MailActivity extends AppCompatActivity {

    TextView textView;
    EditText title;
    TextInputEditText content;
    ImageButton imageButton , backButton;
    String cId , lecturerId,userId,userKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        textView=findViewById(R.id.textView1);
        title=findViewById(R.id.editTextTextPersonName);
        content=findViewById(R.id.textInputEditText);
        imageButton=findViewById(R.id.imageButton);
        backButton=findViewById(R.id.imageButton2);

        Intent intent = getIntent();
        cId=intent.getExtras().getString("CourseId");
        lecturerId=intent.getExtras().getString("lecID");
        userId=intent.getExtras().getString("ID");
        userKind=intent.getExtras().getString("userKind");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userKind.equals("student"))
                {
                    Intent intent = new Intent(MailActivity.this, StudentActivity.class);
                    intent.putExtra("ID", userId);
                    startActivity(intent);
                }
                else if(userKind.equals("lecturer"))
                {
                    Intent intent = new Intent(MailActivity.this, MainActivity.class);
                    intent.putExtra("ID",userId);
                    startActivity(intent);
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userKind.equals("student"))
                {
                    // get lecturer email by lecId
                    Query query = FirebaseDatabase.getInstance().getReference().child("Lecturers").child(lecturerId);

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String email = snapshot.child("email").getValue(String.class);

                            try {
                                GMailSender sender = new GMailSender("castariel01@gmail.com", "cast123456");
                                sender.sendMail(title.getText().toString().trim(),
                                        content.getText().toString().trim(),
                                        "castariel01@gmail.com",
                                        email);

                                Toast.makeText(MailActivity.this,
                                        "email sent !",
                                        Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                Toast.makeText(MailActivity.this,
                                        "email not sent !",
                                        Toast.LENGTH_LONG).show();
                            }

                        }

                            @Override
                            public void onCancelled (@NonNull DatabaseError error){

                            }
                });
                }
                else if(userKind.equals("lecturer"))
                {
                    // get list of followers (students) of this course
                    // and find their email - send massage all followers

                    // get lecturer email by lecId
                    Query query = FirebaseDatabase.getInstance().getReference().child("Courses").child(cId);

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String courseId = snapshot.child("courseId").getValue(String.class);

                            //find students following
                            Query quS = FirebaseDatabase.getInstance().getReference().child("StudentCourses").orderByChild("courseId").equalTo(cId);
                            quS.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot data:snapshot.getChildren())
                                    {
                                        String studenti=data.child("studentId").getValue(String.class);
                                        Query qs=FirebaseDatabase.getInstance().getReference().child("Students").child(studenti);
                                        qs.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String stuemail=snapshot.child("email").getValue(String.class);
                                                try {
                                                    GMailSender sender = new GMailSender("castariel01@gmail.com", "cast123456");
                                                    sender.sendMail(title.getText().toString().trim(),
                                                            content.getText().toString().trim(),
                                                            "castariel01@gmail.com",
                                                            stuemail);

                                                    Toast.makeText(MailActivity.this,
                                                            "email sent !",
                                                            Toast.LENGTH_LONG).show();

                                                } catch (Exception e) {
                                                    Toast.makeText(MailActivity.this,
                                                            "email not sent !",
                                                            Toast.LENGTH_LONG).show();
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


                        }

                        @Override
                        public void onCancelled (@NonNull DatabaseError error){

                        }
                    });

                }
            }
        });



    }
}