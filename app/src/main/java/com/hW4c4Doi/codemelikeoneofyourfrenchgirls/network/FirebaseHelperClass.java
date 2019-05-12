package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.UpdateUserId;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;

import java.util.ArrayList;
import java.util.List;


public class FirebaseHelperClass {
    FirebaseFirestore db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String eventDocRef = "";
    String userDocRef = "";


    public FirebaseHelperClass() {
        db = FirebaseFirestore.getInstance();
    }

    public LiveData<List<Event>> getEvents() {
        final MutableLiveData events = new MutableLiveData();
        final List<Event> eventsList = new ArrayList<>();
        db.collection("Events").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot dc : queryDocumentSnapshots) {
                eventsList.add(dc.toObject(Event.class));
            }
            events.setValue(eventsList);
        });
        return events;

    }

    public String insertEvent(Event event) {
        db.collection("Events").add(event).addOnSuccessListener(documentReference -> eventDocRef = documentReference.getId());
        return eventDocRef;
    }

    public void deleteEventFromFirebase(Event event) {
        Log.d("marko", "deleteEventFromFirebase: started");
        db.collection("Events").whereEqualTo("eventId", event.getEventId()).addSnapshotListener((queryDocumentSnapshots, e) -> {
            for (DocumentSnapshot dc : queryDocumentSnapshots) {
                db.collection("Events").document(dc.getId()).delete().addOnSuccessListener(aVoid -> Log.d("marko", "onSuccess: event deleted from firebase"));
            }
        });
    }

    public LiveData<List<Event>> observeAllEvents() {
        final MutableLiveData<List<Event>> observedLiveData = new MutableLiveData<>();
        final List<Event> eventList = new ArrayList<>();
        db.collection("Events").addSnapshotListener((queryDocumentSnapshots, e) -> {
            eventList.clear();
            for (DocumentSnapshot dc : queryDocumentSnapshots) {
                Log.d("marko", "onEvent: " + dc.toObject(Event.class).getName());
                eventList.add(dc.toObject(Event.class));
            }
            observedLiveData.setValue(eventList);
        });
        return observedLiveData;
    }

    public void createUserAccountInFirebase(User user) {
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).
                addOnSuccessListener(authResult ->
                db.collection("Users").add(user.updateUserId(authResult.getUser().getUid())).
                        addOnSuccessListener(documentReference -> db.collection("Users")
                                .document(documentReference.getId()).update("userDocRef",documentReference.getId())));

    }

    public void setCustomDocRef(String customDocRef) {
    }


}
