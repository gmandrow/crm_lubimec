package com.thrashed.lubimec_crm.DAO;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Record.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RecordDao recordDao();
}
