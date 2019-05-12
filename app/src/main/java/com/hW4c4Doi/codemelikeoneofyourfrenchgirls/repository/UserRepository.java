package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.repository;

import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces.AuthRegisteredListener;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces.UserFetchedListener;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces.UserUpdatedListener;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDao;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserRepository implements UserUpdatedListener, UserFetchedListener {
    EventDao eventDao;
    FirebaseRepository firebaseRepository;
    boolean isUserInRoom = false;


    public UserRepository(EventDatabase database, FirebaseRepository firebaseRepository) {
        eventDao = database.getEventDao();
        this.firebaseRepository = firebaseRepository;
        // Adding this class to userUpdatedListener so we know when we are ready to create user
        // in Room database
        this.firebaseRepository.addUserUpdatedListener(this);
        this.firebaseRepository.addUserFetchedListener(this);
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


    public boolean isUserInRoomDb(String uId) {
        Single.just(eventDao.getCurrentUser(uId)).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(User user) {
                        if (user.getName() != null) {
                            isUserInRoom = true;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return isUserInRoom;
    }

    // Insert given user in Room database when he is created in Firebase

    @Override
    public void UserUpdatedInFirebase(User user) {
        insertUserInDatabase(user);
    }


    @Override
    public void UserFetchedFromFirebase(User user) {
        insertUserInDatabase(user);
    }
}
