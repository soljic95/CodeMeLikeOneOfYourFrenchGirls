package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "user_table")
public class User implements Parcelable {

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String name;
    String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    String userId;
    String password;
    String email;
    String profilePictureUrl;
    String description;
    ArrayList<String> interests;
    int range;
    double userLat;
    double userLong;
    String userToken;
    ArrayList<String> userFriends;
    String userDocRef;
    long dateOfBirth;
    int nogometSkill;
    int kosarkaSkill;
    int sahSkill;
    int numberOfEventsParticipated = 0;
    int positiveReview = 0;
    int percentage = 100;
    long phoneNumber;

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @PrimaryKey(autoGenerate = true)
    int primaryKey;

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public User(String userId, String userToken, String name, String email, String description,
                ArrayList<String> interests, int range, ArrayList<String> userFriends, String profilePictureUrl, int nogometSkill, int kosarkaSkill,
                int sahSkill, int numberOfEventsParticipated, int positiveReview, int percentage) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.description = description;
        this.interests = interests;
        this.range = range;
        this.userToken = userToken;
        this.userFriends = userFriends;
        this.profilePictureUrl = profilePictureUrl;
        this.nogometSkill = nogometSkill;
        this.kosarkaSkill = kosarkaSkill;
        this.sahSkill = sahSkill;
        this.numberOfEventsParticipated = numberOfEventsParticipated;
        this.positiveReview = positiveReview;
        this.percentage = percentage;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getNumberOfEventsParticipated() {
        return numberOfEventsParticipated;
    }

    public void setNumberOfEventsParticipated(int numberOfEventsParticipated) {
        this.numberOfEventsParticipated = numberOfEventsParticipated;
    }

    public int getPositiveReview() {
        return positiveReview;
    }

    public void setPositiveReview(int positiveReview) {
        this.positiveReview = positiveReview;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public int getNogometSkill() {
        return nogometSkill;
    }

    public void setNogometSkill(int nogometSkill) {
        this.nogometSkill = nogometSkill;
    }

    public int getKosarkaSkill() {
        return kosarkaSkill;
    }

    public void setKosarkaSkill(int kosarkaSkill) {
        this.kosarkaSkill = kosarkaSkill;
    }

    public int getSahSkill() {
        return sahSkill;
    }

    public void setSahSkill(int sahSkill) {
        this.sahSkill = sahSkill;
    }


    public String getUserDocRef() {
        return userDocRef;
    }

    public void setUserDocRef(String userDocRef) {
        this.userDocRef = userDocRef;
    }

    public ArrayList<String> getUserFriends() {
        return userFriends;
    }

    public void setUserFriends(ArrayList<String> userFriends) {
        this.userFriends = userFriends;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public double getUserLat() {
        return userLat;
    }

    public void setUserLat(double userLat) {
        this.userLat = userLat;
    }

    public double getUserLong() {
        return userLong;
    }

    public void setUserLong(double userLong) {
        this.userLong = userLong;
    }


    public User() {
    }

    public int getUsersFinalGrade() {
        return usersFinalGrade;
    }

    public void setUsersFinalGrade(int usersFinalGrade) {
        this.usersFinalGrade = usersFinalGrade;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }


    //we will use this in the case of leaving reviews from 1 to 5, and then we will calculate the users review grade.
    private int userGrades;
    private int numberOfGrades;
    private int usersFinalGrade;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUri(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public void gradeUser(int grade) {
        userGrades += grade;
        numberOfGrades += 1;
        usersFinalGrade = userGrades / numberOfGrades;
    }

    public void addPositiveReview() {
        positiveReview += 1;
        numberOfEventsParticipated += 1;
    }

    public void addNegativeReview() {
        numberOfEventsParticipated += 1;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.sex);
        dest.writeString(this.userId);
        dest.writeString(this.password);
        dest.writeString(this.email);
        dest.writeString(this.profilePictureUrl);
        dest.writeString(this.description);
        dest.writeStringList(this.interests);
        dest.writeInt(this.range);
        dest.writeDouble(this.userLat);
        dest.writeDouble(this.userLong);
        dest.writeString(this.userToken);
        dest.writeStringList(this.userFriends);
        dest.writeString(this.userDocRef);
        dest.writeLong(this.dateOfBirth);
        dest.writeInt(this.nogometSkill);
        dest.writeInt(this.kosarkaSkill);
        dest.writeInt(this.sahSkill);
        dest.writeInt(this.numberOfEventsParticipated);
        dest.writeInt(this.positiveReview);
        dest.writeInt(this.percentage);
        dest.writeLong(this.phoneNumber);
        dest.writeInt(this.primaryKey);
        dest.writeInt(this.userGrades);
        dest.writeInt(this.numberOfGrades);
        dest.writeInt(this.usersFinalGrade);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.sex = in.readString();
        this.userId = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.profilePictureUrl = in.readString();
        this.description = in.readString();
        this.interests = in.createStringArrayList();
        this.range = in.readInt();
        this.userLat = in.readDouble();
        this.userLong = in.readDouble();
        this.userToken = in.readString();
        this.userFriends = in.createStringArrayList();
        this.userDocRef = in.readString();
        this.dateOfBirth = in.readLong();
        this.nogometSkill = in.readInt();
        this.kosarkaSkill = in.readInt();
        this.sahSkill = in.readInt();
        this.numberOfEventsParticipated = in.readInt();
        this.positiveReview = in.readInt();
        this.percentage = in.readInt();
        this.phoneNumber = in.readLong();
        this.primaryKey = in.readInt();
        this.userGrades = in.readInt();
        this.numberOfGrades = in.readInt();
        this.usersFinalGrade = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
