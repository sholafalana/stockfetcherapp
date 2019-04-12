package com.dexertencreatives.stockfetcherapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.dexertencreatives.stockfetcherapp.FXDatabase.AppDatabase;
import com.dexertencreatives.stockfetcherapp.FXDatabase.JournalEntry;

/**
 * Created by shola on 3/17/2019.
 */

public class LoadJournalVModel extends AndroidViewModel {

    private AppDatabase appDatabase;


    public LoadJournalVModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(this.getApplication());
    }

    public void insertItem(JournalEntry taskEntry) {

        new BackgroundInsert(appDatabase).execute(taskEntry);
    }

    public void deleteItem(JournalEntry taskEntry) {
        new BackgroundDelete(appDatabase).execute(taskEntry);
    }

    public void updateItem(JournalEntry taskEntry) {

        new BackgroundUpdate(appDatabase).execute(taskEntry);
    }


    private static class BackgroundInsert extends AsyncTask<JournalEntry, Void, Void> {

        private AppDatabase db;

        BackgroundInsert(AppDatabase appDatabase) {

            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final JournalEntry... params) {

            db.taskDao().insertTask((params[0]));

            return null;

        }
    }

    private static class BackgroundDelete extends AsyncTask<JournalEntry, Void, Void> {

        private AppDatabase db;

        BackgroundDelete(AppDatabase appDatabase) {

            db = appDatabase;
        }


        @Override
        protected Void doInBackground(final JournalEntry... params) {
            db.taskDao().deleteTask(params[0]);
            return null;
        }
    }


    private static class BackgroundUpdate extends AsyncTask<JournalEntry, Void, Void> {

        private AppDatabase db;

        BackgroundUpdate(AppDatabase appDatabase) {

            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final JournalEntry... params) {

            db.taskDao().updateTask(params[0]);

            return null;

        }
    }

}





