package com.example.allapps.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.example.allapps.R;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScanActivity extends AppCompatActivity implements DecodeCallback, ErrorCallback {


    private static final String[] PERMISSIONS_ALL = {android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.CAMERA};
    private static ProgressDialog progressDialog;
    private CodeScanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        askForPermission();
        progressDialog = new ProgressDialog(this);

        final CodeScannerView scannerView = findViewById(R.id.scanner_view);
        scanner = new CodeScanner(this, scannerView);
        scanner.setDecodeCallback(this);
        scanner.setErrorCallback(this);

    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public void onDecoded(@NonNull final Result result) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Validating credentials");
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                Toast.makeText(ScanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                System.out.println("//result : " + result.getText());
            }
        });
    }

    private void checkConnection() {
        new AlertDialog.Builder(ScanActivity.this)
                .setTitle("Network Failed")
                .setMessage("Kindly check your Internet connection and Try again")
                .setCancelable(false)
                .setPositiveButton("Try again", null)
                .show();

    }

    @Override
    public void onError(@NonNull Exception error) {
        System.out.println("//exception error : " + error.toString());
    }


    @Override
    protected void onPause() {
        super.onPause();
        scanner.releaseResources();

    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner.startPreview();

    }

    private void askForPermission() {
        for (String permission : PERMISSIONS_ALL) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {

                    //This is called if user has denied the permission before
                    //In this case I am just asking the permission again

                    ActivityCompat.requestPermissions(this, new String[]{permission}, 1);

                } else {
                    // cameraSource.release();
                    ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        recreate();
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.e("value", "Permission Granted");
        } else {
            Log.e("value", "Permission Denied");
        }
    }

    @SuppressWarnings("Convert2Lambda")
    private void scanAgain() {
        new AlertDialog.Builder(ScanActivity.this)
                .setTitle("Registration Failed")
                .setCancelable(false)
                .setMessage("Student registration is not success.If you want to scan again please press YES button.")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        moveTaskToBack(true);
                        ScanActivity.this.finish();
                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        recreate();
                    }
                })
                .show();
    }


}