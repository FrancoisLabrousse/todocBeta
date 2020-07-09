package com.cleanup.todoc.database.dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;


@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TaskRoomDataBase extends RoomDatabase {

    // --- DAO ---
    public abstract TaskDao mTaskDao();
    public abstract ProjectDao mProjectDao();

    // --- SINGLETON ---
    private static volatile TaskRoomDataBase INSTANCE;

    // --- INSTANCE ---
    public static TaskRoomDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (TaskRoomDataBase.class) {
                if (INSTANCE == null) {
                    // create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDataBase.class, "Database.db")
                            .addCallback(sRoomDatabaseCallback())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Project[] projects = Project.getAllProjects();

                for (int i = 0; i <= 2; i++) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", projects[i].getId());
                    contentValues.put("name", projects[i].getName());
                    contentValues.put("color", projects[i].getColor());

                    db.insert("Project", OnConflictStrategy.IGNORE, contentValues);

                }
            }
        };
    }
}



