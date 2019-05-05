package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.FirebaseViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsideEventFragment extends Fragment {
    TextView tvEventName;
    ImageView ivEventImage;
    FirebaseViewModel viewModel;
    Event event;


    public InsideEventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inside_event, container, false);
        Bundle bundle = getArguments();
        event = bundle.getParcelable("event");
        setHasOptionsMenu(true);
        tvEventName = view.findViewById(R.id.tvEventName);
        ivEventImage = view.findViewById(R.id.ivEventImage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivEventImage.setTransitionName(event.getImageTransationName());
        }
        Glide.with(this).load(event.getImageLocation()).centerCrop().into(ivEventImage);
        tvEventName.setText(event.getEventName());
        viewModel = ViewModelProviders.of(getActivity()).get(FirebaseViewModel.class);

        startPostponedEnterTransition();
        Transition transition = TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move);
        setSharedElementEnterTransition(transition);
        setSharedElementReturnTransition(transition);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.event_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        viewModel.deleteEvent(event);
        Navigation.findNavController(getView()).navigate(R.id.fragmentEvents);
        return super.onOptionsItemSelected(item);
    }
}
