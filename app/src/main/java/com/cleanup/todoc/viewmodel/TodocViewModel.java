package com.cleanup.todoc.viewmodel;

import android.app.Application;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TodocViewModel extends AndroidViewModel {

    //---REPOSITORY---
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;


    //---DATA---
    private LiveData<List<Task>> allTasks;
    private LiveData<List<Project>> allProjects;
    private LiveData<Integer> dbSize;


    public TodocViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        allTasks = taskRepository.getAllTasks();
        dbSize = taskRepository.getDbSize();

        projectRepository = new ProjectRepository(application);
        allProjects = projectRepository.getAllProjects();


    }

    public void insertTask(Task task) {
        taskRepository.insert(task);
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    public LiveData<Integer> getDbSize() {
        return dbSize;
    }


}
