package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.repository;


import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces.UserUpdatedListener;

import android.util.Log;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network.FirebaseHelperClass;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDao;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import io.reactivex.schedulers.Schedulers;

public class FirebaseRepository {
    FirebaseHelperClass firebaseHelperClass;
    List<Event> eventList;
    LiveData<List<Event>> eventLiveData;
    User currentUser;
    EventDao eventDao;

    public FirebaseRepository(EventDatabase database) {
        eventList = new ArrayList<>();
        firebaseHelperClass = new FirebaseHelperClass();
        eventDao = database.getEventDao();
    }


    public LiveData<List<Event>> getAllEvents() {
        return firebaseHelperClass.getEvents();
    }

    // Adding user updated listener to firebase helper class
    public void addUserUpdatedListener(UserUpdatedListener listener) {
        firebaseHelperClass.addUserListener(listener);
    }

    public void insertEvent(final Event event) {
        Single.fromCallable(() -> eventDao.insertEvent(event)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        Log.d("marko", "onSuccess: " + aLong);
                        //event.setEventId(aLong);
                        insertEventInFirebaseDb(event);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("marko", "onError: " + e.getLocalizedMessage());
                    }
                });
    }

    public void insertEventInFirebaseDb(final Event event) {
        firebaseHelperClass.insertEvent(event);
    }


    public void deleteEventFromFirebase(Event event) {
        firebaseHelperClass.deleteEventFromFirebase(event);
    }

    public void updateUserInFirebase(User user){
        firebaseHelperClass.updateUserInFirebase(user);
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
                firebaseHelperClass.addAuthenticatedUserInFirebase(authResult, user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    public void updateEvent(Event event) {
        firebaseHelperClass.updateEvent(event);
    }
}
