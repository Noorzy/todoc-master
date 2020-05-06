package com.cleanup.todoc;


import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 1L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID,"Projet Test",0xFFEADAD1);
    private static long PROJECT_ID_2 = 2L;
    private static Project PROJECT_DEMO_2 = new Project(PROJECT_ID_2,"Projet Test 2",0xFFB4CDBA);
    private static long TASK_ID = 1;
    private static Task TASK_DEMO = new Task(TASK_ID,PROJECT_ID,"Test task",1);
    private static long TASK_ID_2 = 2;
    private static Task TASK_DEMO_2 = new Task(TASK_ID_2,PROJECT_ID_2,"Test task 2",2);


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }


    @Test
    public void insertTaskWithSuccess() throws InterruptedException{
        //BEFORE : Creating a new task
        this.database.projectDao().insert(PROJECT_DEMO);
        this.database.taskDao().insert(TASK_DEMO);
        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertEquals(1, tasks.size());
        assertEquals(tasks.get(0).getId(), TASK_ID);
        assertEquals(tasks.get(0).getProjectId(), PROJECT_ID);
    }

    @Test
    public void getAllProjectsWithSuccess() throws InterruptedException{
        //BEFORE : Creating multiple Projects
        this.database.projectDao().insert(PROJECT_DEMO);
        this.database.projectDao().insert(PROJECT_DEMO_2);

        // TEST
        List<Project> allProjects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        assertEquals(2, allProjects.size());
        assertTrue(allProjects.get(0).getId() != allProjects.get(1).getId());
        assertTrue(allProjects.get(0).getColor() != allProjects.get(1).getColor());
        assertNotSame(allProjects.get(0).getName(), allProjects.get(1).getName());
    }

    @Test
    public void getAllTasksWithSuccess() throws  InterruptedException{
        // BEFORE : Creating multiple tasks
        this.database.projectDao().insert(PROJECT_DEMO);
        this.database.projectDao().insert(PROJECT_DEMO_2);
        this.database.taskDao().insert(TASK_DEMO);
        this.database.taskDao().insert(TASK_DEMO_2);
        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertEquals(2, tasks.size());
        assertTrue(tasks.get(0).getId() != tasks.get(1).getId());
        assertTrue(tasks.get(0).getProjectId() != tasks.get(1).getProjectId());
        assertTrue(tasks.get(0).getCreationTimestamp() != tasks.get(1).getCreationTimestamp());
        assertNotSame(tasks.get(0).getName(), tasks.get(1).getName());

    }
    @Test
    public void noTasksWhenNoTaskInserted() throws InterruptedException{
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }
    @Test
    public void noProjectsWhenNoProjectIsInserted() throws InterruptedException{
        List<Project> allProjects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());
        assertTrue(allProjects.isEmpty());
    }
}
