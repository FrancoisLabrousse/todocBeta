package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.dao.TaskRoomDataBase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TaskRoomDataBase mDataBase;

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.mDataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TaskRoomDataBase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        mDataBase.close();
    }

    // DATA SET FOR TEST
    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Blablabla", 0xFFA3CED2);
    private static Task NEW_TASK_RECURER_LES_TOILETTES = new Task(1, PROJECT_ID, "Récurer les toilettes", new Date().getTime());
    private static Task NEW_TASK_FAIRE_LES_POUSSIERES = new Task(2, PROJECT_ID, "Faire les poussières", new Date().getTime());


    @Test
    public void insertAndGetProject() throws InterruptedException {
        // BEFORE : Adding a new project
        this.mDataBase.mProjectDao().insertProject(PROJECT_DEMO);
        // TEST
        List<Project> project = LiveDataTestUtil.getValue(this.mDataBase.mProjectDao().getAllProjects());
        assertTrue(project.get(0).getName().equals(PROJECT_DEMO.getName()) && project.get(0).getId() == PROJECT_ID);
    }

    @Test
    public void getTaskWhenNoProjectInserted() throws InterruptedException {
        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDataBase.mTaskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {
        // BEFORE : Adding demo Project & demo tasks

        this.mDataBase.mProjectDao().insertProject(PROJECT_DEMO);
        this.mDataBase.mTaskDao().insertTask(NEW_TASK_RECURER_LES_TOILETTES);
        this.mDataBase.mTaskDao().insertTask(NEW_TASK_FAIRE_LES_POUSSIERES);

        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDataBase.mTaskDao().getAllTasks());
        assertTrue(tasks.size() == 2);
    }

    @Test
    public void insertAndUpdateTask() throws InterruptedException {
        // BEFORE : Adding demo project & demo tasks. Next, update task added & re-save it
        this.mDataBase.mProjectDao().insertProject(PROJECT_DEMO);
        this.mDataBase.mTaskDao().insertTask(NEW_TASK_RECURER_LES_TOILETTES);
        List<Task> taskAdded = LiveDataTestUtil.getValue(this.mDataBase.mTaskDao().getAllTasks());
        this.mDataBase.mTaskDao().updateTask(taskAdded.get(0));

        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDataBase.mTaskDao().getAllTasks());
        assertTrue(tasks.size() == 1);
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        // BEFORE : Adding demo project & demo task. Next, get the task added & delete it.
        this.mDataBase.mProjectDao().insertProject(PROJECT_DEMO);
        this.mDataBase.mTaskDao().insertTask(NEW_TASK_RECURER_LES_TOILETTES);
        List<Task> taskAdded = LiveDataTestUtil.getValue(this.mDataBase.mTaskDao().getAllTasks());
        this.mDataBase.mTaskDao().deleteTask(taskAdded.get(0).getId());

        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.mDataBase.mTaskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }
}
