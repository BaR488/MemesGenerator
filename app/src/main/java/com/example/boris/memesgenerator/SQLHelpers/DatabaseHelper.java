package com.example.boris.memesgenerator.SQLHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;

/**
 * Created by boris on 15.07.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "memesGenerator.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE = "memes"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RESOURCE = "recource";
    public static final String COLUMN_TAGS = "tags";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_RESOURCE
                + " TEXT, " + COLUMN_TAGS + " INTEGER);");
        // добавление начальных данных
        Field[] drawables = android.R.drawable.class.getFields();
        for (Field f : drawables) {
            try {
                db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_RESOURCE
                        + ", " + COLUMN_TAGS  + ") VALUES (" + f +", 1981);");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
