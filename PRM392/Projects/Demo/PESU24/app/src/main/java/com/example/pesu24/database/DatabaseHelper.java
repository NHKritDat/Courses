package com.example.pesu24.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Names
    public static final String TABLE_TACGIA = "tacgia";
    public static final String TABLE_SACH = "sach";
    // Common column names
    public static final String KEY_ID = "id";
    // TACGIA Table - column names
    public static final String KEY_TACGIA_ID = "id_tacgia";
    public static final String KEY_TACGIA_TEN = "ten_tacgia";
    public static final String KEY_TACGIA_EMAIL = "email";
    public static final String KEY_TACGIA_DIACHI = "dia_chi";
    public static final String KEY_TACGIA_DIENTHOAI = "dien_thoai";
    // SACH Table - column names
    public static final String KEY_SACH_ID = "id_sach";
    public static final String KEY_SACH_TEN = "ten_sach";
    public static final String KEY_SACH_NGAYXB = "ngay_xb";
    public static final String KEY_SACH_THELOAI = "the_loai";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "QuanLySach";
    // Create table statements
    private static final String CREATE_TABLE_TACGIA = "CREATE TABLE "
            + TABLE_TACGIA + "("
            + KEY_TACGIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_TACGIA_TEN + " TEXT,"
            + KEY_TACGIA_EMAIL + " TEXT,"
            + KEY_TACGIA_DIACHI + " TEXT,"
            + KEY_TACGIA_DIENTHOAI + " TEXT" + ")";

    private static final String CREATE_TABLE_SACH = "CREATE TABLE "
            + TABLE_SACH + "("
            + KEY_SACH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_SACH_TEN + " TEXT,"
            + KEY_SACH_NGAYXB + " TEXT,"
            + KEY_SACH_THELOAI + " TEXT,"
            + KEY_TACGIA_ID + " INTEGER,"
            + "FOREIGN KEY(" + KEY_TACGIA_ID + ") REFERENCES "
            + TABLE_TACGIA + "(" + KEY_TACGIA_ID + ")" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating required tables
        db.execSQL(CREATE_TABLE_TACGIA);
        db.execSQL(CREATE_TABLE_SACH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // On upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SACH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TACGIA);

        // Create new tables
        onCreate(db);
    }
}
