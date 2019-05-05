package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEventsFragment extends Fragment {
    private NavController navController;
    private Button btnEvent;

    public MyEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_events, container, false);

        btnEvent = view.findViewById(R.id.btnEvent);
        btnEvent.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.fragmentInsideEvent, null));

        return view;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.up) {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragmentEvents);

        }

        return false;
    }
}
