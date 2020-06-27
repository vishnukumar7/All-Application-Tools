package com.example.allapps.recyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.allapps.R;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class RecyclerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_recyler_main);
        askForPermission();
        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        viewPager.setClipToPadding(false);

//        viewPager.setPageTransformer(true,new CustPagerTransformer(this));
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.addOnTabSelectedListener();


    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Tab("Employee"), "Employee");
        //adapter.addFrag(new Tab("Download"),"Download");
        adapter.addFrag(new Tab("Marvel"), "Marvel");
        adapter.addFrag(new Tab("DemoNuts"), "DemoNuts");
        //adapter.addFrag(new Tab("Coming soon"), "Coming soon");
        viewPager.setAdapter(adapter);
    }

    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(RecyclerMain.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RecyclerMain.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again

                ActivityCompat.requestPermissions(RecyclerMain.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            } else {

                ActivityCompat.requestPermissions(RecyclerMain.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();//fragment arrayList
        private final List<String> mFragmentTitleList = new ArrayList<>();//title arrayList

        ViewPagerAdapter(FragmentManager manager) {
            super(manager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        //adding fragments and title method
        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
