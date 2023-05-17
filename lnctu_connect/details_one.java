package com.example.lnctu_connect;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.appsearch.GlobalSearchSession;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link details_one#newInstance} factory method to
 * create an instance of this fragment.
 */
public class details_one extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    AppCompatImageButton button;

    de.hdodenhof.circleimageview.CircleImageView imageview;

    String value;

    private static final int GalleryPick = 1;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    String cameraPermission[];
    String storagePermission[];


    String send_data;

    Uri uri, uri2;
    Intent data_upload;
    Intent data;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public details_one() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment details_one.
     */
    // TODO: Rename and change types and number of parameters
    public static details_one newInstance(String param1, String param2) {
        details_one fragment = new details_one();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_one, container, false);

        button = view.findViewById(R.id.nextbutton);

        imageview = view.findViewById(R.id._profile_image);


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        cameraPermission = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        value = sharedPreferences.getString("value","");


        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                launcher.launch(  ImagePicker.Companion.with(getActivity())
                        .crop()
                        .cropFreeStyle()
                        .galleryOnly()
                                .setOutputFormat(Bitmap.CompressFormat.JPEG)
                        .createIntent()
);


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((user_details) requireActivity()).InicatorProgress(10);//
                uploadImage();

                ((user_details) getActivity()).setupViewPager(1);




            }
        });

        return view;
    }





    ActivityResultLauncher<Intent> launcher=
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                if(result.getResultCode()==RESULT_OK){
                    Uri uri=result.getData().getData();
                                         uri2 = uri;
                                          send_data = result.getData().getDataString();

                    // Use the uri to load the image
                    imageview.setImageURI(uri);
                }
                else if(result.getResultCode()==ImagePicker.RESULT_ERROR){
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error


                }
            });





        void uploadImage () {
            uri = Uri.parse(send_data);

            if (uri == null) {

                Toast.makeText(getContext(), "Please add profile", Toast.LENGTH_SHORT).show();

            } else {

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    long time = new Date().getTime();
//            StorageReference reference = storage.getReference().child("Profiles").child(time + "");
//            StorageReference reference = storage.getReference().child(FirebaseAuth.getInstance().getUid()).child("profile_pic");
                    StorageReference reference = storage.getReference().child(value).child("profile_pic");
                    reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {

                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {


                                        String filePath = uri.toString();
                                        HashMap<String, Object> obj = new HashMap<>();
                                        obj.put("image", filePath);
                                        database.getReference().child("users")
//                                        .child(FirebaseAuth.getInstance().getUid())
                                                .child(value)
                                                .child("profiles")
                                                .updateChildren(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        HashMap<String, Object> obj2 = new HashMap<>();
                                                        obj2.put("image", filePath);
                                                        obj2.put("Scholar_id", value);
                                                        FirebaseDatabase.getInstance().getReference().child("AllUsers").child(value)
                                                                .updateChildren(obj2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        ((user_details) getActivity()).setupViewPager(1);

                                                                    }
                                                                });


                                                    }
                                                });



                                    }
                                });


                            }


                        }
                    });


            }
        }
    }


