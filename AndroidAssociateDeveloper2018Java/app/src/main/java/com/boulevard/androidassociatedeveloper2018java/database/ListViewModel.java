package com.boulevard.androidassociatedeveloper2018java.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.boulevard.androidassociatedeveloper2018java.model.TaskEntry;

import java.util.List;

// make this class extend AndroidViewModel and implement its default constructor
public class ListViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = ListViewModel.class.getSimpleName();

    // Add a tasks member variable for a list of TaskEntry objects wrapped in a LiveData
    private LiveData<List<TaskEntry>> tasks;

    public ListViewModel(Application application) {
        super(application);
        // In the constructor use the loadAllTasks of the taskDao to initialize the tasks variable
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        tasks = database.taskDao().loadAllTasks();
    }

    // Create a getter for the tasks variable
    public LiveData<List<TaskEntry>> getTasks() {
        return tasks;
    }
}
