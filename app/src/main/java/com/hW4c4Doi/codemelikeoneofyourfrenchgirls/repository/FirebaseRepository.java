package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.repository;

;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.j2objc.annotations.ObjectiveCName;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces.AuthRegisteredListener;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces.UserFetchedListener;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces.UserUpdatedListener;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network.FirebaseHelperClass;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;


public class FirebaseRepository {
    FirebaseHelperClass firebaseHelperClass;
    List<Event> eventList;
    LiveData<List<Event>> eventLiveData;
    User currentUser;

    public FirebaseRepository() {
        eventList = new ArrayList<>();
        firebaseHelperClass = new FirebaseHelperClass();

    }


    // Adding auth listener to firebase helper class
    public void addAuthRegisteredListener(AuthRegisteredListener listener) {
        firebaseHelperClass.addAuthListener(listener);
    }

    // Adding user updated listener to firebase helper class
    public void addUserUpdatedListener(UserUpdatedListener listener) {
        firebaseHelperClass.addUserListener(listener);
    }

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

    // Pipline of creating user in firebase
    // Needs to be
    public void registerUserInFirebase(User user) {
        firebaseHelperClass.createUserAccountInFirebase(user).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                firebaseHelperClass.addAuthenticatedUserInFirebase(authResult,user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        firebaseHelperClass.updateDocRefInFirebase(documentReference);
                    }
                });
            }
        });
    }


    public LiveData<User> getCurrentUser() {
        return getCurrentUser();
    }

    public Task<QuerySnapshot> getUserFromFirebase(String uId) {
        return firebaseHelperClass.getUserFromFirebase(uId);
    }
}
