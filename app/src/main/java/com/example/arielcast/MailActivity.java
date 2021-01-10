package com.example.arielcast;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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




    }
}