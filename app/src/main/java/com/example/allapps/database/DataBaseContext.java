package com.example.allapps.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import java.io.File;

public class DataBaseContext extends ContextWrapper {
    public static final String FILE_STORAGE_APP="WebService";

    public DataBaseContext(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        File sdcard = Environment.getExternalStorageDirectory();
        String dbFile = sdcard.getAbsolutePath() + File.separator +FILE_STORAGE_APP+File.separator+ "databases" + File.separator + name;
        if (!dbFile.endsWith(".db")) {
            dbFile += ".db";
        }

        File result = new File(dbFile);

        if (!result.getParentFile().exists()) {
            result.getParentFile().mkdirs();
        }
        return result;
    }


    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(name, mode, factory);
    }


    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }
}