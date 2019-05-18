package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.FirebaseViewModel;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsideEventFragment extends Fragment {
    @BindView((R.id.tvEventName))
    TextView tvEventName;
    @BindView(R.id.ivEventImage)
    ImageView ivEventImage;
    @BindView(R.id.tvEventActivity)
    TextView tvEventActivity;
    @BindView(R.id.tvEventDetails)
    TextView tvEventDetails;
    @BindView(R.id.tvEventTime)
    TextView tvEventTime;
    @BindView(R.id.tvEventLocation)
    TextView tvEventLocation;
    @BindView(R.id.tvEventCost)
    TextView tvEventCost;
    @BindView(R.id.chipGroup2)
    ChipGroup chipGroup2;
    @BindView(R.id.tvParticipantsInEvent)
    TextView tvParticipantsInEvent;
    @BindView(R.id.btnJoinEvent)
    Button btnJoinEvent;

    private RecyclerView recyclerView2;

    private SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm, EEE, d. MMM ", Locale.getDefault());


    private FirebaseViewModel viewModel;
    private Event event;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_inside_event, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        event = bundle.getParcelable("event");
        checkIfInEvent();
        if (event != null) {
            tvEventName.setText(event.getName());
            tvEventActivity.setText(event.getActivity());
            tvEventDetails.setText(event.getEventDescription());
            tvEventTime.setText(sdfDate.format(event.getEventStart()));
            tvEventLocation.setText(event.getEventAdress());
            for (String need : event.getEventNeeds()) {
                Chip chip = new Chip(getActivity());
                chip.setText(need);
                chip.setCheckable(false);
                chipGroup2.addView(chip, 0);
            }
        }


        if (FirebaseAuth.getInstance().getUid().equals(event.getIdOfTheUserWhoCreatedIt())) {
            setHasOptionsMenu(true);

        }


        viewModel = ViewModelProviders.of(getActivity()).get(FirebaseViewModel.class);


    }

    @OnClick(R.id.btnJoinEvent)
    void setBtnJoinEvent() {

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

    void checkIfInEvent() {
        if (event.getListOfUsersParticipatingInEvent().contains(FirebaseAuth.getInstance().getUid())) {
            btnJoinEvent.setText("Exit event");
        }
    }

    @OnClick(R.id.btnJoinEvent)
    void exitEvent() {
        if (event.getListOfUsersParticipatingInEvent().contains(FirebaseAuth.getInstance().getUid())) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Exit event?")
                    .setMessage("Are you sure you want to exit this event")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        event.removeUserFromEvent(FirebaseAuth.getInstance().getUid());
                        viewModel.updateEvent(event);
                        btnJoinEvent.setText("Join event");
                        dialog.dismiss();
                    }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
        } else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Join event?")
                    .setMessage("Are you sure you want to join this event")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        event.addUsersToArray(FirebaseAuth.getInstance().getUid());
                        viewModel.updateEvent(event);
                        btnJoinEvent.setText("Exit event");
                        dialog.dismiss();
                    }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
        }
    }


}
