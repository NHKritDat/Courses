package com.example.pesu24.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pesu24.entities.Sach;
import com.example.pesu24.entities.TacGia;

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

    // ---------- TacGia CRUD Operations ---------- //

    // Create a new TacGia
    public long createTacGia(TacGia tacGia) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_TACGIA_TEN, tacGia.getTenTacGia());
        values.put(DatabaseHelper.KEY_TACGIA_EMAIL, tacGia.getEmail());
        values.put(DatabaseHelper.KEY_TACGIA_DIACHI, tacGia.getDiaChi());
        values.put(DatabaseHelper.KEY_TACGIA_DIENTHOAI, tacGia.getDienThoai());

        // Inserting Row
        return database.insert(DatabaseHelper.TABLE_TACGIA, null, values);
    }

    // Get a single TacGia
    public TacGia getTacGia(int id) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_TACGIA,
                new String[]{DatabaseHelper.KEY_TACGIA_ID,
                        DatabaseHelper.KEY_TACGIA_TEN,
                        DatabaseHelper.KEY_TACGIA_EMAIL,
                        DatabaseHelper.KEY_TACGIA_DIACHI,
                        DatabaseHelper.KEY_TACGIA_DIENTHOAI},
                DatabaseHelper.KEY_TACGIA_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        TacGia tacGia = new TacGia();
        tacGia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_ID)));
        tacGia.setTenTacGia(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_TEN)));
        tacGia.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_EMAIL)));
        tacGia.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_DIACHI)));
        tacGia.setDienThoai(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_DIENTHOAI)));

        cursor.close();
        return tacGia;
    }

    // Get all TacGia
    public List<TacGia> getAllTacGia() {
        List<TacGia> tacGiaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_TACGIA;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TacGia tacGia = new TacGia();
                tacGia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_ID)));
                tacGia.setTenTacGia(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_TEN)));
                tacGia.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_EMAIL)));
                tacGia.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_DIACHI)));
                tacGia.setDienThoai(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_DIENTHOAI)));

                tacGiaList.add(tacGia);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return tacGiaList;
    }

    // Update a TacGia
    public int updateTacGia(TacGia tacGia) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_TACGIA_TEN, tacGia.getTenTacGia());
        values.put(DatabaseHelper.KEY_TACGIA_EMAIL, tacGia.getEmail());
        values.put(DatabaseHelper.KEY_TACGIA_DIACHI, tacGia.getDiaChi());
        values.put(DatabaseHelper.KEY_TACGIA_DIENTHOAI, tacGia.getDienThoai());

        return database.update(DatabaseHelper.TABLE_TACGIA, values,
                DatabaseHelper.KEY_TACGIA_ID + " = ?",
                new String[]{String.valueOf(tacGia.getId())});
    }

    // Delete a TacGia
    public void deleteTacGia(long id) {
        database.delete(DatabaseHelper.TABLE_TACGIA,
                DatabaseHelper.KEY_TACGIA_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // ---------- Sach CRUD Operations ---------- //

    // Create a new Sach
    public long createSach(Sach sach) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_SACH_TEN, sach.getTenSach());
        values.put(DatabaseHelper.KEY_SACH_NGAYXB, sach.getNgayXB());
        values.put(DatabaseHelper.KEY_SACH_THELOAI, sach.getTheLoai());
        values.put(DatabaseHelper.KEY_TACGIA_ID, sach.getIdTacGia());

        return database.insert(DatabaseHelper.TABLE_SACH, null, values);
    }

    // Get a single Sach
    public Sach getSach(int id) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_SACH,
                new String[]{DatabaseHelper.KEY_SACH_ID,
                        DatabaseHelper.KEY_SACH_TEN,
                        DatabaseHelper.KEY_SACH_NGAYXB,
                        DatabaseHelper.KEY_SACH_THELOAI,
                        DatabaseHelper.KEY_TACGIA_ID},
                DatabaseHelper.KEY_SACH_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Sach sach = new Sach();
        sach.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_ID)));
        sach.setTenSach(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_TEN)));
        sach.setNgayXB(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_NGAYXB)));
        sach.setTheLoai(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_THELOAI)));
        sach.setIdTacGia(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_ID)));

        cursor.close();
        return sach;
    }

    // Get all Sach
    public List<Sach> getAllSach() {
        List<Sach> sachList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_SACH;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Sach sach = new Sach();
                sach.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_ID)));
                sach.setTenSach(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_TEN)));
                sach.setNgayXB(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_NGAYXB)));
                sach.setTheLoai(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_THELOAI)));
                sach.setIdTacGia(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_ID)));

                sachList.add(sach);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return sachList;
    }

    // Update a Sach
    public int updateSach(Sach sach) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_SACH_TEN, sach.getTenSach());
        values.put(DatabaseHelper.KEY_SACH_NGAYXB, sach.getNgayXB());
        values.put(DatabaseHelper.KEY_SACH_THELOAI, sach.getTheLoai());
        values.put(DatabaseHelper.KEY_TACGIA_ID, sach.getIdTacGia());

        return database.update(DatabaseHelper.TABLE_SACH, values,
                DatabaseHelper.KEY_SACH_ID + " = ?",
                new String[]{String.valueOf(sach.getId())});
    }

    // Delete a Sach
    public void deleteSach(long id) {
        database.delete(DatabaseHelper.TABLE_SACH,
                DatabaseHelper.KEY_SACH_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // Get all books by a specific author
    public List<Sach> getSachByTacGiaId(int tacGiaId) {
        List<Sach> sachList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_SACH +
                " WHERE " + DatabaseHelper.KEY_TACGIA_ID + " = " + tacGiaId;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Sach sach = new Sach();
                sach.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_ID)));
                sach.setTenSach(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_TEN)));
                sach.setNgayXB(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_NGAYXB)));
                sach.setTheLoai(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_SACH_THELOAI)));
                sach.setIdTacGia(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_TACGIA_ID)));

                sachList.add(sach);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return sachList;
    }

    // Delete all books by a specific author
    public void deleteSachByTacGiaId(int tacGiaId) {
        database.delete(DatabaseHelper.TABLE_SACH,
                DatabaseHelper.KEY_TACGIA_ID + " = ?",
                new String[]{String.valueOf(tacGiaId)});
    }
}
