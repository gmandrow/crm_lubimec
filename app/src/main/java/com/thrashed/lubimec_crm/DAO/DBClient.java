package com.thrashed.lubimec_crm.DAO;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DBClient {


    private Context mCtx;
    private static DBClient mInstance;

    //our app database object
    private AppDatabase appDatabase;

    private DBClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "records").build();
    }

    public static synchronized DBClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DBClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

}
