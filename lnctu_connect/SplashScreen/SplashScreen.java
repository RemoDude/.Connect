package com.example.lnctu_connect.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lnctu_connect.MainActivity;
import com.example.lnctu_connect.MainActivity2;
import com.example.lnctu_connect.MyApp;
import com.example.lnctu_connect.login.Login_or_register;
import com.example.lnctu_connect.R;
import com.example.lnctu_connect.user_details;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        firebaseAuth = FirebaseAuth.getInstance();
        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
//        ffffff

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        if (MyApp.isOnline()) {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                        if (firebaseAuth.getCurrentUser() != null) {
                            // User is logged in, start the desired activity
                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);

                            startActivity(intent);
                            finish();
                        } else {
                            // User is not logged in, start the login activity
                            Intent intent = new Intent(SplashScreen.this, Login_or_register.class);
                            startActivity(intent);
                            finish();
                        }


                    }
                }


            };
            thread.start();


        }

        else
        {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }
}