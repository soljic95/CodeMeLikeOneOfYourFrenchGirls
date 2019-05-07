package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment2 extends Fragment {
    Button btnContinue;
    TextView tvDay;
    TextView tvMonth;
    TextView tvYear;
    long dateOfBirthInMili;
    Calendar calendar;

    DatePickerDialog.OnDateSetListener listener;


    public SignUpFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnContinue = view.findViewById(R.id.btnContinue);
        tvDay = view.findViewById(R.id.tvDay);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvYear = view.findViewById(R.id.tvYear);

        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(getContext(), year + "", Toast.LENGTH_SHORT).show();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateOfBirthInMili = calendar.getTimeInMillis();
            }
        };

        tvDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), listener, year, month, day);
                datePickerDialog.show();
            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.signUpFragment4);

            }
        });
    }
}
