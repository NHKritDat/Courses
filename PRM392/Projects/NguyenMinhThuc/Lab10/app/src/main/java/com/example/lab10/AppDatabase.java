package com.example.lab10;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.lab10.Person;
import com.example.lab10.PersonDAO;
@Database(entities = {Person.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public  abstract PersonDAO personDAO();
}
