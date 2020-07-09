package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor mExecutor;


    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor){
        this.mTaskRepository = taskRepository;
        this.mProjectRepository = projectRepository;
        this.mExecutor = executor;
    }


    /**
     *  FOR PROJECT
     */
    public LiveData<List<Project>> getProjects() { return mProjectRepository.getAllProjects();}

    /**
     *  FOR TASKS
     */
    public LiveData<List<Task>> getTasks() {
        return mTaskRepository.getAllTasks();
    }

    public void createTask(Task task) {
        mExecutor.execute(() -> {
            mTaskRepository.createTask(task);
        });
    }

    public void deleteTask(long taskId) {
        mExecutor.execute(() -> {
            mTaskRepository.deleteTask(taskId);
        });
    }

}
