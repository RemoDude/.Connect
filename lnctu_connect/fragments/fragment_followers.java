package com.example.lnctu_connect.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lnctu_connect.Adapters.Adapter_of_followList;
import com.example.lnctu_connect.Adapters.MyRecyclerViewAdapter;
import com.example.lnctu_connect.R;
import com.example.lnctu_connect.modalls.follow_list;
import com.example.lnctu_connect.modalls.qna_data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_followers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_followers extends Fragment {

    RecyclerView recyclerView;

    Adapter_of_followList followList;
    String keys, value;
    List<follow_list> dataagain = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_followers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fraent_followers.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_followers newInstance(String param1, String param2) {
        fragment_followers fragment = new fragment_followers();
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


        View view = inflater.inflate(R.layout.fragment_fraent_followers, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        value = sharedPreferences.getString("value", "");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users");

        reference.child(value).child("Followers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                     keys = snapshot1.getKey();
                    long count = snapshot1.getChildrenCount();

                    reference.child(keys).child("profiles").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            follow_list list = snapshot.getValue(follow_list.class);

//                            System.out.println(list.getUser__name());
                                dataagain.add(list);
                            Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();

                                followList = new Adapter_of_followList(dataagain);

                            System.out.println(followList.getItemCount());
                                recyclerView.setAdapter(followList);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;

    }
}