package com.example.allapps.allPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.allapps.R;
import com.example.allapps.Utils;

import java.util.List;


public class AllPackage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_all_package);
        RecyclerView recyclerView = findViewById(R.id.packageRecyclerView);
        List<ResolveInfo> applicationInfos = Utils.getAllPackage(this);
        ApplicationList applicationList=new ApplicationList(this, applicationInfos);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(applicationList);
        applicationList.notifyDataSetChanged();
    }

}
