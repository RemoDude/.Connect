package com.example.lnctu_connect;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link deatisls_two#newInstance} factory method to
 * create an instance of this fragment.
 */
public class deatisls_two extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    AppCompatImageButton button;
    TextInputEditText edituser;

    String value;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public deatisls_two() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment deatisls_two.
     */
    // TODO: Rename and change types and number of parameters
    public static deatisls_two newInstance(String param1, String param2) {
        deatisls_two fragment = new deatisls_two();
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
        View view = inflater.inflate(R.layout.fragment_deatisls_two, container, false);

        button = view.findViewById(R.id.nextbutton);

        edituser = view.findViewById(R.id.User_name);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
//        auth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
         value = sharedPreferences.getString("value", "");

        System.out.println(value);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ( (user_details) requireActivity()).InicatorProgress(10);

                String user_name = edituser.getText().toString();

                FirebaseDatabase.getInstance().getReference("usernames").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

//                        if(snapshot.equals(user_name))
//                        {

                        for (DataSnapshot snapshot1 : snapshot.getChildren())
                        {

                            String names  = snapshot1.getValue(String.class);


                            System.out.println(snapshot1.getKey().toString());

                            if (snapshot1.getKey().equals(user_name))
                            {
                                Toast.makeText(getActivity(), "Username is taken", Toast.LENGTH_SHORT).show();
edituser.setError("Username is Taken");
                                return;
                            }
                            else
                            {
                                database.getReference().child("users")
                                        .child("profiles")
                                        .orderByChild("user__name").equalTo(user_name)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {



                                                HashMap<String, Object> obj = new HashMap<>();
                                                obj.put("user__name", user_name);
                                                obj.put("Github_url", "");
                                                obj.put("LinkedIn_url", "");

                                                database.getReference().child("users").child(value).child("profiles")
                                                        .updateChildren(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                                Toast.makeText(getActivity(), "Username added", Toast.LENGTH_SHORT).show();

                                                            }
                                                        });

                                                HashMap<String, Object> obj3 = new HashMap<>();
                                                obj3.put("username", user_name);
                                                FirebaseDatabase.getInstance().getReference().child("usernames").child(value)
                                                        .updateChildren(obj3);

                                                HashMap<String, Object> obj2 = new HashMap<>();
                                                obj2.put("username", user_name);
                                                obj2.put("profile_name", user_name);
                                                FirebaseDatabase.getInstance().getReference().child("AllUsers").child(value)
                                                        .updateChildren(obj2);

                                                ((user_details) getActivity()).setupViewPager(2);



                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                return;
                            }

                        }
//                            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

                    }


//                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });








            }
        });
        return view;


    }



}