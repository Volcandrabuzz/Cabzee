package com.example.cabzee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPageActivity extends AppCompatActivity {

    EditText email, password;
    Button logInBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    TextView gotoRegister,ForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.InputEmail);
        password = findViewById(R.id.inputPassword);
        logInBtn = findViewById(R.id.forgtBtn);
        progressBar = findViewById(R.id.progressbar);
        gotoRegister=findViewById(R.id.gotoRegister);
        ForgotPassword=findViewById(R.id.forgotPassword);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);


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
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String pass=password.getText().toString();
                progressBar.setVisibility(View.VISIBLE);

                if(!mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    if(!pass.isEmpty()){
                        mAuth.signInWithEmailAndPassword(mail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginPageActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginPageActivity.this,driverLocation.class));
                                progressBar.setVisibility(View.INVISIBLE);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginPageActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);


                            }
                        });
                    }else{
                        password.setError("Password can not be empty");
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                } else if (mail.isEmpty()) {
                    email.setError("Email can not be empty");
                    progressBar.setVisibility(View.INVISIBLE);


                }else{
                    email.setError("Please enter valid email");
                    progressBar.setVisibility(View.INVISIBLE);



                }
            }
        });
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPageActivity.this, com.example.cabzee.Activity.class));
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPageActivity.this, forgetPass.class));


            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}