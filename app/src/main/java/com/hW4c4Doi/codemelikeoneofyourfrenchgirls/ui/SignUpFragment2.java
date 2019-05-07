package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment2 extends Fragment {
    private User user;
    private Button btnContinue;
    private TextView tvDay;
    private TextView tvMonth;
    private TextView tvYear;
    private long dateOfBirthInMili;
    private Calendar calendar;
    private TextInputEditText etFullName;
    private RadioGroup radioGroup;
    private EditText etPhoneNumber;
    private DatePickerDialog.OnDateSetListener listener;
    private String sex;

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
        Bundle bundle = new Bundle();
        bundle = getArguments();
        user = new User();
        user = bundle.getParcelable("user");
        btnContinue = view.findViewById(R.id.btnContinue);
        tvDay = view.findViewById(R.id.tvDay);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvYear = view.findViewById(R.id.tvYear);
        radioGroup = view.findViewById(R.id.radioGroup);
        etFullName = view.findViewById(R.id.etFullName);
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);


        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1; //i dont know why but ondate changed sets the month a month earlier, so this corrects that
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateOfBirthInMili = calendar.getTimeInMillis();
                tvDay.setText("Day :" + dayOfMonth + ".");
                tvMonth.setText("Month :" + month + ".");
                tvYear.setText("Year :" + year + "." + "");
            }
        };

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioFemale) {
                    sex = "female";
                } else if (checkedId == R.id.radioMale) {
                    sex = "male";
                }
            }
        });

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
                if (validateForm()) {
                    Bundle bundle1 = new Bundle();
                    user.setName(etFullName.getText().toString());
                    user.setDateOfBirth(dateOfBirthInMili);
                    user.setSex(sex);
                    user.setPhoneNumber(Long.parseLong(etPhoneNumber.getText().toString()));
                    bundle1.putParcelable("user", user);

                    Navigation.findNavController(view).navigate(R.id.signUpFragment4, bundle1);

                }

            }
        });
    }

    public boolean validateForm() {
        boolean valid = true;
        if (etFullName.getText().toString().length() > 1) {
            Log.d("marko", "validateForm: name okay");
        } else {
            etFullName.setError("Name cant be empty");
            valid = false;
        }

        if (tvDay.getText().toString().length() > 3) {
            Log.d("marko", "validateForm: date picked");
        } else {
            tvDay.setError("Pick date of birth");
            valid = false;
        }

        if (sex != null) {
            Log.d("marko", "validateForm: sex picked");
        } else {
            Toast.makeText(getContext(), "Select sex please", Toast.LENGTH_SHORT).show();
            ;
            valid = false;
        }

        if (etPhoneNumber.getText().toString().length() > 8) {
            Log.d("marko", "validateForm: phone number cool");
        } else {
            etPhoneNumber.setError("Enter valid phone number");
            valid = false;
        }
        return valid;
    }
}
