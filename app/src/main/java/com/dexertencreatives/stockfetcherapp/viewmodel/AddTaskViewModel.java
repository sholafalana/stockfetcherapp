package com.dexertencreatives.stockfetcherapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.dexertencreatives.stockfetcherapp.FXDatabase.AppDatabase;
import com.dexertencreatives.stockfetcherapp.FXDatabase.JournalEntry;

/**
 * Created by shola on 3/18/2019.
 */

public class AddTaskViewModel extends ViewModel {

    private LiveData<JournalEntry> task;

    public AddTaskViewModel(AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    public LiveData<JournalEntry> getTask() {
        return task;
    }
}
