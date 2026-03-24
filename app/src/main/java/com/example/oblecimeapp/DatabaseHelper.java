package com.example.oblecimeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userDB";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IMAGE_PATH = "image_path";

    public static final String TABLE_CLOTHES = "clothes";
    public static final String COLUMN_CLOTHES_ID = "id";
    public static final String COLUMN_CLOTHES_EMAIL = "email";
    public static final String COLUMN_CLOTHES_IMAGE_URI = "image_uri";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_IMAGE_PATH + " TEXT)";

    private static final String CREATE_TABLE_CLOTHES =
            "CREATE TABLE " + TABLE_CLOTHES + " (" +
                    COLUMN_CLOTHES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CLOTHES_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_CLOTHES_IMAGE_URI + " TEXT NOT NULL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CLOTHES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHES);
        onCreate(db);
    }

    public void addUser(String email, String password, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_IMAGE_PATH, imagePath);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public boolean addClothing(String email, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOTHES_EMAIL, email);
        values.put(COLUMN_CLOTHES_IMAGE_URI, imageUri);
        long result = db.insert(TABLE_CLOTHES, null, values);
        db.close();
        return result != -1;
    }

    public List<String> getAllImages(String email) {
        List<String> imageList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_CLOTHES_IMAGE_URI + " FROM " + TABLE_CLOTHES + " WHERE " + COLUMN_CLOTHES_EMAIL + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor.moveToFirst()) {
            do {
                imageList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return imageList;
    }
    public boolean deleteClothing(String email, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CLOTHES, COLUMN_CLOTHES_EMAIL + "=? AND " + COLUMN_CLOTHES_IMAGE_URI + "=?", new String[]{email, imageUri});
        db.close();
        return result > 0; // Returns true if the deletion was successful
    }

}
