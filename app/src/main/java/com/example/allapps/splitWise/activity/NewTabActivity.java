package com.example.allapps.splitWise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.allapps.database.DBHandler;
import com.example.allapps.R;

import java.util.ArrayList;

import static com.example.allapps.MainActivity.DATABASE_NAME;

public class NewTabActivity extends AppCompatActivity {
    private EditText editText;
    private ArrayList<String> arrayTitleList;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.add_new_tab_view);
        editText= findViewById(R.id.title);
        dbHandler=new DBHandler(NewTabActivity.this, DATABASE_NAME);
        final Button button = findViewById(R.id.submit);

        arrayTitleList=dbHandler.getTabList();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=editText.getText().toString().trim();

                if(arrayTitleList!=null && arrayTitleList.contains(title)){
                    Toast.makeText(NewTabActivity.this,"Title already exists",Toast.LENGTH_LONG).show();
                }
                else if(arrayTitleList!=null){
                    arrayTitleList.add(title);
                    editText.setText("");
                    dbHandler.insertAddNewTab(title);
                    Toast.makeText(NewTabActivity.this,"New Tab add Success",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
