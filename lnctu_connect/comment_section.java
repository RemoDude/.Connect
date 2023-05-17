package com.example.lnctu_connect;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lnctu_connect.Adapters.MyRecyclerViewAdapter;
import com.example.lnctu_connect.Adapters.comment_Adapter;
import com.example.lnctu_connect.modalls.ChildItem;
import com.example.lnctu_connect.modalls.comment_modal;
import com.example.lnctu_connect.modalls.qna_data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class comment_section extends AppCompatActivity {
    RecyclerView recyclerView;
    comment_Adapter mAdapter;
    List<comment_modal> dataagain = new ArrayList<>();
    List<ChildItem> childItems = new ArrayList<>();
    List<String> data = new ArrayList<>();
    String pushkey;
    comment_modal commentModal;

    ChildItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_section);


        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        pushkey = intent.getStringExtra("pushkey");

        FirebaseDatabase database = FirebaseDatabase.getInstance();

// Get a reference to the "posts" node in the database
        DatabaseReference postsRef = database.getReference("posts");

// Get a reference to the specific post that contains the comments and replies
        DatabaseReference postRef = postsRef.child(pushkey);





    }




}