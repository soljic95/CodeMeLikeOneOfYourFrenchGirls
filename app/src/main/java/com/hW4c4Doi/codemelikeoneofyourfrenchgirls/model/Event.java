package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "event_table")
public class Event implements Parcelable {

    public Event() {

    }

    public String eventName;
    public String eventActivity;
    public String eventTransitionName;
    public int imageLocation;
    public String imageTransationName;
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public long eventId;

    @Ignore
    public Event(String eventName, String eventActivity, String eventTransitionName,String imageTransationName,int imageLocation) {
        this.eventName = eventName;
        this.eventActivity = eventActivity;
        this.eventTransitionName = eventTransitionName;
        this.imageLocation = imageLocation;
        this.imageTransationName = imageTransationName;

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventActivity() {
        return eventActivity;
    }

    public void setEventActivity(String eventActivity) {
        this.eventActivity = eventActivity;
    }

    public String getEventTransitionName() {
        return eventTransitionName;
    }

    public void setEventTransitionName(String eventTransitionName) {
        this.eventTransitionName = eventTransitionName;
    }

    public int getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(int imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getImageTransationName() {
        return imageTransationName;
    }

    public void setImageTransationName(String imageTransationName) {
        this.imageTransationName = imageTransationName;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eventName);
        dest.writeString(this.eventActivity);
        dest.writeString(this.eventTransitionName);
        dest.writeInt(this.imageLocation);
        dest.writeString(this.imageTransationName);
        dest.writeLong(this.eventId);
    }

    protected Event(Parcel in) {
        this.eventName = in.readString();
        this.eventActivity = in.readString();
        this.eventTransitionName = in.readString();
        this.imageLocation = in.readInt();
        this.imageTransationName = in.readString();
        this.eventId = in.readLong();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
