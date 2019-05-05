package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class FirebaseHelperClass {
    FirebaseFirestore db;
    String documentRef = "";


    public FirebaseHelperClass() {
        db = FirebaseFirestore.getInstance();
    }

    public LiveData<List<Event>> getEvents() {
        final MutableLiveData events = new MutableLiveData();
        final List<Event> eventsList = new ArrayList<>();
        db.collection("Events").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dc : queryDocumentSnapshots) {
                    eventsList.add(dc.toObject(Event.class));
                }
                events.setValue(eventsList);
            }
        });
        return events;

    }

    public String insertEvent(Event event) {
        db.collection("Events").add(event).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                documentRef = documentReference.getId();
            }
        });
        return documentRef;
    }

    public void deleteEventFromFirebase(Event event) {
        Log.d("marko", "deleteEventFromFirebase: started");
        db.collection("Events").whereEqualTo("eventId", event.getEventId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot dc : queryDocumentSnapshots) {
                    db.collection("Events").document(dc.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("marko", "onSuccess: event deleted from firebase");
                        }
                    });
                }
            }
        });
    }

    public LiveData<List<Event>> observeAllEvents(){
        final MutableLiveData<List<Event>> observedLiveData = new MutableLiveData<>();
        final List<Event> eventList = new ArrayList<>();
        db.collection("Events").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                eventList.clear();
                for(DocumentSnapshot dc : queryDocumentSnapshots){
                    Log.d("marko", "onEvent: "+dc.toObject(Event.class).getEventName());
                    eventList.add(dc.toObject(Event.class));
                }
                observedLiveData.setValue(eventList);
            }
        });
        return observedLiveData;
    }
}
