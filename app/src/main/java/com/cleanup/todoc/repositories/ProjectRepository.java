package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private ProjectDao mProjectDao;

    public ProjectRepository(ProjectDao projectDao) { this.mProjectDao = projectDao;}


    // --- GET PROJECT ---
    public LiveData<List<Project>> getAllProjects() { return this.mProjectDao.getAllProjects();}


    public void insertProject(Project project){
        this.mProjectDao.insertProject(project);
    }
}
