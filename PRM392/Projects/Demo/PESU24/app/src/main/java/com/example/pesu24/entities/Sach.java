package com.example.pesu24.entities;

public class Sach {
    private int id;
    private String tenSach;
    private String ngayXB;
    private String theLoai;
    private int idTacGia;
    public Sach(String tenSach, String ngayXB, String theLoai, int idTacGia) {
        this.tenSach = tenSach;
        this.ngayXB = ngayXB;
        this.theLoai = theLoai;
        this.idTacGia = idTacGia;
    }

    public Sach(int id, String tenSach, String ngayXB, String theLoai, int idTacGia) {
        this.id = id;
        this.tenSach = tenSach;
        this.ngayXB = ngayXB;
        this.theLoai = theLoai;
        this.idTacGia = idTacGia;
    }

    public Sach() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getNgayXB() {
        return ngayXB;
    }

    public void setNgayXB(String ngayXB) {
        this.ngayXB = ngayXB;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public int getIdTacGia() {
        return idTacGia;
    }

    public void setIdTacGia(int idTacGia) {
        this.idTacGia = idTacGia;
    }
}
