package com.cleanup.todoc.database;

import android.content.Context;
import android.os.AsyncTask;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = true)
public abstract class TodocDatabase extends RoomDatabase {

    //---SINGLETON---
    private static TodocDatabase instance;

    //---DAO---
    public abstract ProjectDao projectDao();

    public abstract TaskDao taskDao();

    //---INSTANCE---
    public static synchronized TodocDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TodocDatabase.class, "TodocDatabase.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // TODO load sampledata/tasks.json in the database
            new PopulateDbAsyncTask(instance).execute();


        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;
        private ProjectDao projectDao;

        private PopulateDbAsyncTask(TodocDatabase db) {
            taskDao = db.taskDao();
            projectDao = db.projectDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            projectDao.insert(new Project(1L, "Projet Tartampion", 0xFFEADAD1));
            projectDao.insert(new Project(2L, "Projet Lucidia", 0xFFB4CDBA));
            projectDao.insert(new Project(3L, "Projet Circus", 0xFFA3CED2));
            return null;
        }
    }
}