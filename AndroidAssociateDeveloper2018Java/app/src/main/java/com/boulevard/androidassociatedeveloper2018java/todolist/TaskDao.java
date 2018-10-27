package com.boulevard.androidassociatedeveloper2018java.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.boulevard.androidassociatedeveloper2018java.common.models.TaskEntry;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task ORDER BY priority")
    LiveData<List<TaskEntry>> loadAllTasks();

    @Insert
    void insertTask(TaskEntry taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE )
    void updateTask(TaskEntry taskEntry);

    @Delete
    void deleteTask(TaskEntry taskEntry);

    @Query("SELECT * FROM task WHERE id = :id")
    LiveData<TaskEntry> loadTaskById(int id);
}
