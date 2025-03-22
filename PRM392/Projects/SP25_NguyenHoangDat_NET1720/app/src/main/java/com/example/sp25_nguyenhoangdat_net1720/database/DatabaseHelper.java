package com.example.sp25_nguyenhoangdat_net1720.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table names
    public static final String TABLE_BOOK = "book";
    public static final String TABLE_AUTHOR = "author";
    // Author table columns
    public static final String COLUMN_AUTHOR_ID = "author_id";
    public static final String COLUMN_AUTHOR_NAME = "name";
    public static final String COLUMN_AUTHOR_EMAIL = "email";
    public static final String COLUMN_AUTHOR_PHONE = "phone";
    public static final String COLUMN_AUTHOR_ADDRESS = "address";
    // Book table columns
    public static final String COLUMN_BOOK_ID = "book_id";
    public static final String COLUMN_BOOK_TITLE = "book_title";
    public static final String COLUMN_PUBLICATION_DATE = "publication_date";
    public static final String COLUMN_TYPE = "type";
    // Database info
    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;
    // Create table statements
    private static final String CREATE_TABLE_AUTHOR = "CREATE TABLE " + TABLE_AUTHOR + "("
            + COLUMN_AUTHOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_AUTHOR_NAME + " TEXT NOT NULL, "
            + COLUMN_AUTHOR_EMAIL + " TEXT, "
            + COLUMN_AUTHOR_PHONE + " TEXT, "
            + COLUMN_AUTHOR_ADDRESS + " TEXT"
            + ")";

    private static final String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + "("
            + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_BOOK_TITLE + " TEXT NOT NULL, "
            + COLUMN_PUBLICATION_DATE + " TEXT, "
            + COLUMN_TYPE + " TEXT, "
            + COLUMN_AUTHOR_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_AUTHOR_ID + ") REFERENCES " + TABLE_AUTHOR + "(" + COLUMN_AUTHOR_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_AUTHOR);
        db.execSQL(CREATE_TABLE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        onCreate(db);
    }
}
