package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "event_table")
public class Event implements Parcelable {

    public Event() {

    }

    @OnConflictStrategy()
    @ColumnInfo(name = "event_id")
    @PrimaryKey(autoGenerate = true)
    private long table_id;
    private String eventId;
    private String name;
    private String activity;
    private String eventDescription;
    private long eventStart;
    private double eventLat;
    private double eventLng;
    private int usersNeeded;
    private int usersEntered;
    private String idOfTheUserWhoCreatedIt;
    private ArrayList<String> listOfUsersParticipatingInEvent = new ArrayList<>();
    private boolean isCompleted = false;
    private boolean isPrivate;
    private int skillNeeded;
    private int pictureNumber;
    private ArrayList<String> eventNeeds;


    public ArrayList<String> getEventNeeds() {
        return eventNeeds;
    }

    public void setEventNeeds(ArrayList<String> eventNeeds) {
        this.eventNeeds = eventNeeds;
    }

    public long getTable_id() {
        return table_id;
    }

    public void setTable_id(long table_id) {
        this.table_id = table_id;
    }

    public int getPictureNumber() {
        return pictureNumber;
    }

    public void setPictureNumber(int pictureNumber) {
        this.pictureNumber = pictureNumber;
    }

    public int getSkillNeeded() {
        return skillNeeded;
    }

    public void setSkillNeeded(int skillNeeded) {
        this.skillNeeded = skillNeeded;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public long getEventStart() {
        return eventStart;
    }

    public void setEventStart(long eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventAdress() {
        return eventAdress;
    }

    public void setEventAdress(String eventAdress) {
        this.eventAdress = eventAdress;
    }

    private String eventAdress;

    public ArrayList<String> getListOfUsersParticipatingInEvent() {
        return listOfUsersParticipatingInEvent;
    }

    public void setListOfUsersParticipatingInEvent(ArrayList<String> listOfUsersParticipatingInEvent) {
        this.listOfUsersParticipatingInEvent = listOfUsersParticipatingInEvent;
    }

    public String getIdOfTheUserWhoCreatedIt() {
        return idOfTheUserWhoCreatedIt;
    }

    public void setIdOfTheUserWhoCreatedIt(String idOfTheUserWhoCreatedIt) {
        this.idOfTheUserWhoCreatedIt = idOfTheUserWhoCreatedIt;
    }


    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Event(String idOfTheUserWhoCreatedIt, String name, String activity, long eventStart, int usersNeeded,
                 String eventDescription, String eventAdress, boolean isCompleted) {
        this.idOfTheUserWhoCreatedIt = idOfTheUserWhoCreatedIt;
        this.name = name;
        this.activity = activity;
        this.eventStart = eventStart;
        this.usersNeeded = usersNeeded;
        this.eventDescription = eventDescription;
        this.eventAdress = eventAdress;
        this.isCompleted = isCompleted;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }


    public double getEventLat() {
        return eventLat;
    }

    public void setEventLat(double eventLat) {
        this.eventLat = eventLat;
    }

    public double getEventLng() {
        return eventLng;
    }

    public void setEventLng(double eventLng) {
        this.eventLng = eventLng;
    }

    public int getUsersNeeded() {
        return usersNeeded;
    }

    public void setUsersNeeded(int usersNeeded) {
        this.usersNeeded = usersNeeded;
    }

    public int getUsersEntered() {
        return usersEntered;
    }

    public void setUsersEntered(int usersEntered) {
        this.usersEntered = usersEntered;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void addUsersToArray(String userId) {
        listOfUsersParticipatingInEvent.add(userId);
        usersEntered += 1;

    }

    public void addCreatorUserToArray(String userId) {
        listOfUsersParticipatingInEvent.add(userId);
    }

    public void removeUserFromEvent(String userIdToBeRemoved) {
        listOfUsersParticipatingInEvent.remove(userIdToBeRemoved);
        usersEntered -= 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.table_id);
        dest.writeString(this.eventId);
        dest.writeString(this.name);
        dest.writeString(this.activity);
        dest.writeString(this.eventDescription);
        dest.writeLong(this.eventStart);
        dest.writeDouble(this.eventLat);
        dest.writeDouble(this.eventLng);
        dest.writeInt(this.usersNeeded);
        dest.writeInt(this.usersEntered);
        dest.writeString(this.idOfTheUserWhoCreatedIt);
        dest.writeStringList(this.listOfUsersParticipatingInEvent);
        dest.writeByte(this.isCompleted ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isPrivate ? (byte) 1 : (byte) 0);
        dest.writeInt(this.skillNeeded);
        dest.writeInt(this.pictureNumber);
        dest.writeStringList(this.eventNeeds);
        dest.writeString(this.eventAdress);
    }

    protected Event(Parcel in) {
        this.table_id = in.readLong();
        this.eventId = in.readString();
        this.name = in.readString();
        this.activity = in.readString();
        this.eventDescription = in.readString();
        this.eventStart = in.readLong();
        this.eventLat = in.readDouble();
        this.eventLng = in.readDouble();
        this.usersNeeded = in.readInt();
        this.usersEntered = in.readInt();
        this.idOfTheUserWhoCreatedIt = in.readString();
        this.listOfUsersParticipatingInEvent = in.createStringArrayList();
        this.isCompleted = in.readByte() != 0;
        this.isPrivate = in.readByte() != 0;
        this.skillNeeded = in.readInt();
        this.pictureNumber = in.readInt();
        this.eventNeeds = in.createStringArrayList();
        this.eventAdress = in.readString();
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
