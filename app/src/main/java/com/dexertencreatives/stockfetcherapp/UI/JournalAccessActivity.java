package com.dexertencreatives.stockfetcherapp.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.dexertencreatives.stockfetcherapp.FXDatabase.JournalEntry;
import com.dexertencreatives.stockfetcherapp.viewmodel.AddTaskViewModel;
import com.dexertencreatives.stockfetcherapp.viewmodel.AddTaskViewModelFactory;
import com.dexertencreatives.stockfetcherapp.FXDatabase.AppDatabase;
import com.dexertencreatives.stockfetcherapp.viewmodel.LoadJournalVModel;
import com.dexertencreatives.stockfetcherapp.R;

import java.util.Date;

public class JournalAccessActivity extends AppCompatActivity {

    private static final String TAG = JournalAccessActivity.class.getSimpleName();

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    private static final String INSTANCE_TASK_ID = "instanceTaskId";

    // Constants for priority
    private static final int PRIORITY_PROFIT = 1;
    private static final int PRIORITY_LOSS = 2;
    private static final int PRIORITY_BREAKEVEN = 3;

    private static final int DEFAULT_TASK_ID = -1;
    EditText mEditText;
    RadioGroup mRadioGroup;
    Button mButton;

    private int mTaskId = DEFAULT_TASK_ID;

    private AppDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        setTitle(R.string.ja_title);

        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText(R.string.update_button);
            if (mTaskId == DEFAULT_TASK_ID) {

                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mDb, mTaskId);
                final AddTaskViewModel viewModel = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);
                viewModel.getTask().observe(this, new Observer<JournalEntry>() {
                    @Override
                    public void onChanged(@Nullable JournalEntry taskEntry) {
                        // Remove the observer nce we won't want to receive update
                        viewModel.getTask().removeObserver(this);
                        Log.d(TAG, getString(R.string.recievin_d_update));
                        populateUI(taskEntry);
                    }
                });

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        mEditText = findViewById(R.id.editTextTaskDescription);
        mRadioGroup = findViewById(R.id.radioGroup);

        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(view -> onSaveButtonClicked());
    }


    private void populateUI(JournalEntry task) {
        if (task == null) {
            return;
        }
        mEditText.setText(task.getDescription());
        setPriorityInViews(task.getPriority());
    }


    public void onSaveButtonClicked() {
        String description = mEditText.getText().toString();
        int priority = getPriorityFromViews();
        Date date = new Date();


        final JournalEntry taskEntry = new JournalEntry(description, priority, date);

        if (mTaskId == DEFAULT_TASK_ID) {
            insertTodoFromDatabase(taskEntry);
        } else {

            taskEntry.setId(mTaskId);
            updateTodoFromDatabase(taskEntry);
        }
        finish();
    }


    public int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_PROFIT;
                break;
            case R.id.radButton2:
                priority = PRIORITY_LOSS;
                break;
            case R.id.radButton3:
                priority = PRIORITY_BREAKEVEN;
        }
        return priority;
    }


    public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_PROFIT:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case PRIORITY_LOSS:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case PRIORITY_BREAKEVEN:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }
    }


    private void insertTodoFromDatabase(JournalEntry taskEntry) {
        LoadJournalVModel loadJournalVModel = ViewModelProviders.of(this).get(LoadJournalVModel.class);
        loadJournalVModel.insertItem(taskEntry);

    }

    private void updateTodoFromDatabase(JournalEntry taskEntry) {
        LoadJournalVModel loadJournalVModel = ViewModelProviders.of(this).get(LoadJournalVModel.class);
        loadJournalVModel.updateItem(taskEntry);

    }
}
