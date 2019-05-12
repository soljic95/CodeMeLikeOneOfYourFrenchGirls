package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.network;


import android.util.Log;

import androidx.annotation.MainThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.EventInterfaces.AuthRegisteredListener;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.UpdateUserId;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FirebaseHelperClass implements  AuthRegisteredListener{
    FirebaseFirestore db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String eventDocRef = "";
    String userDocRef = "";

    private List<AuthRegisteredListener> listeners = new ArrayList<AuthRegisteredListener>();

    public void addListener(AuthRegisteredListener toAdd) {
        listeners.add(toAdd);
    }
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

    public void createUserAccountInFirebase(User user)  {

         auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        for (AuthRegisteredListener hl : listeners)
                            hl.UserAuthenticatedInFirebase(authResult, user);
                    }
                });
    }


    public void setCustomDocRef(String customDocRef) {
    }


    @Override
    public void UserAuthenticatedInFirebase(AuthResult authResult, User user) {
        db.collection("Users").add(user.updateUserId(authResult.getUser().getUid()))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        db.collection("Users").document(documentReference.getId()).update("userDocRef",documentReference.getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                db.collection("Users").document(documentReference.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        for (AuthRegisteredListener hl : listeners)
                                            hl.UserUpdatedInFirebase(documentSnapshot.toObject(User.class));
                                    }
                                });
                            }
                        });
                    }
                });
    };


    @Override
    public void UserUpdatedInFirebase(User user) {
        return;
    }
}