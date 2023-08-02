package com.example.cabzee;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Fragment fragment=new Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).commit();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = cm.getActiveNetworkInfo();
        if (activenetwork == null) {
            Intent intent = new Intent(HomeActivity.this, NoInternet.class);
            startActivity(intent);
            finish();
        }
        ImageView menubtn = findViewById(R.id.imageView2);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace With Your Own Action", Snackbar.LENGTH_LONG).
                        setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        //    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                drawer.close();
            }

            if (item.getItemId() == R.id.nav_settings) {
                Intent intent = new Intent(HomeActivity.this, user_profile_activity.class);
                startActivity(intent);
                finish();
                drawer.close();


            }
            if (item.getItemId() == R.id.nav_driver) {
                Toast.makeText(HomeActivity.this, "Switch To Driver", Toast.LENGTH_SHORT).show();
                drawer.close();

            }
            if (item.getItemId() == R.id.nav_history) {
                Toast.makeText(HomeActivity.this, "History", Toast.LENGTH_SHORT).show();
                drawer.close();

            }
            if (item.getItemId() == R.id.nav_SignOut) {
                Intent intent = new Intent(HomeActivity.this, Login_Activity.class);
                startActivity(intent);
                finish();
                drawer.close();


            }
            return true;
        });

        menubtn.setOnClickListener(v -> drawer.open());

        menubtn.setOnClickListener(v -> drawer.open());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_switch) {
            Toast.makeText(this, "Switch to driver", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.action_signout) {
            Intent intent = new Intent(HomeActivity.this, Login_Activity.class);
            startActivity(intent);
            finish();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private boolean isBackPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (isBackPressedOnce) {
            super.onBackPressed();
            return;
        }
        Toast.makeText(this, "Press again to exit !!", Toast.LENGTH_SHORT).show();
        isBackPressedOnce = true;
        new Handler().postDelayed(() -> isBackPressedOnce = false, 2000);
    }
}

