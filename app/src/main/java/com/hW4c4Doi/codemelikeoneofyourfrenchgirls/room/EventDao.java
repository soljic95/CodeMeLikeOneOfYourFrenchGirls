package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface EventDao {

    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAllEvents();

    @Insert
    long insertEvent(Event event);

    @Delete
    void deleteEvent(Event event);

    @Update
    void updateEvent(Event event);

    @Update
    void updateUser(User user);

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user_table where userId = :uId")
    Single<User> getCurrentUser(String uId);

    @Query("SELECT * FROM user_table")
    LiveData<User> getAnyUser();


}
