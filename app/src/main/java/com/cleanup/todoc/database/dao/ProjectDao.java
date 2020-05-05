package com.cleanup.todoc.database.dao;

import com.cleanup.todoc.model.Project;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);
//
   // @Update
   // void update(Project project);
//
   // @Delete
   // void delete(Project project);

    @Query("SELECT * FROM project_table ORDER BY id DESC")
    LiveData<List<Project>> getAllProjects();

}
