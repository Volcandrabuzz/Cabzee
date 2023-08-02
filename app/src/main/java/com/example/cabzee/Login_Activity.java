package com.example.cabzee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {
    Button phonebtn ,googlebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        phonebtn=findViewById(R.id.Phone);
        googlebtn=findViewById(R.id.google);


        phonebtn.setOnClickListener(view -> {
            Intent intent=new Intent(Login_Activity.this,phoneLogin.class);
            startActivity(intent);
            finish();

        });


    }

    @Override
    protected  void onStart(){
        super.onStart();
        FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentuser!=null){
            startActivity(new Intent(Login_Activity.this,HomeActivity.class));
            finish();
        }
    }
}