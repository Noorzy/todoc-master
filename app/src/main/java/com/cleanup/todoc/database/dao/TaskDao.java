package com.cleanup.todoc.database.dao;

import com.cleanup.todoc.model.Task;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT COUNT (id) FROM task_table")
    LiveData<Integer> getDbSize();


}
