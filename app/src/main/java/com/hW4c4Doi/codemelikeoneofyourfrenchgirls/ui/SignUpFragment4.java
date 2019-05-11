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
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment4 extends Fragment {
    public static final String PASSED_USER_TAG = "user";

    User user;
    @BindView(R.id.btnContinue)
    Button btnContinue;

    @BindView(R.id.nogometChip)
    Chip nogometChip;
    @BindView(R.id.kosarkaChip)
    Chip kosarkaChip;

    @BindView(R.id.rukometChip)
    Chip rukometChip;

    @BindView(R.id.trcanjeChip)
    Chip trcanjeChip;

    @BindView(R.id.teretanaChip)
    Chip teretanaChip;

    @BindView(R.id.drustveneChip)
    Chip drustveneChip;

    @BindView(R.id.druzenjeChip)
    Chip druzenjeChip;

    List<String> interestList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle bundle = new Bundle();
        bundle = getArguments();
        user = new User();
        user = bundle.getParcelable(PASSED_USER_TAG);


        setChipsListener();

    }

    @OnClick(R.id.btnContinue)
    void continueToNextCreationPage() {
        user.setInterests((ArrayList<String>) interestList);
        user.setRange(100);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PASSED_USER_TAG, user);
        Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment4_to_signUpFragment3, bundle);
    }

   /* @OnCheckedChanged(R.id.nogometChip)
    void toastMe() {
        if (nogometChip.isChecked()) {
            Toast.makeText(getContext(), "nogometChecked", Toast.LENGTH_SHORT).show(); we can use it like this also :)
        } else if (!nogometChip.isChecked()) {
            Toast.makeText(getContext(), "uncheked", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void setChipsListener() {
        nogometChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                interestList.add(nogometChip.getText().toString());
                Log.d("marko", "onCheckedChanged: nogometChip added");
            } else if (isChecked == false) {
                interestList.remove(nogometChip.getText().toString());
                Log.d("marko", "onCheckedChanged: nogometChip removed");
            }
        });
        kosarkaChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                interestList.add(kosarkaChip.getText().toString());
                Log.d("marko", "onCheckedChanged: kosarkaChip added");
            } else if (isChecked == false) {
                interestList.remove(kosarkaChip.getText().toString());
                Log.d("marko", "onCheckedChanged: kosarkaChip removed");
            }
        });
        rukometChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                interestList.add(rukometChip.getText().toString());
                Log.d("marko", "onCheckedChanged: rukometChip added");
            } else if (isChecked == false) {
                interestList.remove(rukometChip.getText().toString());
                Log.d("marko", "onCheckedChanged: rukometChip removed");
            }
        });
        trcanjeChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                interestList.add(trcanjeChip.getText().toString());
                Log.d("marko", "onCheckedChanged: trcanjeChip added");
            } else if (isChecked == false) {
                interestList.remove(trcanjeChip.getText().toString());
                Log.d("marko", "onCheckedChanged: trcanjeChip removed");
            }
        });
        teretanaChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                interestList.add(teretanaChip.getText().toString());
                Log.d("marko", "onCheckedChanged: teretanaChip added");
            } else if (isChecked == false) {
                interestList.remove(teretanaChip.getText().toString());
                Log.d("marko", "onCheckedChanged: teretanaChip removed");
            }
        });
        drustveneChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                interestList.add(drustveneChip.getText().toString());
                Log.d("marko", "onCheckedChanged: drustveneChip added");
            } else if (isChecked == false) {
                interestList.remove(drustveneChip.getText().toString());
                Log.d("marko", "onCheckedChanged: drustveneChip removed");
            }
        });
        druzenjeChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                interestList.add(druzenjeChip.getText().toString());
                Log.d("marko", "onCheckedChanged: druzenjeChip added");
            } else if (isChecked == false) {
                interestList.remove(druzenjeChip.getText().toString());
                Log.d("marko", "onCheckedChanged: druzenjeChip removed");
            }
        });
    }
}

