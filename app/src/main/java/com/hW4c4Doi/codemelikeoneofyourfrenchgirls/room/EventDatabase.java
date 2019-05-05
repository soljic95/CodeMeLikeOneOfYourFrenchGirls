package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;

@Database(entities = Event.class, version = 1)
public abstract class EventDatabase extends RoomDatabase {

    public static EventDatabase instance;

    public abstract EventDao getEventDao();

    public static synchronized EventDatabase getInstance(Context context) {

        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EventDatabase.class,"event_database")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;

    }


}
