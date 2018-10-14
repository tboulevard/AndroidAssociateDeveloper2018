package com.boulevard.androidassociatedeveloper2018java.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.boulevard.androidassociatedeveloper2018java.R;
import com.boulevard.androidassociatedeveloper2018java.database.AppDatabase;
import com.boulevard.androidassociatedeveloper2018java.model.TaskEntry;

import java.util.Date;


public class ListViewFragment extends Fragment {

    private AppDatabase mDb;

    /* View holder member variables */
    private FloatingActionButton saveButton;
    private EditText descriptionEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = AppDatabase.getInstance(this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup view elements for reference, after view is created
        saveButton = getView().findViewById(R.id.save_task_button);
        descriptionEditText = getView().findViewById(R.id.description_edit_text);

        // Set up listeners
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = descriptionEditText.getText().toString();
                int priority = 1;
                Date date = new Date();

                TaskEntry task = new TaskEntry(description, priority, date);
                mDb.taskDao().insertTask(task);
            }
        });
    }
}
