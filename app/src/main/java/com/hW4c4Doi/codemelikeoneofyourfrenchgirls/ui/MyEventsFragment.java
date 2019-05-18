package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter.MyEventsAdapter;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEventsFragment extends Fragment {
    private NavController navController;
    private Button btnEvent;
    private MyEventsAdapter myEventsAdapter;

    public MyEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_events, container, false);
        setUpMyEventsRecyclerView(view);
        return view;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.up) {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragmentEvents);

        }

        return false;
    }

    private void setUpMyEventsRecyclerView(View view) {
        Log.d("marko","UID:" + FirebaseAuth.getInstance().getUid());
        Query query = FirebaseFirestore.getInstance().collection("Events").whereArrayContains("listOfUsersParticipatingInEvent", FirebaseAuth.getInstance().getUid())
                .orderBy("name", Query.Direction.DESCENDING)
                .limit(50);

        FirestoreRecyclerOptions<Event> firestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<Event>()
                .setQuery(query, Event.class)
                .build();

        myEventsAdapter = new MyEventsAdapter(firestoreRecyclerOptions, getContext(), (AppCompatActivity) getActivity());

        RecyclerView recyclerView = view.findViewById(R.id.my_events_container);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myEventsAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        myEventsAdapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        myEventsAdapter.stopListening();
    }
}
