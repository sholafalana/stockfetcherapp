package com.dexertencreatives.stockfetcherapp.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dexertencreatives.stockfetcherapp.FXDatabase.JournalEntry;
import com.dexertencreatives.stockfetcherapp.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by shola on 3/18/2019.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;

    // Class variables for the List that holds task data and the Context
    private List<JournalEntry> mTaskEntries;
    private Context mContext;

    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    public TaskAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);

        return new TaskViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        JournalEntry taskEntry = mTaskEntries.get(position);

        String description = taskEntry.getDescription();
        int priority = taskEntry.getPriority();
        String updatedAt = dateFormat.format(taskEntry.getUpdatedAt());

        //Set values
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);

        // Programmatically set the text and color for the priority TextView
        String priorityString = "" + getPriorityString(priority); // converts int to String
        holder.priorityView.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }


    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.medium_green);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }

    private String getPriorityString(int priority) {
        String priorityString = "";

        switch (priority) {
            case 1:
                priorityString = "P";
                break;
            case 2:
                priorityString = "L";
                break;
            case 3:
                priorityString = "B";
                break;
            default:
                break;
        }
        return priorityString;
    }


    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    public List<JournalEntry> getTasks() {
        return mTaskEntries;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<JournalEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView taskDescriptionView;
        TextView updatedAtView;
        TextView priorityView;


        public TaskViewHolder(View itemView) {
            super(itemView);

            taskDescriptionView = itemView.findViewById(R.id.taskDescription);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            priorityView = itemView.findViewById(R.id.priorityTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mTaskEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}