package com.example.pesu24.entities;

public class TacGia {
    private int id;
    private String tenTacGia;
    private String email;
    private String diaChi;
    private String dienThoai;

    public TacGia(String tenTacGia, String email, String diaChi, String dienThoai) {
        this.tenTacGia = tenTacGia;
        this.email = email;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
    }

    public TacGia(int id, String tenTacGia, String email, String diaChi, String dienThoai) {
        this.id = id;
        this.tenTacGia = tenTacGia;
        this.email = email;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
    }

    public TacGia() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }
}
