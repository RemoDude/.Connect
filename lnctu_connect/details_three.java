package com.example.lnctu_connect;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adroitandroid.chipcloud.Chip;
import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.adroitandroid.chipcloud.FlowLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link details_three#newInstance} factory method to
 * create an instance of this fragment.
 */
public class details_three extends Fragment {

    String det;
    ArrayList<String> namen = new ArrayList<>();
    AppCompatImageButton button;

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    String result;
    int indexget;

    Boolean condotion = false;

    String value;


    int[] selectedIndices;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public details_three() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment details_three.
     */
    // TODO: Rename and change types and number of parameters
    public static details_three newInstance(String param1, String param2) {
        details_three fragment = new details_three();
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
        View view = inflater.inflate(R.layout.fragment_details_three, container, false);



        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        String[] strAr2 = {"Photography",
                "Sports",
                "Books",
        "Reading",
                "Music",
        "Computer",
        "Technology",
        "Drawing",
        "Food",
        "Agriculture",
        "Gaming",
        "Travelling",
        "Movies",
        "Coding",
        "Programming",
        "Web Development",
        "Android development",
        "Full Stack",
        };

        ChipCloud chipCloud =  view.findViewById(R.id.chip_cloud);

//        ChipGroup chipGroup = view.findViewById(R.id.chip_cloud);
//        for (String label : strAr2) {
//            Chip chip = new Chip(getActivity());
//            chip.setText(label);
//            chip.setClickable(true);
//            chipGroup.addView(chip);
//            chip.setBackgroundColor(R.color.teal_200);
//        }
        List<String> selectedChips = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        value = sharedPreferences.getString("value", "");

        new ChipCloud.Configure()
                .chipCloud(chipCloud)
                .selectedColor(Color.parseColor("#1B8BF2"))
                .selectedFontColor(Color.parseColor("#ffffff"))
                .deselectedColor(Color.parseColor("#e1e1e1"))
                .deselectedFontColor(Color.parseColor("#333333"))
                .selectTransitionMS(500)
                .deselectTransitionMS(250)
                .mode(ChipCloud.Mode.MULTI)
                .labels(strAr2)
                .allCaps(false)
                .gravity(ChipCloud.Gravity.STAGGERED)
                .chipListener(new ChipListener() {
                    @Override
                    public void chipSelected(int index) {
                        Log.d("ChipListener", "Selected chip: " + strAr2[index]);
                        System.out.println(strAr2[index]);
                        selectedChips.add(strAr2[index]);


                    }
                    @Override
                    public void chipDeselected(int index) {
                        selectedChips.remove(strAr2[index]);
                    }
                })
                .build();

        namen.add(det);



        button = view.findViewById(R.id.nextbutton);

//

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> obj = new HashMap<>();
                obj.put("Followers", 0);
                obj.put("Following", 0);

                HashMap<String, Object> users_update = new HashMap<>();
                users_update.put(value, true);


                Log.d("submitButton", "Clicked");
                System.out.println(selectedChips);

                DatabaseReference chipsRef = FirebaseDatabase.getInstance().getReference();
                for (String chip : selectedChips) {
                    chipsRef.child("Public_post").child(chip).child("users").updateChildren(users_update);
                }

                database.getReference().child("users").child(value)
                        .child("profiles").updateChildren(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {



                                Toast.makeText(getActivity(), "Succes", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity() , MainActivity.class);
                                startActivity(intent);

                            }
                        });


            }
        });



//                database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())

//

        return view;
    }
}