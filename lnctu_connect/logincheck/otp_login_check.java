package com.example.lnctu_connect.logincheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lnctu_connect.MainActivity;
import com.example.lnctu_connect.R;
import com.example.lnctu_connect.user_details;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class otp_login_check extends AppCompatActivity {

    AppCompatButton veriButton;
    EditText otp1, otp2, otp3, otp4, otp5, otp6;


    TextView timer;
    String enteredotp;

    FirebaseAuth firebaseAuth;
    String value, phone_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login_check);


        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
//        ffffff


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        otp1 = findViewById(R.id.input_otp0);
        otp2 = findViewById(R.id.input_otp1);
        otp3 = findViewById(R.id.input_otp2);
        otp4 = findViewById(R.id.input_otp3);
        otp5 = findViewById(R.id.input_otp4);
        otp6 = findViewById(R.id.input_otp5);

//
//        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
//        value = sharedPreferences.getString("value", "");


        phone_number = getIntent().getStringExtra("mobile");


        TextView textView = (TextView) findViewById(R.id.number_text);
        firebaseAuth = FirebaseAuth.getInstance();

        Toast.makeText(this, phone_number, Toast.LENGTH_SHORT).show();

        textView.setText(phone_number);
        enteredotp = getIntent().getStringExtra("otp");


        otp1.requestFocus();

        timer = findViewById(R.id.resend_code);

        int count = 60; // number of seconds for the timer

        new CountDownTimer(count * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                timer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                timer.setText("done!");
            }
        }.start();


        otp1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp1.getText().toString().length() == 1)     //size as per your requirement
                {
                    otp2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        otp2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp2.getText().toString().length() == 1)     //size as per your requirement
                {
                    otp3.requestFocus();
                } else if (otp2.getText().toString().length() == 0) {
                    otp1.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        otp3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp3.getText().toString().length() == 1)     //size as per your requirement
                {
                    otp4.requestFocus();
                } else if (otp3.getText().toString().length() == 0) {
                    otp2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        otp4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp4.getText().toString().length() == 1)     //size as per your requirement
                {
                    otp5.requestFocus();
                } else if (otp4.getText().toString().length() == 0) {
                    otp3.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        otp5.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp5.getText().toString().length() == 1)     //size as per your requirement
                {
                    otp6.requestFocus();
                } else if (otp5.getText().toString().length() == 0) {
                    otp4.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        otp6.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otp6.getText().toString().length() == 0)     //size as per your requirement
                {
                    otp5.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
//        ffffff

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        firebaseAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);


        veriButton = findViewById(R.id.verify_button);
        veriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                otp1.clearFocus();
                if (!otp1.getText().toString().trim().isEmpty() && !otp2.getText().toString().trim().isEmpty()) {
                    String enteredcode = otp1.getText().toString() +
                            otp2.getText().toString() +
                            otp3.getText().toString() +
                            otp4.getText().toString() +
                            otp5.getText().toString() +
                            otp6.getText().toString();
                    if (enteredotp != null) {


                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                enteredotp, enteredcode);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {


                                        if (task.isSuccessful()) {

                                            FirebaseDatabase.getInstance().getReference().child("Phonenumber")
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                                            for(DataSnapshot snapshot1: snapshot.getChildren())
//                                                            {


                                                                String scholar_no = snapshot.child(phone_number).getValue(String.class);
//                                                            String scholar_no = snapshot.getKey();
                                                            Toast.makeText(otp_login_check.this, scholar_no, Toast.LENGTH_SHORT).show();

                                                            System.out.println(scholar_no);
//
                                                            SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = sharedPref.edit();
                                                            editor.putString("value", scholar_no.trim());
                                                            editor.apply();

                                                            Toast.makeText(otp_login_check.this, scholar_no.trim(), Toast.LENGTH_SHORT).show();

                                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
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


            }
        });


    }
}