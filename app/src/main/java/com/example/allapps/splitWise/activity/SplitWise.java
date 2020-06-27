package com.example.allapps.splitWise.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.allapps.R;


public class SplitWise extends AppCompatActivity implements View.OnClickListener {


    public static Context APP_CONTEXT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_splitwise);
        APP_CONTEXT = getApplicationContext();


        CardView allDetails = findViewById(R.id.allDetails);
        CardView spentAmount = findViewById(R.id.spentAmount);
        CardView addItem = findViewById(R.id.addNewITem);
        CardView addList = findViewById(R.id.addNewList);
        spentAmount.setOnClickListener(this);
        addItem.setOnClickListener(this);
        addList.setOnClickListener(this);
        allDetails.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allDetails:
                Intent allDetails = new Intent(SplitWise.this, HomeViewActivity.class);
                allDetails.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(allDetails);
                break;
            case R.id.spentAmount:
                Intent view = new Intent(SplitWise.this, ViewItemActivity.class);
                view.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(view);
                break;
            case R.id.addNewITem:
                Intent item = new Intent(SplitWise.this, NewItemActivity.class);
                item.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(item);
                break;
            case R.id.addNewList:
                Intent tab = new Intent(SplitWise.this, NewTabActivity.class);
                tab.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(tab);
                break;
        }
    }


}
