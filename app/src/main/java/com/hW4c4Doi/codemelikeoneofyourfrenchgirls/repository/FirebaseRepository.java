package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.repository;

;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces.AuthRegisteredListener;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network.FirebaseHelperClass;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;


public class FirebaseRepository {
    FirebaseHelperClass firebaseHelperClass;
    List<Event> eventList;
    LiveData<List<Event>> eventLiveData;
    User currentUser;

    public FirebaseRepository() {
        eventList = new ArrayList<>();
        firebaseHelperClass = new FirebaseHelperClass();
        this.addAuthRegisteredListener(firebaseHelperClass);

    }

    public void addAuthRegisteredListener(AuthRegisteredListener listener){firebaseHelperClass.addListener(listener);}
    public LiveData<List<Event>> getAllEvents() {
        return eventLiveData;
    }

    public void insertEvent(final Event event) {
        firebaseHelperClass.insertEvent(event);
    }


    public void deleteEventFromFirebase(Event event) {
        firebaseHelperClass.deleteEventFromFirebase(event);
    }

    public LiveData<List<Event>> getEventLiveData() {
        return firebaseHelperClass.observeAllEvents();
    }

    public void createUserInFirebase(User user) {
        firebaseHelperClass.createUserAccountInFirebase(user);
    }





    public LiveData<User> getCurrentUser() {
        return getCurrentUser();
    }
}
