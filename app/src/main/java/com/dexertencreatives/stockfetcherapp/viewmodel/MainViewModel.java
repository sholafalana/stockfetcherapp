package com.dexertencreatives.stockfetcherapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dexertencreatives.stockfetcherapp.FXDatabase.AppDatabase;
import com.dexertencreatives.stockfetcherapp.FXDatabase.JournalEntry;

import java.util.List;

/**
 * Created by shola on 3/18/2019.
 */

public class MainViewModel extends AndroidViewModel {

    // Constant for Logging
    private static final String TAG = MainViewModel.class.getSimpleName();
    private AppDatabase database;

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the database");
        tasks = database.taskDao().loadAllTasks();
    }

    private LiveData<List<JournalEntry>> tasks;
    private LiveData<List<JournalEntry>> task;

    public LiveData<List<JournalEntry>> gettodosbyId(int todosId) {
        task = database.taskDao().fetchTodoListByCategory(todosId);
        return task;
    }

    public LiveData<List<JournalEntry>> getTasks() {
        return tasks;
    }
}
