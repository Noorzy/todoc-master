package com.cleanup.todoc.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;
    private LiveData<Integer> dbSize;

    public TaskRepository(Application application) {
        TodocDatabase database = TodocDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
        dbSize = taskDao.getDbSize();
    }

    public void insert(Task task) {
        new InsertTaskAsyncTask(taskDao).execute(task);

    }

    public void update(Task task) {

    }

    public void delete(Task task) {
        new DeleteTaskAsyncTask(taskDao).execute(task);

    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<Integer> getDbSize() {
        return dbSize;
    }

    public static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    public static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        public DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }
}
