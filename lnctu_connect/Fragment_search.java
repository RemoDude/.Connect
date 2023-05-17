package com.example.lnctu_connect;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lnctu_connect.Adapters.Adapter_of_search_user;
import com.example.lnctu_connect.modalls.searchusers;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.FirebaseRec
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_search extends Fragment {


    SearchView searchView;

    List<searchusers> SearchUsers = new ArrayList<>();

    private DatabaseReference usersRef;
    RecyclerView recyclerView;

    Adapter_of_search_user adapters;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_search.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_search newInstance(String param1, String param2) {
        Fragment_search fragment = new Fragment_search();
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


        View view = inflater.inflate(R.layout.fragment_search, container, false);

        usersRef = FirebaseDatabase.getInstance().getReference().child("All_Users");
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        searchView = view.findViewById(R.id.searchview);

        FirebaseRecyclerOptions<searchusers> options = new FirebaseRecyclerOptions
                .Builder<searchusers>()
                .setQuery(null, searchusers.class)
                .build();


        adapters = new Adapter_of_search_user(options);

        recyclerView.setAdapter(adapters);

        int count = adapters.getItemCount();

        Toast.makeText(getContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();


        if (adapters.getItemCount() == 0)
        {
            Toast.makeText(getContext(), "Searchview is Empty", Toast.LENGTH_SHORT).show();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapters.stopListening();
                processsearch(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapters.stopListening();

                if (newText.isEmpty()) {
                    processsearch("");
                    adapters.stopListening();


                } else {
                    adapters.stopListening();

                    processsearch(newText);
                }
                return false;
            }
        });


        return view;


    }

    private void processsearch(String s) {

        String allwords = s.toLowerCase();


        FirebaseRecyclerOptions<searchusers> options = new FirebaseRecyclerOptions
                .Builder<searchusers>()
                .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child("All_Users").orderByChild("username").startAt(allwords).endAt(allwords + "\uf8ff"), searchusers.class)
                .build();
        adapters = new Adapter_of_search_user(options);
        recyclerView.setAdapter(adapters);
        adapters.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
        adapters.stopListening();
    }


}