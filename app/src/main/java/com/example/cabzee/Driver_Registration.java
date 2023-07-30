package com.example.cabzee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Driver_Registration extends AppCompatActivity {
    Button login;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);

        login.setOnClickListener(v -> {
            Intent loginintent=new Intent(Driver_Registration.this,LoginPageActivity.class);
            startActivity(loginintent);
            finish();
        });

        signup.setOnClickListener(v -> {
            Intent signintent=new Intent(Driver_Registration.this,Activity.class);
            startActivity(signintent);
            finish();

        });
    }
}