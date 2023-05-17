package com.example.lnctu_connect.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lnctu_connect.MainActivity;
import com.example.lnctu_connect.R;
import com.example.lnctu_connect.logincheck.phone_Enter;

public class Login_or_register extends AppCompatActivity {


    AppCompatButton login_regi_button;
    TextView signup_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_or_register);

        login_regi_button = findViewById(R.id.login_register_number);
        signup_register = findViewById(R.id.signup_login);


        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
//        ffffff

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        login_regi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login_or_register.this, phone_Enter.class);
                startActivity(intent);
            }
        });


        signup_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login_or_register.this, portal_login.class);
                startActivity(intent);

            }
        });
    }
}