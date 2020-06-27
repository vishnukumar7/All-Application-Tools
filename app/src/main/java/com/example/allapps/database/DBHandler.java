package com.example.allapps.database;

import android.content.ContentValues;
import android.content.Context;
import android.os.Build;

import com.example.allapps.splitWise.Item;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {



    public DBHandler(Context context, String DATABASE_NAME) {
        //Create the Database
        super(new DataBaseContext(context), DATABASE_NAME, null, 3);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SQLiteDatabase.loadLibs(context, context.getDataDir());
        } else {
            SQLiteDatabase.loadLibs(context);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS AddNewTab(title text)");
        database.execSQL("CREATE TABLE IF NOT EXISTS ITEM(ITEM_NAME VARCHAR(30),AMOUNT VARCHAR(20),DATE VARCHAR(20),TITLE VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }




    public void insertAddNewTab(String title) {

        SQLiteDatabase database = this.getWritableDatabase("Vishnu");
        database.execSQL("CREATE TABLE IF NOT EXISTS AddNewTab(title text)");
        ContentValues values = new ContentValues();
        values.put("title", title);
        database.insert("AddNewTab", null, values);
    }

    public ArrayList<String> getTabList() {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase("Vishnu");
        database.execSQL("CREATE TABLE IF NOT EXISTS AddNewTab(title text)");
        Cursor cursor = database.rawQuery("SELECT * FROM AddNewTab", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("title")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public void insertItem(String name, String amount, String date, String title) {
        SQLiteDatabase database = this.getWritableDatabase("Vishnu");
        database.execSQL("CREATE TABLE IF NOT EXISTS ITEM(ITEM_NAME VARCHAR(30),AMOUNT VARCHAR(20),DATE VARCHAR(20),TITLE VARCHAR(20))");
        ContentValues cv = new ContentValues();
        cv.put("ITEM_NAME", name);
        cv.put("AMOUNT", amount);
        cv.put("DATE", date);
        cv.put("TITLE", title.toLowerCase());
        database.insert("ITEM", null, cv);
        database.close();
    }

    public ArrayList<Item> getItemArrayList(String title) {
        ArrayList<Item> items = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase("Vishnu");
        database.execSQL("CREATE TABLE IF NOT EXISTS ITEM(ITEM_NAME VARCHAR(30),AMOUNT VARCHAR(20),DATE VARCHAR(20),TITLE VARCHAR(20))");
        Cursor cursor = database.rawQuery("SELECT * FROM ITEM WHERE TITLE= '" + title.toLowerCase() + "'", null);
        cursor.moveToFirst();
        System.out.println("://title : " + title);
        printColumnList("ITEM");
        while (!cursor.isAfterLast()) {
            Item item = new Item();
            item.setName(cursor.getString(0));
            item.setAmount(cursor.getString(1));
            item.setDate(cursor.getString(2));
            items.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();

        return items;
    }

    private void printColumnList(String table) {
        SQLiteDatabase database = this.getWritableDatabase("Vishnu");
        Cursor cursor = database.rawQuery("SELECT * FROM " + table, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                System.out.print("://" + cursor.getString(i) + " ");
            }
            System.out.println();
            cursor.moveToNext();
        }
    }

    public ArrayList<String> fromAllTable(){
        ArrayList<String> arrayList=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase("Vishnu");
        Cursor cursor=database.rawQuery("SELECT name from sqlite_master WHERE type='table'",null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0)
        while (!cursor.isAfterLast()){
            String tableName=cursor.getString(cursor.getColumnIndex("name"));
            System.out.println("//tables : "+tableName);
            arrayList.addAll(displayAllData(tableName));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return arrayList;
    }

    private ArrayList<String> displayAllData(String tableName){
        ArrayList<String>  arrayList=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase("Vishnu");
        Cursor cursor=database.rawQuery("SELECT * FROM "+tableName,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            int columnCount=cursor.getColumnCount();
            while (!cursor.isAfterLast()){
                for(int i=0;i<columnCount;i++)
                {
                    String data=cursor.getString(i);
                    System.out.println("//data : "+data);
                    arrayList.add(data);
                }
                cursor.moveToNext();
            }
        }
        return arrayList;
    }

}



