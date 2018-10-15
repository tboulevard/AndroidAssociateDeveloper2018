package com.boulevard.androidassociatedeveloper2018java.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.boulevard.androidassociatedeveloper2018java.R;
import com.boulevard.androidassociatedeveloper2018java.database.AppDatabase;
import com.boulevard.androidassociatedeveloper2018java.database.TaskAdapter;


import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * IMPORTANT: Notice how TaskAdapter.ItemClickListener provides a callback back into the adapter
 */
public class ListViewFragment extends Fragment implements TaskAdapter.ItemClickListener {

    private AppDatabase mDb;

    /* View holder member variables */
    private FloatingActionButton saveButton;
    private RecyclerView taskListRecyclerView;

    /* Other member variables*/
    private TaskAdapter taskAdapter;

    /* Fragments */
    private AddTaskFragment addTaskFragment;

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

        // Set up listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);

                if(addTaskFragment == null) {
                    addTaskFragment = new AddTaskFragment();
                }

                fragmentTransaction.replace(R.id.fragment_container, addTaskFragment,  "add_task_fragment_tag");
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Toast toast = Toast.makeText(this.getContext(), "Clicked itemId: " + itemId, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        // Call the adapter's setTasks method using the result
        // of the loadAllTasks method from the taskDao
        taskAdapter.setTasks(mDb.taskDao().loadAllTasks());
    }
}
