package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
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
    @BindView(R.id.btnCreateEvent)
    Button btnCreateEvent;
    @BindView(R.id.eiEventName)
    TextInputEditText eiEventName;
    @BindView(R.id.spinnerActivity)
    Spinner activitySpinner;
    @BindView(R.id.tvEventLocation)
    TextInputEditText tvEventLocation;
    @BindView(R.id.etPlayersNeeded)
    TextInputEditText etPlayersNeeded;
    @BindView(R.id.cbEntryCharged)
    CheckBox cbEntryCharged;
    @BindView(R.id.ivMoneyPicture)
    ImageView ivMoneyPicture;
    @BindView(R.id.eiEventValue)
    TextInputEditText eiEventValue;
    @BindView(R.id.tvEntryCharged)
    TextView tvEntryCharged;
    @BindView(R.id.tvKuna)
    TextView tvKuna;

    private final ArrayList<String> listOfNeeds = new ArrayList<>();
    private FirebaseViewModel viewModel;
    private DatePickerDialog.OnDateSetListener listener;
    private TimePickerDialog.OnTimeSetListener timeListener;
    private String eventActivity = "other";
    private Calendar calendar = Calendar.getInstance();


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
        eiEventValue.setEnabled(false);

        cbEntryCharged.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    eiEventValue.setEnabled(true);
                    tvEntryCharged.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvKuna.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    eiEventValue.setEnabled(false);
                    tvEntryCharged.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_light_disabled));
                    tvKuna.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_light_disabled));

                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.activities,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpinner.setAdapter(adapter);

        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventActivity = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                eventActivity = "Other";
            }
        });

        listener = (view12, year, month, dayOfMonth) -> {
            month = month; //i dont know why but ondate changed sets the month a month earlier, so this corrects that
            final SimpleDateFormat sdfDate = new SimpleDateFormat("EEE, d. MMM ", Locale.getDefault());
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            tvDateOfEvent.setText(sdfDate.format(calendar.getTimeInMillis()));
        };

        timeListener = (view1, hourOfDay, minute) -> {
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
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
        Calendar calendarDate = Calendar.getInstance();
        int year = calendarDate.get(Calendar.YEAR);
        int month = calendarDate.get(Calendar.MONTH);
        int day = calendarDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener, year, month, day);
        datePickerDialog.show();

    }

    @OnClick(R.id.tvEventTime)
    void setTimeListener() {
        Calendar calendarTime = Calendar.getInstance();
        int hour = calendarTime.get(Calendar.HOUR);
        int minute = calendarTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), timeListener, hour, minute, true);
        timePickerDialog.show();
    }

    @OnClick(R.id.btnCreateEvent)
    void createEvent() {
        Event currentEvent = new Event(FirebaseAuth.getInstance().getUid(), eiEventName.getText().toString(),
                eventActivity, calendar.getTimeInMillis(), Integer.parseInt(etPlayersNeeded.getText().toString()), eiEventDetails.getText().toString(),
                tvEventLocation.getText().toString(), false);
        currentEvent.addUsersToArray(FirebaseAuth.getInstance().getUid());
        currentEvent.setEventNeeds(listOfNeeds);
        viewModel.insertEvent(currentEvent);
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.fragmentEvents);
    }


}
