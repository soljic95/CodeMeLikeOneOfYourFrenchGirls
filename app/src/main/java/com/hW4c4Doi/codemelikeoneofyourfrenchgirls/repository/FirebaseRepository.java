package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.UpdateUserId;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network.FirebaseHelperClass;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDao;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class FirebaseRepository {
    FirebaseHelperClass firebaseHelperClass;
    List<Event> eventList;
    LiveData<List<Event>> eventLiveData;
    EventDao eventDao;
    User currentUser;

    public FirebaseRepository(EventDatabase database) {
        eventList = new ArrayList<>();
        firebaseHelperClass = new FirebaseHelperClass();
        eventDao = database.getEventDao();
        eventLiveData = eventDao.getAllEvents();

    }


    public LiveData<List<Event>> getAllEvents() {
        return eventLiveData;
    }


    public void deleteEvent(final Event event) {
        Completable.fromAction(() -> eventDao.deleteEvent(event)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("marko", "onComplete: event deleted");
                        deleteEventFromFirebase(event);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
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
                        event.setEventId(aLong);
                        insertEventInFirebaseDb(event);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void insertEventInFirebaseDb(Event event) {
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

    public void insertUserInDatabase(User user) {
        Completable.fromAction(() -> eventDao.insertUser(user)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        Log.d("marko", "onInsertUser: userInserted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void updateId(User user) {
        Completable.fromAction(() -> eventDao.updateUser(user)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("marko", "onComplete: userId updated");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    public LiveData<User> getCurrentUser() {
        return getCurrentUser();
    }
}
