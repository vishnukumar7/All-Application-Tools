package com.example.allapps.genericFile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.allapps.NoSwipeAdapter;
import com.example.allapps.R;
import com.example.allapps.Utils;
import com.example.allapps.genericFile.fragments.AllFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllFiles extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView bottomNavigationView;
    private NoSwipeAdapter viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_files);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPagingEnabled(false);
        BottomBarAdapter adapter = new BottomBarAdapter(getSupportFragmentManager());
        adapter.addFragments(new AllFragment("Image", Utils.getPicturePaths(this)));
       adapter.addFragments(new AllFragment("Audio", Utils.getAudioPaths(this)));
       adapter.addFragments(new AllFragment("Video", Utils.getVideoPaths(this)));
//        adapter.addFragments(new ReportFragment());
        viewPager.setAdapter(adapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            default:
          /*  case R.id.allFiles:
                Objects.requireNonNull(getSupportActionBar()).setTitle("Files");
                viewPager.setCurrentItem(0);
                return true;*/
            case R.id.allAudio:
                Objects.requireNonNull(getSupportActionBar()).setTitle("Audio");
                viewPager.setCurrentItem(1);
                return true;
            case R.id.allImages:
                viewPager.setCurrentItem(0);
                Objects.requireNonNull(getSupportActionBar()).setTitle("Images");
                return true;
            case R.id.allVideo:
                viewPager.setCurrentItem(2);
                Objects.requireNonNull(getSupportActionBar()).setTitle("Videos");
                return true;
        }
    }

    class BottomBarAdapter extends SmartPageFragmentAdapter {
        private final List<Fragment> fragments = new ArrayList<>();

        public BottomBarAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        // Our custom method that populates this Adapter with Fragments
        public void addFragments(Fragment fragment) {
            fragments.add(fragment);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
