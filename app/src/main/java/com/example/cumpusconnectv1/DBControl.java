package com.example.cumpusconnectv1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBControl extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "your_database_name";
    private static final int DATABASE_VERSION = 1;

    // cleanerInfo tablosunu oluşturacak olan SQL sorgusu
    private static final String CREATE_CLEANER_TABLE =
            "CREATE TABLE cleanerInfo (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "firstName TEXT," +
                    "secondName TEXT," +
                    "mail TEXT," +
                    "phoneNumber TEXT," +
                    "password TEXT," +
                    "age TEXT," +
                    "gend TEXT)" ;


    // universityInfo tablosunu oluşturacak olan SQL sorgusu
    private static final String CREATE_UNIVERSITY_TABLE =
            "CREATE TABLE universityInfo (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "univers TEXT," +
                    "fakulte TEXT," +
                    "bolum TEXT," +
                    "sinif TEXT)";

    private static final String CREATE_HOBBY_TABLE =
            "CREATE TABLE hobbyInfo (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "hobi TEXT," +
                    "mevki TEXT)";

    public DBControl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // cleanerInfo tablosunu oluştur
        db.execSQL(CREATE_CLEANER_TABLE);
        db.execSQL(CREATE_UNIVERSITY_TABLE);
        db.execSQL(CREATE_HOBBY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Veritabanı sürümü yükseltildiğinde gerekli güncelleme işlemleri yapılabilir
    }
}

