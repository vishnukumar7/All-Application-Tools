package com.example.allapps.Other;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.allapps.R;
import com.example.allapps.database.DBHandler;

import java.util.ArrayList;


import static com.example.allapps.MainActivity.DATABASE_NAME;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ListView list;
    ListViewAdapter adapter;

    ArrayList<String> arraylist = new ArrayList<>();
    SearchView searchView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        list = findViewById(R.id.listview);
        arraylist=new DBHandler(this,DATABASE_NAME).fromAllTable();

        adapter = new ListViewAdapter(this, arraylist);
        list.setAdapter(adapter);
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(!newText.equalsIgnoreCase(""))
        {
            list.setVisibility(View.VISIBLE);
            adapter.filter(newText);
        }
        else
            list.setVisibility(View.GONE);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
