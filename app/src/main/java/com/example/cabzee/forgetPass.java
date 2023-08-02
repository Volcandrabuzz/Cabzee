package com.example.cabzee;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPass extends AppCompatActivity {

    EditText email;
    Button submit;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.ForgetEmail);
        submit = findViewById(R.id.forgtBtn);
        progressBar = findViewById(R.id.progressbar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN );



        submit.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);

            if (email.getText().toString().isEmpty()) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(forgetPass.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(forgetPass.this, "Successfully send ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}