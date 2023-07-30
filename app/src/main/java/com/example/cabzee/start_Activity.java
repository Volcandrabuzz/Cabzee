package com.example.cabzee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class start_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void onDriverButtonClick(View view) {
        showToast("You are a Driver!");
        Intent driverintent=new Intent(start_Activity.this,DriverSplash.class);
        startActivity(driverintent);
        finish();



    }

    public void onPassengerButtonClick(View view) {
        showToast("You are a Passenger!");
        Intent passengerintent=new Intent(start_Activity.this,SplashActivity.class);
        startActivity(passengerintent);
        finish();

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
