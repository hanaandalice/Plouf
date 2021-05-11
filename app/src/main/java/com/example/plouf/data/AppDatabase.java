package com.example.plouf.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.List;

@Database(entities = PdEntity.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PdDao pdDao();
    private static AppDatabase mDb;

    public static AppDatabase getInstance(Context context){
        if(mDb == null){
            synchronized (AppDatabase.class){
                if(mDb == null) {
                    mDb = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "PD_01").build();
                }
            }

        }
        return (AppDatabase) mDb;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }


}
