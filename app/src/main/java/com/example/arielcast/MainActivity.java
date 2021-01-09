package com.example.arielcast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView register = findViewById(R.id.register1);
        register.setOnClickListener(this);

        Button signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        TextView reset = findViewById(R.id.setNewPassword);
        reset.setOnClickListener(this);


        editTextEmail = findViewById(R.id.emailAddress);
        editTextPassword = findViewById(R.id.password1);

        mAuth = FirebaseAuth.getInstance();
    }

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register1)
            startActivity(new Intent(this, RegisterUser.class));

        if (v.getId() == R.id.signIn)
            userLogin();

        if(v.getId() == R.id.setNewPassword) {
            String email = editTextEmail.getText().toString().trim();
            if(email.isEmpty()) {
                editTextEmail.setError("email is required!");
                editTextEmail.requestFocus();
            } else
                resetPassword();
        }
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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //check if it's lecturer user or student user
                        Query query = myRef.child("Lecturers").orderByChild("email").equalTo(email);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for(DataSnapshot data:snapshot.getChildren()) {
                                        Intent intent = new Intent(MainActivity.this, LecturerActivity.class);
                                       String value = data.child("lecturerId").getValue(String.class);
                                        intent.putExtra("Email", email);
                                        intent.putExtra("ID", value);
                                        startActivity(intent);
                                    }
                                } else {
                                    Query query = myRef.child("Students").orderByChild("email").equalTo(email);
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                for(DataSnapshot data:snapshot.getChildren()) {
                                                    Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                                                    String value = data.getKey();
                                                    intent.putExtra("Email", email);
                                                    intent.putExtra("ID", value);
                                                    startActivity(intent);
                                                }
                                            } else {
                                                editTextEmail.setError("Please provide valid email");
                                                editTextEmail.requestFocus();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            throw error.toException(); // don't ignore errors
                                        }
                                    });
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                throw error.toException(); // don't ignore errors
                            }
                        });

                } else {
                    Toast.makeText(MainActivity.this,
                            "Fail to login, please check your credentials",
                            Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    // reset password  if user forget password
    private void resetPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = editTextEmail.getText().toString();

        if(editTextEmail==null) {
            Toast toast = Toast.makeText(getApplicationContext(), "Enter your email", Toast.LENGTH_SHORT);
            ViewGroup group = (ViewGroup) toast.getView();
            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setTextSize(24);
            toast.show();
        } else {
            auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Email sent. Check Your Email", Toast.LENGTH_SHORT);
                        ViewGroup group = (ViewGroup) toast.getView();
                        TextView messageTextView = (TextView) group.getChildAt(0);
                        messageTextView.setTextSize(24);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Email not sent. Check Your Email", Toast.LENGTH_SHORT);
                        ViewGroup group = (ViewGroup) toast.getView();
                        TextView messageTextView = (TextView) group.getChildAt(0);
                        messageTextView.setTextSize(24);
                        toast.show();
                    }
                }
            });
        }
    }
}
