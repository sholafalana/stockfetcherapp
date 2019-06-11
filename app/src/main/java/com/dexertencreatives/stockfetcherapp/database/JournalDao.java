package com.dexertencreatives.stockfetcherapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dexertencreatives.stockandforexfetcher.model.JournalEntry;

import java.util.List;

/**
 * Created by shola on 3/18/2019.
 */

@Dao
public interface JournalDao {

    // Fetch data from the db [The first constructor @TaskEntry class will be used here]
    @Query("SELECT * FROM journal ORDER BY id")
    LiveData<List<JournalEntry>> loadAllTasks();

    // Add new data to the database [The second constructor @TaskEntry class will be used here]
    @Insert
    void insertTask(JournalEntry taskEntry);

    // Update a pre existing table in the db
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(JournalEntry taskEntry);

    // Delete a table in the db
    @Delete
    void deleteTask(JournalEntry taskEntry);

    @Query("SELECT * FROM journal WHERE id = :id")
    LiveData<JournalEntry> loadTaskById(int id);

    @Query("SELECT * FROM journal WHERE priority = :priority")
    LiveData<List<JournalEntry>> fetchTodoListByCategory(int priority);

}
