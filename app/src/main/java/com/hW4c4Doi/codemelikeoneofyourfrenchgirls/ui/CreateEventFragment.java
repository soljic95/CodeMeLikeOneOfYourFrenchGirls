package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.FirebaseViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment {
    MaterialButton button;
    TextInputEditText inEventName;
    TextInputEditText inEventActivity;
    Event event;
    FirebaseViewModel viewModel;

    public CreateEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        button = view.findViewById(R.id.btnCreateEvent);
        inEventActivity = view.findViewById(R.id.inEventActivity);
        inEventName = view.findViewById(R.id.inEventName);
        viewModel = ViewModelProviders.of(getActivity()).get(FirebaseViewModel.class);


        button.setOnClickListener(v -> {
            event = new Event(FirebaseAuth.getInstance().getUid(), inEventName.getText().toString(), inEventActivity.getText().toString(),
                    0, 0, 0, 0, 6, "EVENT DESCRIPTION", "",
                    false, false, 000);

            viewModel.insertEvent(event);
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragmentEvents);

        });

        return view;
    }

}
