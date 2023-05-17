package com.example.lnctu_connect;

import static com.example.lnctu_connect.QnA.firebaseDatabase;
import static com.example.lnctu_connect.QnA.scholar_id;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Repo;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Post_creation extends AppCompatActivity {

    AppCompatButton click_gallery, share_button;

    String send_data, value, username, image_dp;
    de.hdodenhof.circleimageview.CircleImageView profile;
    TextView user_name_share, current_time;
    Bitmap   bitmap;
    TextInputEditText editText;
    Repo repo;
    Uri uri, uri2;
    ImageView imageview;
    String times;

    ConstraintLayout constraintLayout;

    FirebaseDatabase database;
    FirebaseStorage storage;

    LottieAnimationView lottieAnimationView;



    String filePath;

    HashMap<String, Object> obj = new HashMap<>();

    ValueEventListener valueEventListener, valueEventListeneragain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);

        SharedPreferences sharedPreferences = this.getSharedPreferences("myKey", MODE_PRIVATE);
        value = sharedPreferences.getString("value", "");


        click_gallery = findViewById(R.id.image);
        imageview = findViewById(R.id.upload_image);
        share_button = findViewById(R.id.Ask_button);

        editText = findViewById(R.id.question_asked);

        profile = findViewById(R.id.profile_post);
        user_name_share = findViewById(R.id.user_name_post);
        current_time = findViewById(R.id.timestamp_post);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timestamp = sdf.format(currentTime);

        lottieAnimationView = findViewById(R.id.animationView);
        constraintLayout = findViewById(R.id.constraint);


        current_time.setText(timestamp);

        FirebaseDatabase.getInstance().getReference().child("users").child(value).child("profiles")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String name = snapshot.child("user__name").getValue(String.class);
                        String image = snapshot.child("image").getValue(String.class);

                        user_name_share.setText(name);
                        Glide.with(Post_creation.this).load(image).into(profile);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        getWindow().setStatusBarColor(Color.parseColor("#1B8BF2"));


        click_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launcher.launch(ImagePicker.Companion.with(Post_creation.this)
                        .crop()
                        .cropFreeStyle()
                        .galleryOnly()
                        .setOutputFormat(Bitmap.CompressFormat.JPEG)
                        .createIntent()
                );

            }
        });


        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String caption = editText.getText().toString();

                lottieAnimationView.setVisibility(View.VISIBLE);
                constraintLayout.setAlpha(0.5F);

                uploadImage();



//                Toast.makeText(Post_creation.this, filePath, Toast.LENGTH_SHORT).show();

                System.out.println(filePath + " this is value");

            }
        });

    }

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    uri2 = uri;
                    send_data = result.getData().getDataString();

                    // Use the uri to load the image
                    imageview.setVisibility(View.VISIBLE);
                    Glide.with(getApplication()).load(uri).centerCrop().into(imageview);
                    imageview.setImageURI(uri);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    imageview.setVisibility(View.GONE);
                    Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();


                }
            });


    private void uploadImage() {

        String caption = editText.getText().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference reference = storage.getReference().child(value).child("Post" + System.currentTimeMillis());


        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timestamp = sdf.format(currentTime);

        SimpleDateFormat sdff = new SimpleDateFormat();
        sdff.setTimeZone(TimeZone.getTimeZone("UTC"));
        String newtimestamp = sdf.format(currentTime);

        if (send_data == null) {

            Toast.makeText(this, "Image not seleted", Toast.LENGTH_SHORT).show();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("posts");

            DatabaseReference users_value_get = FirebaseDatabase.getInstance().getReference()
                    .child("users").child(value)
                    .child("profiles");

            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    username = snapshot.child("user__name").getValue(String.class);
                    image_dp = snapshot.child("image").getValue(String.class);
                    String pushKey = databaseReference.push().getKey();

                    HashMap<String, Object> obj = new HashMap<>();
                    obj.put("scholar_id", value);
                    obj.put("user_name", username);
                    obj.put("post_time_stamp", timestamp);
                    obj.put("pushkey", pushKey);
                    obj.put("user_image", image_dp);
                    obj.put("Caption", caption);
                    obj.put("post_mili_second", System.currentTimeMillis());

                    databaseReference.child(pushKey).updateChildren(obj)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    users_value_get.removeEventListener(valueEventListener);

                                    FirebaseDatabase.getInstance().getReference().child("users").child(value).child("Posts").child(pushKey)
                                            .updateChildren(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    HashMap<String, Object> Like_DisLike = new HashMap<>();
                                                    Like_DisLike.put("Likes", "");
                                                    Like_DisLike.put("DisLikes", "");

                                                    FirebaseDatabase.getInstance().getReference().child("AllPosts").child(pushKey)
                                                            .setValue(Like_DisLike);

                                                    Toast.makeText(Post_creation.this, "Success", Toast.LENGTH_SHORT).show();

                                                    lottieAnimationView.setVisibility(View.GONE);
                                                    constraintLayout.setAlpha(1);
                                                    Map<String, String> timeee = ServerValue.TIMESTAMP;
                                                    System.out.println(timeee);

                                                }
                                            });
                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {


                }
            };
            users_value_get.addListenerForSingleValueEvent(valueEventListener);


        } else {

            uri = Uri.parse(send_data);


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

// Calculate the scaled dimensions of the image
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWidth = ((float) 1024) / width;
            float scaleHeight = ((float) 1024) / height;
            float scale = Math.min(scaleWidth, scaleHeight);

// Create a new scaled bitmap
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

// Compress the scaled bitmap
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] data = baos.toByteArray();

