package com.dexertencreatives.stockfetcherapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.dexertencreatives.stockfetcherapp.FXDatabase.AppDatabase;

/**
 * Created by shola on 3/18/2019.
 */


public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mTaskId;

    public AddTaskViewModelFactory(AppDatabase mDb, int mTaskId) {
        this.mDb = mDb;
        this.mTaskId = mTaskId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AddTaskViewModel(mDb, mTaskId);
    }
}
