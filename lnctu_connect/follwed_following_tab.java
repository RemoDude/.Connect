package com.example.lnctu_connect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.lnctu_connect.Adapters.Adapter_of_followList;
import com.example.lnctu_connect.Adapters.follow_following_Adapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class follwed_following_tab extends AppCompatActivity {

    TabLayout tabLayout;
    CustomViewPager viewPager;
    MaterialToolbar toolbar;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follwed_following_tab);


        getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);



        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.toolbar);

 String title = (String) FirebaseDataSingleton.getInstance().getData().get("uniq_name");

        toolbar.setTitle(title);



        int selectedTab = getIntent().getIntExtra("selected_tab", 0);



        final follow_following_Adapter adapter = new follow_following_Adapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);




        viewPager.setCurrentItem(selectedTab);



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




    }
}