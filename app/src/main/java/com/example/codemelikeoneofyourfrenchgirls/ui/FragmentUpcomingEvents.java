package com.example.codemelikeoneofyourfrenchgirls.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codemelikeoneofyourfrenchgirls.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUpcomingEvents extends Fragment {


    public FragmentUpcomingEvents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_events, container, false);
    }

}
