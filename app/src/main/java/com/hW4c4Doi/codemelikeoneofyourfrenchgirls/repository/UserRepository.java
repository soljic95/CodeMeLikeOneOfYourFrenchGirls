package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.repository;

import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces.AuthRegisteredListener;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDao;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserRepository implements AuthRegisteredListener {
    EventDao eventDao;
    FirebaseRepository firebaseRepository;

    public UserRepository(EventDatabase database, FirebaseRepository firebaseRepository){
        eventDao = database.getEventDao();
        this.firebaseRepository = firebaseRepository;
        this.firebaseRepository.addAuthRegisteredListener(this);
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
                        firebaseRepository.deleteEventFromFirebase(event);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
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
                        Log.d("error", e.getMessage());
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

    @Override
    public void UserAuthenticatedInFirebase(AuthResult authResult, User user) {
        return;
    }

    @Override
    public void UserUpdatedInFirebase(User user) {
        insertUserInDatabase(user);
    }
}
