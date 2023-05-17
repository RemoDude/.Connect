package com.example.lnctu_connect;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_profile extends Fragment {

    MaterialToolbar toolbar;

    TabLayout tabLayout;
    ViewPager viewPager;

    TextView class_branch;

    TextView follow_listt, following_list;
    LinearLayoutCompat jumpTab, jumptab2;
    //    ShapeableImageView profile_picture;
    de.hdodenhof.circleimageview.CircleImageView profile_picture;

    List<profile_data> details;

    TextView usernameeee, gitkaurl, linkidkaurl, biodata;

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    String value;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_profile newInstance(String param1, String param2) {
        Fragment_profile fragment = new Fragment_profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar = view.findViewById(R.id.toolbar);

        jumpTab = view.findViewById(R.id.Followers);
        jumptab2 = view.findViewById(R.id.Following);
        toolbar.inflateMenu(R.menu.toolbar_menu);

        gitkaurl = view.findViewById(R.id.gitadd_url);
        linkidkaurl = view.findViewById(R.id.linkedin_url);
class_branch = view.findViewById(R.id.class_name);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        value = sharedPreferences.getString("value", "");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.dit) {
                    Intent intent = new Intent(getActivity(), profile_creation.class);
                    startActivity(intent);

                }

                return false;
            }
        });

        follow_listt = view.findViewById(R.id.followers_number);
        following_list = view.findViewById(R.id.following_number);
        usernameeee = view.findViewById(R.id.userkanaam);
        biodata = view.findViewById(R.id.setbio);


        profile_picture = view.findViewById(R.id.profile_image);


        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewpager);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        setHasOptionsMenu(true);

        jumpTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), follwed_following_tab.class);
                intent.putExtra("selected_tab", 0);

                startActivity(intent);



            }
        });

        jumptab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), follwed_following_tab.class);
                intent.putExtra("selected_tab", 1);
                startActivity(intent);



            }
        });

        final My_Adapter adapter = new My_Adapter(getContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        FragmentManager fragmentManager = getChildFragmentManager();


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//                .
//
        database.getReference().child("users").child(value)
                .child("profiles").addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

//
//                        for (DataSnapshot ds:snapshot.getChildren())
//                        {

                        String username = snapshot.child("user__name").getValue(String.class);
                        String GitUrl = snapshot.child("Github_url").getValue(String.class);
                        String LinkUrl = snapshot.child("LinkedIn_url").getValue(String.class);
                        String bioget = snapshot.child("Bio").getValue(String.class);
                        Long follow_no = snapshot.child("Followers").getValue(Long.class);
                        Long following_no = snapshot.child("Following").getValue(Long.class);
                        String image_dp = snapshot.child("image").getValue(String.class);
                       String  class_name = snapshot.child("class").getValue(String.class);


////                        System.out.println(username);
//                        String num = String.valueOf(follow_no);
//                        String num_folowing = String.valueOf(following_no);

                        Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                        FirebaseDataSingleton.getInstance().setData(data);


//                        follow_listt.setText(num);
//                        following_list.setText(num_folowing);

                        usernameeee.setText(username);
                        gitkaurl.setText(GitUrl);
                        linkidkaurl.setText(LinkUrl);
                        biodata.setText(bioget);
                        class_branch.setText("( "+class_name+" )");
                        Glide.with(getContext()).load(image_dp).into(profile_picture);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        gitkaurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(gitkaurl.getText().toString()));
                Toast.makeText(getActivity(), gitkaurl.getText().toString(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        DatabaseReference following_num = FirebaseDatabase.getInstance().getReference()
                .child("users").child(value).child("Following");


        DatabaseReference followers_num = FirebaseDatabase.getInstance()
                .getReference("users").child(value).child("Followers");


        followers_num.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount();
                String counts = String.valueOf(count);

                follow_listt.setText(counts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        following_num.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long count = snapshot.getChildrenCount();

                String count_value = String.valueOf(count);

                following_list.setText(count_value);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return view;
    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.dit) {
//            Intent intent = new Intent(getActivity(), profile_creation.class);
//            startActivity(intent);
            Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


}

