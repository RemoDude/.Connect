package com.example.lnctu_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lnctu_connect.Adapters.MyRecyclerViewAdapter;
import com.example.lnctu_connect.Adapters.comment_Adapter;
import com.example.lnctu_connect.Adapters.rough_one_Adapter;
import com.example.lnctu_connect.Adapters.rough_two_adapter;
import com.example.lnctu_connect.modalls.qna_data;
import com.example.lnctu_connect.modalls.rough_one;
import com.example.lnctu_connect.modalls.rough_two;
import com.example.lnctu_connect.viewholder.roughone_viewholder;
import com.example.lnctu_connect.viewholder.roughtwo_viewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements rough_one_Adapter.OnCommentItemClickListener {

    EditText editText, editText1;
    LinearLayoutCompat submit, reply_submit;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;

    String value, getPushkey, getScholarID;

    ConstraintLayout constraintLayout1, constraintLayout2;

    RecyclerView.LayoutManager manager;


    List<rough_one> comment_list = new ArrayList<>();
    List<rough_two> reply_list = new ArrayList<>();

    String pushkey;
    rough_one_Adapter first_adapter;
    rough_two_adapter second_adapter;

    LinearLayoutCompat linearLayoutCompat, linearLayoutCompat1;

    String pushValue;
    AlphaAnimation fadeIn, fadeOut;
    TranslateAnimation animateTop, animateBottom;
    ImageView imageView;
    DatabaseReference comment_update, reply_update;
    int height;

    String keys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        pushkey = intent.getStringExtra("pushkey");

//        rough_one_Adapter adapter = new rough_one_Adapter(pushkey);

        database = FirebaseDatabase.getInstance();

        manager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(manager);
        DatabaseReference postsRef = database.getReference("posts").child(pushkey)
                .child("Comments");

        SharedPreferences sharedPreferences = this.getSharedPreferences("myKey", MODE_PRIVATE);
        value = sharedPreferences.getString("value","");


        editText = findViewById(R.id.reply);
        editText1 = findViewById(R.id.reply_coment);
        submit = findViewById(R.id.nextbutton);
        reply_submit = findViewById(R.id.submit_reply);

        constraintLayout1 = findViewById(R.id.constraintLayout2);
        constraintLayout2 = findViewById(R.id.constraintLayout3);

        linearLayoutCompat = findViewById(R.id.linearLayout);


//            rough_one_Adapter.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition()

        imageView = findViewById(R.id.goback);


         fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(100);

         fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(100); // Set the duration of the animation in milliseconds

        height = linearLayoutCompat.getHeight();

        animateTop = new TranslateAnimation(0, 0, height, 0);
        animateTop.setDuration(1000);

        animateBottom = new TranslateAnimation(0, 0, -height, 0);
        animateBottom.setDuration(1000);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String comment = editText.getText().toString();

                Log.d("key", "onClick: " + pushValue) ;

                comment_update= FirebaseDatabase.getInstance().getReference()
                        .child("posts").child(pushkey).child("Comments").push();
                pushValue = comment_update.getKey();

                HashMap<String, Object> commentadd = new HashMap<>();
                commentadd.put("Scholar_id", value);
                commentadd.put("pushkey", pushValue);
                commentadd.put("comment", comment);
                commentadd.put("timestamp", System.currentTimeMillis());
                comment_update.child(value).updateChildren(commentadd);
            }
        });


reply_submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        String reply = editText1.getText().toString();


        Toast.makeText(MainActivity2.this, getPushkey, Toast.LENGTH_SHORT).show();


                            HashMap<String, Object> reply_comment = new HashMap<>();
                            reply_comment.put("Scholar_id", value);
                            reply_comment.put("comment_reply", reply);
                            reply_comment.put("timestamp", System.currentTimeMillis());


        FirebaseDatabase.getInstance().getReference()
                .child("posts").child(pushkey).child("Comments")
                .child(getPushkey).child(getScholarID).child("replies").push().updateChildren(reply_comment);

//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                                keys = snapshot.child("pushkey").getValue(String.class);
//
//
//
//
//
////                        }
//
//                        Log.d("key", "onClick: " + keys);
//
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//


    }
});




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                constraintLayout1.setVisibility(View.VISIBLE);
                constraintLayout2.setVisibility(View.GONE);
                linearLayoutCompat.setVisibility(View.GONE);
                linearLayoutCompat.startAnimation(fadeOut);


            }
        });


        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comment_list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

                        if (snapshot2.hasChild("comment")) {
//                            Toast.makeText(MainActivity2.this, "yes we have", Toast.LENGTH_SHORT).show();
                            rough_one one = snapshot2.getValue(rough_one.class);
//                            Log.d("TAG", "onDataChange: " + one.getComment());
                            comment_list.add(one);
                            first_adapter = new rough_one_Adapter( comment_list, pushkey);
                            first_adapter.setOnCommentItemClickListener(MainActivity2.this);
                            recyclerView.setHasFixedSize(false);

                            recyclerView.setAdapter(first_adapter);
                        } else {
                            Toast.makeText(MainActivity2.this, "Nahi hai", Toast.LENGTH_SHORT).show();
                        }
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onCommentItemClick(View view, int position) {
        constraintLayout1.setVisibility(View.GONE);
        constraintLayout2.setVisibility(View.VISIBLE);
        linearLayoutCompat.setVisibility(View.VISIBLE);
        linearLayoutCompat.startAnimation(fadeIn);

    }

    @Override
    public void onCommentItemClick(View view, int position, int replyPosition) {
        constraintLayout1.setVisibility(View.GONE);
        constraintLayout2.setVisibility(View.VISIBLE);
        linearLayoutCompat.setVisibility(View.VISIBLE);
        linearLayoutCompat.startAnimation(fadeIn);

//        first_adapter.notifyItemChanged(replyPosition);
    }

    @Override
    public void onItemClick(String push_Key, String scholar__id) {
        getPushkey = push_Key;
        getScholarID = scholar__id;
    }




}