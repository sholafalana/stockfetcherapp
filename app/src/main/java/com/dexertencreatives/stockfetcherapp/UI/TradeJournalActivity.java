package com.dexertencreatives.stockfetcherapp.UI;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

import com.dexertencreatives.stockfetcherapp.FXDatabase.AppDatabase;
import com.dexertencreatives.stockfetcherapp.viewmodel.LoadJournalVModel;
import com.dexertencreatives.stockfetcherapp.viewmodel.MainViewModel;
import com.dexertencreatives.stockfetcherapp.FXDatabase.JournalEntry;
import com.dexertencreatives.stockfetcherapp.R;
import com.dexertencreatives.stockfetcherapp.adapter.TaskAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TradeJournalActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = TradeJournalActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;


    private AppDatabase mDb;
    @BindView(R.id.spinner11)
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        setTitle(R.string.tj_title);

        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
        mRecyclerView = findViewById(R.id.recyclerViewTasks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TaskAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mDb = AppDatabase.getInstance(getApplicationContext());
        setUpViewModel();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();
                List<JournalEntry> tasks = mAdapter.getTasks();
                deleteTodoFromDatabase(tasks.get(position));
                setUpViewModel();
            }
        }).attachToRecyclerView(mRecyclerView);

        FloatingActionButton fabButton = findViewById(R.id.fab);

        fabButton.setOnClickListener(view -> {
            Intent addTaskIntent = new Intent(TradeJournalActivity.this, JournalAccessActivity.class);
            startActivity(addTaskIntent);
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position == 0) {
            setUpViewModel();
        } else {
            setUpViewModelbyID(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void setUpViewModel() {

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, taskEntries -> mAdapter.setTasks(taskEntries));
    }

    private void setUpViewModelbyID(int ID) {


        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.gettodosbyId(ID).observe(this, taskEntries -> mAdapter.setTasks(taskEntries));

    }

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(TradeJournalActivity.this, JournalAccessActivity.class);
        intent.putExtra(JournalAccessActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }

    private void deleteTodoFromDatabase(JournalEntry taskEntry) {
        LoadJournalVModel loadJournalVModel = ViewModelProviders.of(this).get(LoadJournalVModel.class);
        loadJournalVModel.deleteItem(taskEntry);

    }
}
