package com.example.SE1719_SE172258_ducthse172258.models;

public class Student {
    long ID;
    String name;
    String date;
    String gender;
    String email;
    String address;
    long id_major;

    public Student(String name, String date, String gender, String email, String address, long id_major) {
        this.name = name;
        this.date = date;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.id_major = id_major;
    }

    public Student(long ID, String name, String date, String gender, String email, String address, long id_major) {
        this.ID = ID;
        this.name = name;
        this.date = date;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.id_major = id_major;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId_major() {
        return id_major;
    }

    public void setId_major(long id_major) {
        this.id_major = id_major;
    }
}
