package com.example.allapps.splitWise.adapter;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.database.DBHandler;
import com.example.allapps.R;
import com.example.allapps.splitWise.activity.SplitWise;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.allapps.MainActivity.DATABASE_NAME;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private final ArrayList<String> list;
    private final SQLiteDatabase sqLiteDatabase;

    public MainAdapter(ArrayList<String> list) {
        super();
        this.list= list;
        DBHandler dbHandler = new DBHandler(SplitWise.APP_CONTEXT, DATABASE_NAME);
        sqLiteDatabase= dbHandler.getWritableDatabase("vishnu");
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_adapter_view, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String string=list.get(position);
        int total=getTotal(string);
        holder.setTotal.setText(string+" total amount is : "+total);
    }

    private int getTotal(String title) {
        int total=0;
        try{
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ITEM(ITEM_NAME VARCHAR(30),AMOUNT VARCHAR(20),DATE VARCHAR(20),TITLE VARCHAR(20))");
            Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM ITEM WHERE TITLE= '"+title+"'",null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                total+=Integer.parseInt(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }




    @Override
    public int getItemCount() {
        return 0;
    }
    

    class CustomViewHolder extends RecyclerView.ViewHolder{
        final TextView setTotal;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            setTotal=itemView.findViewById(R.id.setTotalAmount);
        }

    }
}
