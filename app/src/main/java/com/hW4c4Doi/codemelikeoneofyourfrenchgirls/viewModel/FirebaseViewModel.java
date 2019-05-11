package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.repository.FirebaseRepository;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;

import java.util.List;

public class FirebaseViewModel extends AndroidViewModel {
    private FirebaseRepository firebaseRepository;
    private LiveData<List<Event>> eventListLiveData;

    public FirebaseViewModel(@NonNull Application application, EventDatabase database) {
        super(application);
        firebaseRepository = new FirebaseRepository(database);
        eventListLiveData = firebaseRepository.getAllEvents();

    }

    public LiveData<List<Event>> getObservableFirebaseLiveData() {
        return firebaseRepository.getEventLiveData();
    }

    public void insertEvent(Event event) {
        Log.d("marko", "insertEvent: called");
        firebaseRepository.insertEvent(event);
    }


    public LiveData<List<Event>> getEventList() {
        return eventListLiveData;
    }

    public void deleteEvent(Event event) {
        firebaseRepository.deleteEvent(event);
    }

    public void createUser(User user) {
        firebaseRepository.createUserInFirebase(user);
        firebaseRepository.insertUserInDatabase(user);
    }


    public void updateUser(User user) {
        firebaseRepository.updateId(user);
    }


}