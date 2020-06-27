package com.example.allapps.splitWise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allapps.database.DBHandler;
import com.example.allapps.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.allapps.MainActivity.DATABASE_NAME;


public class NewItemActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name,amount;
    private TextView date;
    private final Calendar calendar=Calendar.getInstance();
    private Spinner spinner;
    private DBHandler dbHandler;
    private final DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            setDate();
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        dbHandler =new DBHandler(NewItemActivity.this, DATABASE_NAME);
        ArrayList<String> list = dbHandler.getTabList();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SplitWise.APP_CONTEXT, android.R.layout.simple_spinner_dropdown_item, list);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        setContentView(R.layout.add_new_item);
        name=findViewById(R.id.item_name);
        amount=findViewById(R.id.amount);
        date=findViewById(R.id.dataPicker);
        Button submit = findViewById(R.id.submit);
        spinner=findViewById(R.id.spinner);

        date.setOnClickListener(this);
        submit.setOnClickListener(this);
        spinner.setAdapter(arrayAdapter);
    }

    private void setDate() {
        String format="dd-MM-yy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format, Locale.US);
        date.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dataPicker:
                new DatePickerDialog(NewItemActivity.this,datePickerDialog,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.submit:
                String itemName=name.getText().toString();
                String itemAmount=amount.getText().toString();
                String itemDate=date.getText().toString();
                String spinneritem=spinner.getSelectedItem().toString().toLowerCase();

                if(!spinneritem.equalsIgnoreCase("setDate") && !itemName.equalsIgnoreCase("") && !itemAmount.equalsIgnoreCase("") && !itemDate.equalsIgnoreCase("")){
                   dbHandler.insertItem(itemName,itemAmount,itemDate,spinneritem);
                    name.setText("");
                    amount.setText("");
                    date.setText("set Date");
                    Toast.makeText(SplitWise.APP_CONTEXT,"Insert Success",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SplitWise.APP_CONTEXT,"Please Enter the data",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
