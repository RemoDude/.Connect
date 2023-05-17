package com.example.lnctu_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.List;

public class Searched_Uses_Profile extends AppCompatActivity {


    TextView follow_listt, following_list;
    de.hdodenhof.circleimageview.CircleImageView profile_picture;
    TextView usernameeee, gitkaurl, linkidkaurl, biodata;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String scholarid, username, value;
    LinearLayoutCompat linearLayoutCompat;
    TextView followbutt;

    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_uses_profile);


        SharedPreferences sharedPreferences = this.getSharedPreferences("myKey", MODE_PRIVATE);
        value = sharedPreferences.getString("value", "");


        gitkaurl = findViewById(R.id.gitadd_url);
        linkidkaurl = findViewById(R.id.linkedin_url);
        follow_listt = findViewById(R.id.followers_number);
        following_list = findViewById(R.id.following_number);
        usernameeee = findViewById(R.id.userkanaam);
        biodata = findViewById(R.id.setbio);
        profile_picture = findViewById(R.id.profile_image);
        toolbar = findViewById(R.id.toolbar);

        followbutt = findViewById(R.id.followbutton);
        linearLayoutCompat = findViewById(R.id.follow_linear);


        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        scholarid = getIntent().getStringExtra("scholar_id");
        username = getIntent().getStringExtra("name");


//        Toast.makeText(this, scholarid, Toast.LENGTH_SHORT).show();

        toolbar.setTitle(username);

        DatabaseReference following = FirebaseDatabase.getInstance()
                .getReference("users").child(scholarid).child("Following");


        DatabaseReference followers_of_users = FirebaseDatabase.getInstance()
                .getReference("users").child(scholarid).child("Followers");


        following.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                long count = snapshot.getChildrenCount();

                String valuess = String.valueOf(count);
                following_list.setText(valuess);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        followers_of_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long count = snapshot.getChildrenCount();

                String valuess = String.valueOf(count);


//                follow_listt.setText(num);
                follow_listt.setText(valuess);

                System.out.println(count + " This is count values");

                if (snapshot.child(value.trim()).exists()) {

                    followbutt.setText("Following");


                } else {

                    followbutt.setText("Follow");


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("users").child(scholarid.trim()).child("profiles")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String username = snapshot.child("user__name").getValue(String.class);
                        String GitUrl = snapshot.child("Github_url").getValue(String.class);
                        String LinkUrl = snapshot.child("LinkedIn_url").getValue(String.class);
                        String bioget = snapshot.child("Bio").getValue(String.class);
                        Long follow_no = snapshot.child("Followers").getValue(Long.class);
                        Long following_no = snapshot.child("Following").getValue(Long.class);
                        String image_dp = snapshot.child("image").getValue(String.class);

                        String num = String.valueOf(follow_no);
                        String num_folowing = String.valueOf(following_no);


                        usernameeee.setText(username);
                        gitkaurl.setText(GitUrl);
                        linkidkaurl.setText(LinkUrl);
                        biodata.setText(bioget);
                        Glide.with(Searched_Uses_Profile.this).load(image_dp).into(profile_picture);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        linearLayoutCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//               Saamne waaale ke followers badhne chahhiye on button click
//                 nd mere following badhne chaihiye


                DatabaseReference follows = FirebaseDatabase.getInstance()
                        .getReference("users").child(value).child("Following");


                DatabaseReference followers = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(scholarid).child("Followers");


                HashMap<String, Object> useradd = new HashMap<>();
                useradd.put(scholarid, true);


                HashMap<String, Object> follow_searched_users_list = new HashMap<>();
                follow_searched_users_list.put(value, true);


                follows.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if (snapshot.child(scholarid.trim()).exists()) {


                            Toast.makeText(Searched_Uses_Profile.this, "exists", Toast.LENGTH_SHORT).show();
                            follows.child(scholarid.trim()).removeValue();
                            followers.child(value.trim()).removeValue();


                        } else {

                            follows.updateChildren(useradd);
                            followers.updateChildren(follow_searched_users_list);
                            Toast.makeText(Searched_Uses_Profile.this, "Add", Toast.LENGTH_SHORT).show();


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                        FirebaseDatabase.getInstance().getReference().child("users")
//                                        .child(scholarid)
//                                        .child("Followers")
//                                                .updateChildren(useradd);


            }
        });


    }
}