package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserRepository implements UserUpdatedListener{
    EventDao eventDao;
    FirebaseRepository firebaseRepository;
    boolean isUserInRoom = false;


    public UserRepository(EventDatabase database, FirebaseRepository firebaseRepository) {
        eventDao = database.getEventDao();
        this.firebaseRepository = firebaseRepository;
        // Adding this class to userUpdatedListener so we know when we are ready to create user
        // in Room database
        this.firebaseRepository.addUserUpdatedListener(this);
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

    // Insert user in Room database
    public Completable insertUserInDatabase(User user) {
        return Completable.fromAction(() -> eventDao.insertUser(user)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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


    public Single<User> isUserInRoomDb(String uId) {
       //return Completable.fromSingle(() -> eventDao.getCurrentUser(uId)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return eventDao.getCurrentUser(uId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    };

    // Insert given user in Room database when he is created in Firebase

    @Override
    public void UserUpdatedInFirebase(User user) {

        insertUserInDatabase(user).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d("marko", "onSuccess: User was added in database!" + user);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("marko","onSuccess: User couldn be added in database!-" + e.getMessage());
            }
        });
    }

}
