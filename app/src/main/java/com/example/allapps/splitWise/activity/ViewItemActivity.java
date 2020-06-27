package com.example.allapps.splitWise.activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.allapps.database.DBHandler;
import com.example.allapps.R;

import com.example.allapps.splitWise.adapter.ViewPagerAdapter;
import com.example.allapps.splitWise.fragment.Tab;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static com.example.allapps.MainActivity.DATABASE_NAME;

public class ViewItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.tab_activity_view);
        DBHandler dbHandler=new DBHandler(ViewItemActivity.this, DATABASE_NAME);
        ArrayList<String> arrayTitleList = dbHandler.getTabList();
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setupViewPager(viewPager, arrayTitleList);
    }

    private void setupViewPager(ViewPager viewPager, ArrayList<String> list) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (String title : list)
            adapter.addFragment(new Tab(title), title);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
