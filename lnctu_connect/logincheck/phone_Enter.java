package com.example.lnctu_connect.logincheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.lnctu_connect.R;
import com.example.lnctu_connect.login.otp_login;
import com.example.lnctu_connect.login.registserd_using;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class phone_Enter extends AppCompatActivity {

    AppCompatButton submit;
    String getnumber, number;
    //    TextInputEditText numberinput;
    AppCompatEditText numberinput;
    LottieAnimationView lottieAnimationView;
    ImageView backbutton;
    String codesent;
    ConstraintLayout constraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_enter);


        submit = findViewById(R.id.Continue_button);
        numberinput = findViewById(R.id.phonenumber);
        constraintLayout = findViewById(R.id.constraint_layout);
        backbutton = findViewById(R.id.backimage);


        numberinput.requestFocus();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            numberinput.isFocusedByDefault();
        }


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
//        ffffff

        lottieAnimationView = findViewById(R.id.animationView);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getnumber = numberinput.getText().toString();

                number = "+91" + getnumber;

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                numberinput.clearFocus();

                if (getnumber.isEmpty() || getnumber.length() < 10) {

                    Toast.makeText(phone_Enter.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();

                }

                else if (getnumber.isEmpty() || getnumber.length() == 10)

                {


                    constraintLayout.setAlpha(0.5F);
                    lottieAnimationView.setVisibility(View.VISIBLE);

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    Toast.makeText(phone_Enter.this, number, Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference().child("Phonenumber").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                String keys = snapshot1.getKey();
                                System.out.println(keys + " This is number");
                                if (keys.trim().equals(number.trim())) {

                                    Toast.makeText(phone_Enter.this, "True", Toast.LENGTH_SHORT).show();

                                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                            number,
                                            60,
                                            TimeUnit.SECONDS,
                                            phone_Enter.this,
                                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                @Override
                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                    Toast.makeText(phone_Enter.this, "Code Sent", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onVerificationFailed(@NonNull FirebaseException e) {


                                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                Toast.makeText(getApplicationContext(), "Faild to fetch OTP", Toast.LENGTH_SHORT).show();


                                                }

                                                @Override
                                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                    super.onCodeSent(s, forceResendingToken);

                                                    codesent = s;

                                                    Intent intent = new Intent(getApplicationContext(), otp_login_check.class);
                                                    intent.putExtra("mobile", number);
                                                    intent.putExtra("otp", s);
                                                    startActivity(intent);
                                                }
                                            }
                                    );


//                                    Toast.makeText(phone_Enter.this, "Exist", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(phone_Enter.this, otp_login_check.class);
//                                    intent.putExtra("number", number);
//                                    startActivity(intent);


                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }


            }
        });


    }
}