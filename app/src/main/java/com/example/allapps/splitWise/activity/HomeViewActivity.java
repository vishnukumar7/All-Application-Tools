package com.example.allapps.splitWise.activity;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.R;
import com.example.allapps.splitWise.adapter.MainAdapter;

import java.util.ArrayList;

public class HomeViewActivity extends AppCompatActivity {


    private MainAdapter mainAdapter;
    private ArrayList<String> list;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_home);

    }


}
