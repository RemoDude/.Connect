package com.example.lnctu_connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lnctu_connect.Adapters.Adapter_of_Answers;
import com.example.lnctu_connect.modalls.Question_Answers_modal;
import com.example.lnctu_connect.modalls.qna_data;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Questions_Answers extends AppCompatActivity {

    de.hdodenhof.circleimageview.CircleImageView profileurltext;
    TextView nametext, timetext, questiontext;
    AppCompatImageButton appCompatImageButton;
//    AppCompatEditText editText;
    EditText editText;
    String time;

    FirebaseDatabase firebaseDatabase;

    String value, Answername;

    String new_name;

    String answergivenname;
    String  currenttime;

    String scholar;
    String  user_ka_profile;

    String profile_ki_value;

    String   pofile_url;

    DatabaseReference ref;

    Adapter_of_Answers  mAdapter;
    RecyclerView recyclerView;

//    List<String[]> datausers = new ArrayList<>();

    List<Question_Answers_modal> dataagain = new ArrayList<>();



    String[] newData;





    String database_scholar_id , image_of_answer;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_answers);

        String questionAsked = getIntent().getStringExtra("question_asked");
        String username = getIntent().getStringExtra("username");
        String timestamp = getIntent().getStringExtra("time");
        String profileurl = getIntent().getStringExtra("profile");
        String pushkey = getIntent().getStringExtra("key");
        scholar = getIntent().getStringExtra("scholar_key");


        appCompatImageButton = findViewById(R.id.nextbutton);

        nametext = findViewById(R.id.user_name_qna);
        nametext.setText(username);

        questiontext = findViewById(R.id.question_qna);
        questiontext.setText(questionAsked);

        timetext = findViewById(R.id.timestamp_qna);
        timetext.setText(timestamp);
         storage = FirebaseStorage.getInstance();

        Map<String, Object> database_data = FirebaseDataSingleton.getInstance().getData();

         answergivenname = (String) database_data.get("user__name");
        System.out.println("reading data from singleton class");
        System.out.println(answergivenname);

        profileurltext = findViewById(R.id.profile_qna);

        editText = findViewById(R.id.answers);
        editText.setMovementMethod(new ScrollingMovementMethod());

        firebaseDatabase = FirebaseDatabase.getInstance();

        Glide.with(this).load(profileurl).into(profileurltext);

        SharedPreferences sharedPreferences = this.getSharedPreferences("myKey", MODE_PRIVATE);
        value = sharedPreferences.getString("value","");


        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
       time= sdf.format(currentTime);



        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        recyclerView = findViewById(R.id.recyclerview_of_qna);
//        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        System.out.println(value);

        FirebaseDatabase.getInstance().getReference().child("QnA").child("Answers").child(pushkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataagain.clear();

                for (DataSnapshot snapshot1: snapshot.getChildren())

                {

                    for (DataSnapshot snapshot2: snapshot1.getChildren())

                    {

                        Question_Answers_modal questionAnswersModal = snapshot2.getValue(Question_Answers_modal.class);


                        dataagain.add(0,questionAnswersModal);

//                        qna_data qnaData = snapshot2.getValue(qna_data.class);



                        Answername = snapshot2.child("Answer").getValue(String.class);
                        currenttime = snapshot2.child("Answer_time").getValue(String.class);
                        new_name = snapshot2.child("answer_name").getValue(String.class);
                        String profile_image =  snapshot2.child("image").getValue(String.class);
                        Long upvote =  snapshot2.child("upvotes").getValue(Long.class);
                        Long downvote =  snapshot2.child("downvotes").getValue(Long.class);
//                        profile_ki_value = snapshot2.child("image").getValue(String.class);
                        System.out.println(profile_image);

//                        datausers.add(new String[]{Answername,currenttime, new_name,profile_image, String.valueOf(upvote), String.valueOf(downvote)});
//                        mAdapter.notifyItemInserted(0);
//                        mAdapter.notifyDataSetChanged();



                    }



                }

                mAdapter = new Adapter_of_Answers(dataagain);
                recyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//
//
//        recyclerView.getLayoutManager()
//                .smoothScrollToPosition(recyclerView,new RecyclerView.State(), recyclerView.getAdapter()
//                        .getItemCount());
        appCompatImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Answers = editText.getText().toString();
                String profiles = (String) database_data.get("image");

//                String
                        ref = FirebaseDatabase.getInstance().getReference().child("QnA").child("Answers").child(pushkey);
                String answer_pushkey = ref.push().getKey();

                HashMap<String, Object> obj = new HashMap<>();
                obj.put("Answer", Answers);
                obj.put("Answer_time", time);
                obj.put("Scholar_id", value);
                obj.put("answer_name", answergivenname);
                obj.put("image", profiles);
                obj.put("upvotes", 0);
                obj.put("downvotes", 0);
                obj.put("pushkey", answer_pushkey);
                obj.put("question_key", pushkey);

//                firebaseDatabase.getReference().child("QnA").child("Answers").child(pushkey)
                assert answer_pushkey != null;
                ref.child(answer_pushkey).child(value)
                        .updateChildren(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                HashMap<String, Object> Like_DisLike = new HashMap<>();
                                Like_DisLike.put("Likes", "");
                                Like_DisLike.put("DisLikes", "");



FirebaseDatabase.getInstance().getReference().child("AllAnswers").child(answer_pushkey)
        .setValue(Like_DisLike);


                            }
                        });
//
//
            }
        });
//
//
//
//
        if (getSupportActionBar()!=null)
        {

            getSupportActionBar().hide();

        }

//
//
//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // Get the current cursor position
//                    int cursorPosition = editText.getSelectionStart();
//
//                    // Scroll to the bottom of the text
//                    editText.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            editText.setSelection(editText.getText().length());
//                        }
//                    }, 100);
//
//                    // Set the cursor position back to where it was
//                    editText.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            editText.setSelection(cursorPosition);
//                        }
//                    }, 200);
//                }
//            }
//        });
//
//
//    }
//
//
//    public void anotherMethod(String name) {
//        // Do something with the name value, for example print it to the console
//        System.out.println("Name from another method: " + name);
//    }
//
//    public interface StringCompletionListener {
//        void onComplete(String data);
//        void onError(String errorMessage);
//    }
//
//
//
//    public void firebaseimagee(String url)
//    {
//
//
//
//
//    }
//
//    private String getData() {
//
//
//  firebaseDatabase.getReference().child("users").child(value).child("profiles").addValueEventListener(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//            pofile_url = snapshot.child("image").getValue(String.class);
//
//
//
//
//
//
//
//
//
//
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//});
//
//
//
//
//        return pofile_url;
    }
//
}