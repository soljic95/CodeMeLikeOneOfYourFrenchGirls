package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter.UpcomingEventsRecyclerViewAdapter;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.FirebaseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.MyViewModelFactory;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingEventsFragment extends Fragment {
    private FloatingActionButton fab;
    private FirebaseViewModel viewModel;
    private RecyclerView recyclerView;
    private UpcomingEventsRecyclerViewAdapter adapter;
    private LinearLayoutManager manager;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_upcoming_events, container, false);
        context = getContext();
        setupAdapter(view);
        fab = view.findViewById(R.id.fab);
        viewModel = ViewModelProviders.of(getActivity(), new MyViewModelFactory(getActivity().getApplication(), EventDatabase.getInstance(getContext())))
                .get(FirebaseViewModel.class);
        viewModel.getObservableFirebaseLiveData().observe(this, events -> {
            adapter.addAllEvents(events);
            adapter.notifyDataSetChanged();
        });

        fab.setOnClickListener(v -> Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragmentCreateEvent));

        return view;
    }

    public void setupAdapter(View view) {
        recyclerView = view.findViewById(R.id.container);
        recyclerView.setHasFixedSize(true);

        adapter = new UpcomingEventsRecyclerViewAdapter(context, getActivity());
        manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
