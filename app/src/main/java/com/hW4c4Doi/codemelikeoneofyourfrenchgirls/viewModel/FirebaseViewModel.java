package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network.FirebaseHelperClass;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.repository.FirebaseRepository;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.repository.UserRepository;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;

import java.util.List;

import io.reactivex.Single;

public class FirebaseViewModel extends AndroidViewModel {
    private FirebaseRepository firebaseRepository;
    private UserRepository userRepository;
    private LiveData<List<Event>> eventListLiveData;

    public FirebaseViewModel(@NonNull Application application, EventDatabase database) {
        super(application);
        firebaseRepository = new FirebaseRepository();
        userRepository = new UserRepository(database, firebaseRepository);
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
        userRepository.deleteEvent(event);
    }

    public void createUser(User user) {
        firebaseRepository.createUserInFirebase(user);
    }

    public void updateUser(User user) {
        userRepository.updateId(user);
    }

    public boolean isUserInRoomDatabase(String uId) {
        return userRepository.isUserInRoomDb(uId);
    }

    public User getUserFromFirebase(String uId) {
        return firebaseRepository.getUserFromFirebase(uId);
    }

}