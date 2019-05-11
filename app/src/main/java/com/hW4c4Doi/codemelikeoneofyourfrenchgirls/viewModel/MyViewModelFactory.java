package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private EventDatabase database;


    public MyViewModelFactory(Application application, EventDatabase database) {
        this.application = application;
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new FirebaseViewModel(application, database);
    }
}
