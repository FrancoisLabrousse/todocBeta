package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao mTaskDao;


    public TaskRepository(TaskDao taskDao) {
        this.mTaskDao = taskDao;
    }

    // --- GET ---

    public LiveData<List<Task>> getAllTasks() {
        return this.mTaskDao.getAllTasks();
    }


    // --- CREATE ---

    public void createTask(Task task) { mTaskDao.insertTask(task);}

    // --- DELETE ---

    public void deleteTask(long taskId) { mTaskDao.deleteTask(taskId);}


}
