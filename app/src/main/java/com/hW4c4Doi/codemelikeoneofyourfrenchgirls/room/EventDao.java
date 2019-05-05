package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;

import java.util.List;

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

}
