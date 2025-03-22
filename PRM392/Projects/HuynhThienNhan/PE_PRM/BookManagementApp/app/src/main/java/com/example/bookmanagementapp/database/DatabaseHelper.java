package com.example.bookmanagementapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BookManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng Author
    private static final String TABLE_AUTHOR = "Author";
    private static final String COLUMN_AUTHOR_ID = "Author_ID";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_ADDRESS = "address";

    // Bảng Book
    private static final String TABLE_BOOK = "Book";
    private static final String COLUMN_BOOK_ID = "Book_ID";
    private static final String COLUMN_BOOK_TITLE = "book_title";
    private static final String COLUMN_PUBLICATION_DATE = "publication_date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_BOOK_AUTHOR_ID = "Author_ID";

    private static final String CREATE_TABLE_AUTHOR = "CREATE TABLE " + TABLE_AUTHOR + " (" +
            COLUMN_AUTHOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_PHONE + " TEXT, " +
            COLUMN_ADDRESS + " TEXT)";

    private static final String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + " (" +
            COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BOOK_TITLE + " TEXT, " +
            COLUMN_PUBLICATION_DATE + " TEXT, " +
            COLUMN_TYPE + " TEXT, " +
            COLUMN_BOOK_AUTHOR_ID + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_BOOK_AUTHOR_ID + ") REFERENCES " + TABLE_AUTHOR + "(" + COLUMN_AUTHOR_ID + "))";

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
