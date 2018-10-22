package com.boulevard.androidassociatedeveloper2018java.fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boulevard.androidassociatedeveloper2018java.R;
import com.boulevard.androidassociatedeveloper2018java.activities.AddTaskActivity;
import com.boulevard.androidassociatedeveloper2018java.database.AppDatabase;
import com.boulevard.androidassociatedeveloper2018java.database.AppExecutors;
import com.boulevard.androidassociatedeveloper2018java.database.TaskAdapter;
import com.boulevard.androidassociatedeveloper2018java.model.TaskEntry;


import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * IMPORTANT: Notice how TaskAdapter.ItemClickListener provides a callback back into the adapter
 */
public class ListViewFragment extends Fragment implements TaskAdapter.ItemClickListener {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";

    private AppDatabase mDb;

    /* View holder member variables */
    private FloatingActionButton saveButton;
    private RecyclerView taskListRecyclerView;

    /* Other member variables*/
    private TaskAdapter taskAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = AppDatabase.getInstance(this.getContext().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Un-hide action bar
        ActionBar actionbar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();
        actionbar.show();

        // Setup view elements for reference, after view is created
        saveButton = getView().findViewById(R.id.save_task_button);
        taskListRecyclerView = getView().findViewById(R.id.recyclerViewTasks);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        taskListRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // Initialize the adapter and attach it to the RecyclerView
        /* `this` is acceptable for the listener argument because this fragment implements
         TaskAdapter.ItemClickListener */
        taskAdapter = new TaskAdapter(this.getContext(), this);
        taskListRecyclerView.setAdapter(taskAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(this.getContext().getApplicationContext(), VERTICAL);
        taskListRecyclerView.addItemDecoration(decoration);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                // Get the diskIO Executor from the instance of AppExecutors and
                // call the diskIO execute method with a new Runnable and implement its run method
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // get the position from the viewHolder parameter
                        int position = viewHolder.getAdapterPosition();
                        List<TaskEntry> tasks = taskAdapter.getTasks();
                        // Call deleteTask in the taskDao with the task at that position
                        mDb.taskDao().deleteTask(tasks.get(position));
                        // Call retrieveTasks method to refresh the UI
                        retrieveTasks();
                    }
                });
            }
        }).attachToRecyclerView(taskListRecyclerView);

        // Set up listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {

        // Launch AddTaskActivity adding the itemId as an extra in the intent
        // COMPLETED (2) Launch AddTaskActivity with itemId as extra for the key AddTaskActivity.EXTRA_TASK_ID
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        intent.putExtra(AddTaskActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        retrieveTasks();
    }

    private void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<TaskEntry> tasks = mDb.taskDao().loadAllTasks();
                // We will be able to simplify this once we learn more
                // about Android Architecture Components
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        taskAdapter.setTasks(tasks);
                    }
                });
            }
        });
    }
}
