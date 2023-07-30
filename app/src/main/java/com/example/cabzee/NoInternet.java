package com.example.cabzee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class NoInternet extends AppCompatActivity {

    private Button retry;
    private ImageView interimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        retry=(Button) findViewById(R.id.nointernet);
        interimage=findViewById(R.id.no_image);
        Glide.with(this).load(R.drawable.internet).into(interimage);
        retry.setOnClickListener(v -> {
            ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activenetwork=cm.getActiveNetworkInfo();
            if(activenetwork !=null){
                Intent intent=new Intent(NoInternet.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }
}