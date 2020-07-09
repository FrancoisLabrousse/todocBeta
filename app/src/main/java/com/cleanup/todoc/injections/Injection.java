package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.database.dao.TaskRoomDataBase;
import com.cleanup.todoc.repositories.ProjectRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskRepository sTaskRepository(Context context) {
        TaskRoomDataBase dataBase = TaskRoomDataBase.getDatabase(context);
        return new TaskRepository(dataBase.mTaskDao());
    }

    public static ProjectRepository sProjectRepository(Context context) {
        TaskRoomDataBase dataBase = TaskRoomDataBase.getDatabase(context);
        return new ProjectRepository(dataBase.mProjectDao());
    }

    public static Executor sExecutor() { return Executors.newSingleThreadExecutor();}

    public static ViewModelFactory sViewModelFactory(Context context) {
        TaskRepository dataSourceTask = sTaskRepository(context);
        ProjectRepository dataSourceProject = sProjectRepository(context);
        Executor executor = sExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}
