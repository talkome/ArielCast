package com.example.arielcast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button login,register;
    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.btblogin);
        register = (Button) findViewById(R.id.btnregister);
        welcome = (TextView)findViewById(R.id.txtwelcome);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v == login){
            welcome.setText("hello user");
        }
        if (v == register){
            welcome.setText("sign in");
        }

    }
}