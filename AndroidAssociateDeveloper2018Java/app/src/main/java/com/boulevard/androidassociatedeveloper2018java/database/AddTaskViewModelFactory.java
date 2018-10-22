package com.boulevard.androidassociatedeveloper2018java.database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

// Make this class extend ViewModel ViewModelProvider.NewInstanceFactory
public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // Add two member variables. One for the database and one for the taskId
    private final AppDatabase mDb;
    private final int mTaskId;

    // Initialize the member variables in the constructor with the parameters received
    public AddTaskViewModelFactory(AppDatabase database, int taskId) {
        mDb = database;
        mTaskId = taskId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddTaskViewModel(mDb, mTaskId);
    }
}
