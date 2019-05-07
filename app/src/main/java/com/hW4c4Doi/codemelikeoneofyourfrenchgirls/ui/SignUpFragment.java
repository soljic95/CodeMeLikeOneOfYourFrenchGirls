package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    User user;
    Button btnContinue;
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    TextInputEditText etPasswordCheck;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnContinue = view.findViewById(R.id.btnContinue);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etPasswordCheck = view.findViewById(R.id.etConfirmPassword);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateForm()){
                    Bundle bundle = new Bundle();
                    user = new User();
                    user.setEmail(etEmail.getText().toString());
                    user.setPassword(etPasswordCheck.getText().toString());
                    bundle.putParcelable("user",user);
                    Navigation.findNavController(view).navigate(R.id.signUpFragment2,bundle);

                }

            }
        });

    }

    public boolean validateForm(){
        boolean valid = true;
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString())
                .matches()){
            Log.d("marko", "validateForm: email in correct form");
        }else {
            etEmail.setError("Email not valid");
            valid = false;
        }

        if(etPassword.getText().toString().length()>6){
            Log.d("marko", "validateForm: password big enough");
        }else {
            etPassword.setError("Password must be more than 6 characters");
            valid = false;
        }

        if(etPassword.getText().toString().equals(etPasswordCheck.getText().toString())){
            Log.d("marko", "validateForm: password the same");
        }else {
            etPasswordCheck.setError("Passwords dont match");
            valid = false;
        }
        return valid;
    }


}
