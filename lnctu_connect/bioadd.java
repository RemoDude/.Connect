package com.example.lnctu_connect;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bioadd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bioadd extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextInputEditText bio__add;
    String value;


    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public bioadd() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bioadd.
     */
    // TODO: Rename and change types and number of parameters
    public static bioadd newInstance(String param1, String param2) {
        bioadd fragment = new bioadd();
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
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
//        auth = FirebaseAuth.getInstance();

        View view =  inflater.inflate(R.layout.fragment_bioadd, container, false);
        bio__add = view.findViewById(R.id.B_io);

        AppCompatImageButton button = view.findViewById(R.id.nextbutton);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        value = sharedPreferences.getString("value", "");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String BioGett = bio__add.getText().toString();

                HashMap<String, Object> obj = new HashMap<>();
                obj.put("Bio", BioGett);

                database.getReference().child("users").child(value)
                        .child("profiles").updateChildren(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
//                                ( (user_details) requireActivity()).InicatorProgress(10);
                            }
                        });

                ( (user_details)getActivity()).setupViewPager(3);


            }
        });


        return view;

    }
}