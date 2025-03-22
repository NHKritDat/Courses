package com.example.sp25_nguyenhoangdat_net1720.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sp25_nguyenhoangdat_net1720.entities.Author;
import com.example.sp25_nguyenhoangdat_net1720.entities.Book;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // ---------- Author CRUD Operations ---------- //
    public long createAuthor(Author author) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_AUTHOR_NAME, author.getName());
        values.put(DatabaseHelper.COLUMN_AUTHOR_EMAIL, author.getEmail());
        values.put(DatabaseHelper.COLUMN_AUTHOR_PHONE, author.getPhone());
        values.put(DatabaseHelper.COLUMN_AUTHOR_ADDRESS, author.getAddress());

        // Inserting Row
        return database.insert(DatabaseHelper.TABLE_AUTHOR, null, values);
    }

    public Author getAuthor(int id) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_AUTHOR,
                new String[]{DatabaseHelper.COLUMN_AUTHOR_ID,
                        DatabaseHelper.COLUMN_AUTHOR_NAME,
                        DatabaseHelper.COLUMN_AUTHOR_EMAIL,
                        DatabaseHelper.COLUMN_AUTHOR_PHONE,
                        DatabaseHelper.COLUMN_AUTHOR_ADDRESS},
                DatabaseHelper.COLUMN_AUTHOR_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Author author = new Author();
        author.setAuthorId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_ID)));
        author.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_NAME)));
        author.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_EMAIL)));
        author.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_PHONE)));
        author.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_ADDRESS)));

        cursor.close();
        return author;
    }

    public List<Author> getAllAuthor() {
        List<Author> tacGiaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_AUTHOR;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Author author = new Author();
                author.setAuthorId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_ID)));
                author.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_NAME)));
                author.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_EMAIL)));
                author.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_PHONE)));
                author.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_ADDRESS)));

                tacGiaList.add(author);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return tacGiaList;
    }

    public int updateAuthor(Author author) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_AUTHOR_NAME, author.getName());
        values.put(DatabaseHelper.COLUMN_AUTHOR_EMAIL, author.getEmail());
        values.put(DatabaseHelper.COLUMN_AUTHOR_PHONE, author.getPhone());
        values.put(DatabaseHelper.COLUMN_AUTHOR_ADDRESS, author.getAddress());

        return database.update(DatabaseHelper.TABLE_AUTHOR, values,
                DatabaseHelper.COLUMN_AUTHOR_ID + " = ?",
                new String[]{String.valueOf(author.getAuthorId())});
    }

    public void deleteAuthor(long id) {
        database.delete(DatabaseHelper.TABLE_AUTHOR,
                DatabaseHelper.COLUMN_AUTHOR_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // ---------- Book CRUD Operations ---------- //
    public long createBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOK_TITLE, book.getBookTitle());
        values.put(DatabaseHelper.COLUMN_PUBLICATION_DATE, book.getPublishDate());
        values.put(DatabaseHelper.COLUMN_TYPE, book.getType());
        values.put(DatabaseHelper.COLUMN_AUTHOR_ID, book.getAuthorId());

        // Inserting Row
        return database.insert(DatabaseHelper.TABLE_BOOK, null, values);
    }

    public Book getBook(int id) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_BOOK,
                new String[]{DatabaseHelper.COLUMN_BOOK_ID,
                        DatabaseHelper.COLUMN_BOOK_TITLE,
                        DatabaseHelper.COLUMN_PUBLICATION_DATE,
                        DatabaseHelper.COLUMN_TYPE,
                        DatabaseHelper.COLUMN_AUTHOR_ID},
                DatabaseHelper.COLUMN_BOOK_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Book book = new Book();
        book.setBookId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_ID)));
        book.setBookTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_TITLE)));
        book.setPublishDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PUBLICATION_DATE)));
        book.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE)));
        book.setAuthorId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_ID)));

        cursor.close();
        return book;
    }

    public List<Book> getAllBook() {
        List<Book> bookList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_BOOK;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setBookId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_ID)));
                book.setBookTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_TITLE)));
                book.setPublishDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PUBLICATION_DATE)));
                book.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE)));
                book.setAuthorId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_ID)));

                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bookList;
    }

    public int updateBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOK_TITLE, book.getBookTitle());
        values.put(DatabaseHelper.COLUMN_PUBLICATION_DATE, book.getPublishDate());
        values.put(DatabaseHelper.COLUMN_TYPE, book.getType());
        values.put(DatabaseHelper.COLUMN_AUTHOR_ID, book.getAuthorId());

        return database.update(DatabaseHelper.TABLE_BOOK, values,
                DatabaseHelper.COLUMN_BOOK_ID + " = ?",
                new String[]{String.valueOf(book.getBookId())});
    }

    public void deleteBook(long id) {
        database.delete(DatabaseHelper.TABLE_BOOK,
                DatabaseHelper.COLUMN_BOOK_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public List<Book> getBooksByAuthor(int authorId) {
        List<Book> bookList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_BOOK + " WHERE " + DatabaseHelper.COLUMN_AUTHOR_ID + " = " + authorId;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setBookId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_ID)));
                book.setBookTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_TITLE)));
                book.setPublishDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PUBLICATION_DATE)));
                book.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE)));
                book.setAuthorId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_ID)));

                bookList.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bookList;
    }

    public void deleteBookByAuthorId(int authorId) {
        database.delete(DatabaseHelper.TABLE_BOOK,
                DatabaseHelper.COLUMN_AUTHOR_ID + " = ?",
                new String[]{String.valueOf(authorId)});
    }
}
