package com.example.lab10;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Person {
    @PrimaryKey
    private int uid;
    @ColumnInfo(name = "first_name")
    private String fistName;
    @ColumnInfo(name = "last_name")
    private String lastName;
    public Person(String fistName, String lastName){
        this.fistName = fistName;
        this.lastName = lastName;
    }
    public int getUid(){
        return uid;
    }
    public  void setUid(int uid){this.uid = uid;}

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
