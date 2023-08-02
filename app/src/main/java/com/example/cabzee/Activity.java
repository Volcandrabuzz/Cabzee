package com.example.cabzee;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity extends AppCompatActivity {
    EditText email, password, name;
    Button signupBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    TextView gotoregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();
        name=findViewById(R.id.InputName);
        email = findViewById(R.id.InputEmail);
        password = findViewById(R.id.inputPassword);
        signupBtn = findViewById(R.id.signupBtn);
        progressBar = findViewById(R.id.progressbar);
        gotoregister=findViewById(R.id.gotoRegister);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });



        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String user=name.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String mail=email.getText().toString().trim();
                if (user.isEmpty()){
                    name.setError("Name cannot be empty");
                    progressBar.setVisibility(View.INVISIBLE);

                } else if (pass.isEmpty()) {
                    password.setError("Password cannot be empty");
                    progressBar.setVisibility(View.INVISIBLE);

                } else if (mail.isEmpty()) {
                    email.setError("Email cannot be empty");
                    progressBar.setVisibility(View.INVISIBLE);

                }else{
                    mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Activity.this,"SignUp Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity.this,LoginPageActivity.class));
                                progressBar.setVisibility(View.INVISIBLE);


                            }else{
                                progressBar.setVisibility(View.INVISIBLE);
                                Snackbar.make(v, "SignUp Failed"+task.getException().getMessage(), Snackbar.LENGTH_LONG).
                                        setAction("Action", null).show();

                            }

                        }
                    });
                }

            }
        });
        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity.this,LoginPageActivity.class));
            }
        });


    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(android.app.Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }





    @Override
    public void onBackPressed() {
        finish();
        onDestroy();
        super.onBackPressed();
    }
}