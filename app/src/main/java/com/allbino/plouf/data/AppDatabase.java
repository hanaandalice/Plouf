package com.allbino.plouf.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

/*####################################################################################
 *형태 : Class
 * 모듈ID : AppDatabase
 * 설명 : db 생성
 * */
@Database(entities = PdEntity.class, version = 1)
@TypeConverters(TypeConvertor.class)
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
