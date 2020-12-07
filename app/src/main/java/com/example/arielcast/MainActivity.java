package com.example.arielcast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arielcast.firebase.model.FirebaseBaseModel;
import com.example.arielcast.firebase.model.FirebaseDBLecturers;
import com.example.arielcast.firebase.model.dataObject.LecturerObj;
import com.example.arielcast.firebase.model.dataObject.StudentObj;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private CheckBox checkBoxL;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    //DatabaseReference mConditionRef=myRef.child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView register = findViewById(R.id.register1);
        register.setOnClickListener(this);

        Button signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = findViewById(R.id.emailAddress);
        editTextPassword = findViewById(R.id.password1);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register1) { startActivity(new Intent(this, RegisterUser.class)); }
        if (v.getId() == R.id.signIn) { userLogin(); }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("min password length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }


        // progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, StudentActivity.class));
                    Toast toast = Toast.makeText(MainActivity.this,
                            "Welcome!",
                            Toast.LENGTH_LONG);

                    ViewGroup group = (ViewGroup) toast.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(30);
                    messageTextView.setTextColor(Color.rgb(0, 200, 150));
                    toast.show();

                } else {
                    Toast.makeText(MainActivity.this,
                            "Fail to login, please check your credentials",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
/*
// add this on RegisterUser
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    assert user != null;
                    if (user.isEmailVerified()) {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,
                                "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(MainActivity.this,
                            "Failed to login! try again", Toast.LENGTH_LONG).show();
                }

            }
        });
*/
    }

}