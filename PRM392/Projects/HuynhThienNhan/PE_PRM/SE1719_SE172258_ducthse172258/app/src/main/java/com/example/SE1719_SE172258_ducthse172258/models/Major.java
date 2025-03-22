package com.example.SE1719_SE172258_ducthse172258.models;

public class Major {
    long ID;
    String name_Major;

    public Major(long ID, String name_Major) {
        this.ID = ID;
        this.name_Major = name_Major;
    }

    public Major(String name_Major) {
        this.name_Major = name_Major;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName_Major() {
        return name_Major;
    }

    public void setName_Major(String name_Major) {
        this.name_Major = name_Major;
    }
}