//                                filePath = uri.toString();
//            filePath = Arrays.toString(data);



            reference.putBytes(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    if (task.isSuccessful()) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


//                                 bitmap = null;
//                                try {
//                                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }

                                filePath = uri.toString();






                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                                            .child("posts");

                                    DatabaseReference users_value_get = FirebaseDatabase.getInstance().getReference()
                                            .child("users").child(value)
                                            .child("profiles");


                                    valueEventListener = new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            username = snapshot.child("user__name").getValue(String.class);
                                            image_dp = snapshot.child("image").getValue(String.class);
                                            String pushKey = databaseReference.push().getKey();

                                            HashMap<String, Object> obj = new HashMap<>();
                                            obj.put("scholar_id", value);
                                            obj.put("user_name", username);
                                            obj.put("post_time_stamp", timestamp);
                                            obj.put("post_image", filePath);
                                            obj.put("pushkey", pushKey);
                                            obj.put("user_image", image_dp);
                                            obj.put("Caption", caption);
                                            obj.put("post_mili_second", System.currentTimeMillis());

//                                    databaseReference.child(pushKey).child(value).updateChildren(obj)
                                            databaseReference.child(pushKey).updateChildren(obj)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {

                                                            users_value_get.removeEventListener(valueEventListener);

                                                            FirebaseDatabase.getInstance().getReference().child("users").child(value).child("Posts").child(pushKey)
                                                                    .updateChildren(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {

//                                                                    Toast.makeText(Question_Asked.this, "success", Toast.LENGTH_SHORT).show();

                                                                            HashMap<String, Object> Like_DisLike = new HashMap<>();
                                                                            Like_DisLike.put("Likes", "");
                                                                            Like_DisLike.put("DisLikes", "");


                                                                            FirebaseDatabase.getInstance().getReference().child("AllPosts").child(pushKey)
                                                                                    .setValue(Like_DisLike);

                                                                            Toast.makeText(Post_creation.this, "Success", Toast.LENGTH_SHORT).show();
                                                                            lottieAnimationView.setVisibility(View.GONE);
                                                                            constraintLayout.setAlpha(1);
                                                                            Map<String, String> timeee = ServerValue.TIMESTAMP;

                                                                            System.out.println(timeee);


                                                                        }
                                                                    });


                                                        }
                                                    });


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {


                                        }
                                    };
                                    users_value_get.addListenerForSingleValueEvent(valueEventListener);



                            }
                        });


                    }


                }
            });

        }
    }


}