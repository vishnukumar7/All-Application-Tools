package com.example.allapps;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.allapps.Other.SearchActivity;
import com.example.allapps.allPackage.AllPackage;
import com.example.allapps.fileManager.FileManagerActivity;
import com.example.allapps.genericFile.AllFiles;
import com.example.allapps.recyclerView.RecyclerMain;
import com.example.allapps.splitWise.activity.SplitWise;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATABASE_NAME = "WebService.db";

    private LinearLayout camera, music, setting, allPackage, recyclerView, split, fileManager, chess;

    private String[] PERMISSION_ALL = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            android.Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.MODIFY_PHONE_STATE,
            Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        requestPermission();
        initView();

        camera.setOnClickListener(this);
        music.setOnClickListener(this);
        setting.setOnClickListener(this);
        allPackage.setOnClickListener(this);
        recyclerView.setOnClickListener(this);
        split.setOnClickListener(this);
        fileManager.setOnClickListener(this);
        chess.setOnClickListener(this);

    }

    private void initView() {
        camera = findViewById(R.id.nav_camera);
        music = findViewById(R.id.nav_music);
        setting = findViewById(R.id.nav_setting);
        allPackage = findViewById(R.id.nav_all_package);
        recyclerView = findViewById(R.id.nav_recyclerView);
        split = findViewById(R.id.nav_splitwise);
        fileManager = findViewById(R.id.nav_file_manager);
        chess = findViewById(R.id.chess);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Exit application?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .show();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_camera:
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                camera.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(camera);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
                break;
            case R.id.nav_music:
                Intent gallery = new Intent(this, AllFiles.class);
                gallery.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(gallery);
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
                break;
            case R.id.nav_recyclerView:
                Intent recyclerMain = new Intent(this, RecyclerMain.class);
                recyclerMain.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(recyclerMain);
                overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                break;
            case R.id.nav_splitwise:
                Intent splitWise = new Intent(this, SplitWise.class);
                splitWise.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(splitWise);
                overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                break;
            case R.id.nav_all_package:
                Intent allPackage = new Intent(this, AllPackage.class);
                allPackage.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(allPackage);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
            case R.id.nav_setting:
                Intent settings = new Intent(Settings.ACTION_SETTINGS);
                settings.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(settings);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                break;
            case R.id.nav_file_manager:
                Intent fileManager = new Intent(this, FileManagerActivity.class);
                fileManager.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(fileManager);
                overridePendingTransition(R.anim.top_bottom, R.anim.bottom_top);
                break;
            case R.id.chess:
                Intent chess = new Intent(this, SearchActivity.class);
                chess.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(chess);
                break;
        }

    }


    private void requestPermission() {

        for (String permission : PERMISSION_ALL) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                Toast.makeText(MainActivity.this, "" + permission, Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, PERMISSION_ALL, 111);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("value", "Permission Granted, Now you can use local drive .");
        }
    }


}
