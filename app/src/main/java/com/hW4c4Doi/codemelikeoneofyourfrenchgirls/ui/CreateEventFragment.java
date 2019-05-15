package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.Event;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.FirebaseViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.android.material.resources.MaterialResources.getDrawable;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment {
    @BindView(R.id.createEventChipGroup)
    ChipGroup createEventChipGroup;
    @BindView(R.id.btnAddNeed)
    Button btnAddNeed;
    @BindView(R.id.eiAddNeed)
    TextInputEditText eiAddNeed;
    @BindView(R.id.tvDateOfEvent)
    TextView tvDateOfEvent;
    @BindView(R.id.clock_event_image)
    ImageView ivEventClock;
    @BindView(R.id.tvEventTime)
    TextView tvEventTime;
    @BindView(R.id.eiEventDetails)
    TextInputEditText eiEventDetails;
    private final List<String> listOfNeeds = new ArrayList<>();
    private FirebaseViewModel viewModel;
    private DatePickerDialog.OnDateSetListener listener;
    private TimePickerDialog.OnTimeSetListener timeListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_event, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(getActivity()).get(FirebaseViewModel.class);

        listener = (view12, year, month, dayOfMonth) -> {
            month = month; //i dont know why but ondate changed sets the month a month earlier, so this corrects that
            final SimpleDateFormat sdfDate = new SimpleDateFormat("EEE, d. MMM ", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            tvDateOfEvent.setText(sdfDate.format(calendar.getTimeInMillis()));
        };

        timeListener = (view1, hourOfDay, minute) -> {
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            tvEventTime.setText(sdfTime.format(calendar.getTimeInMillis()));


        };

    }

    @OnClick(R.id.btnAddNeed)
    void addNeed() {
        String currentNeed = eiAddNeed.getText().toString();
        if (currentNeed.length() > 2) {
            listOfNeeds.add(currentNeed);
            Chip chip = new Chip(getActivity());
            chip.setText(eiAddNeed.getText().toString().trim());
            chip.setCheckable(false);
            createEventChipGroup.addView(chip, 0);
            eiAddNeed.setText(R.string.empty_string);
        } else {
            eiAddNeed.setError("Tag cant be null");
        }


    }

    @OnClick({R.id.tvDateOfEvent, R.id.clock_event_image})
    void setAnotherListener() {
        Log.d("marko", "setListener: ");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener, year, month, day);
        datePickerDialog.show();

    }

    @OnClick(R.id.tvEventTime)
    void setTimeListener() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), timeListener, hour, minute, true);
        timePickerDialog.show();
    }
}
