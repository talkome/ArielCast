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

                            String fromEmail = "castariel01@gmail.com";
                            String fromPassword = "cast123456";
                            String toEmails = "hodaya_s@hotmail.com";
                            List<String> toEmailList = Arrays.asList(toEmails
                                    .split("\\s*,\\s*"));

                            String emailSubject =title.toString();
                            String emailBody = content.toString();

                            try {
                                GMailSender sender = new GMailSender("castariel01@gmail.com", "cast123456");
                                sender.sendMail(title.toString(),
                                        content.toString(),
                                        "castariel01@gmail.com",
                                        "hodaya_s@hotmal.com");
                                Toast.makeText(MailActivity.this,
                                        "email sent !",
                                        Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(MailActivity.this,
                                        "email not sent !",
                                        Toast.LENGTH_LONG).show();
                            }

//                            Intent emailintent = new Intent(Intent.ACTION_SEND);
//                            emailintent.setType("message/rfc822");
//                            emailintent.putExtra(Intent.EXTRA_EMAIL, new String[] { "hodaya_s@hotmail.com" });
//                            emailintent.putExtra(Intent.EXTRA_SUBJECT, title.toString());
//                            emailintent.putExtra(Intent.EXTRA_TEXT, content.toString());
//                            try {
//                                startActivity(Intent.createChooser(emailintent, title.toString()));
//                                Toast.makeText(MailActivity.this,
//                                        "email sent !",
//                                        Toast.LENGTH_LONG).show();
//                            } catch (android.content.ActivityNotFoundException ex) {
//                                Toast.makeText(MailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                            }

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

                }
            }
        });



    }
}