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


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment4 extends Fragment {
    User user;
    Button btnContinue;
    Chip nogometChip;
    Chip kosarkaChip;
    Chip rukometChip;
    Chip trcanjeChip;
    Chip teretanaChip;
    Chip drustveneChip;
    Chip druzenjeChip;
    List<String> interestList = new ArrayList<>();

    public SignUpFragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new Bundle();
        bundle = getArguments();
        user = new User();
        user = bundle.getParcelable("user");
        btnContinue = view.findViewById(R.id.btnContinue);
        nogometChip = view.findViewById(R.id.nogometChip);
        kosarkaChip = view.findViewById(R.id.kosarkaChip);
        rukometChip = view.findViewById(R.id.rukometChip);
        trcanjeChip = view.findViewById(R.id.trcanjeChip);
        teretanaChip = view.findViewById(R.id.teretanaChip);
        drustveneChip = view.findViewById(R.id.drustveneChip);
        druzenjeChip = view.findViewById(R.id.druzenjeChip);

        Toast.makeText(getContext(), user.getName(), Toast.LENGTH_SHORT).show();

        setChipsListener();


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment4_to_signUpFragment3);
            }
        });
    }

    public void setChipsListener() {
        nogometChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    interestList.add(nogometChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: nogometChip added");
                } else if (isChecked == false) {
                    interestList.remove(nogometChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: nogometChip removed");
                }
            }
        });
        kosarkaChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    interestList.add(kosarkaChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: kosarkaChip added");
                } else if (isChecked == false) {
                    interestList.remove(kosarkaChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: kosarkaChip removed");
                }
            }
        });
        rukometChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    interestList.add(rukometChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: rukometChip added");
                } else if (isChecked == false) {
                    interestList.remove(rukometChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: rukometChip removed");
                }
            }
        });
        trcanjeChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    interestList.add(trcanjeChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: trcanjeChip added");
                } else if (isChecked == false) {
                    interestList.remove(trcanjeChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: trcanjeChip removed");
                }
            }
        });
        teretanaChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    interestList.add(teretanaChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: teretanaChip added");
                } else if (isChecked == false) {
                    interestList.remove(teretanaChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: teretanaChip removed");
                }
            }
        });
        drustveneChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    interestList.add(drustveneChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: drustveneChip added");
                } else if (isChecked == false) {
                    interestList.remove(drustveneChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: drustveneChip removed");
                }
            }
        });
        druzenjeChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    interestList.add(druzenjeChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: druzenjeChip added");
                } else if (isChecked == false) {
                    interestList.remove(druzenjeChip.getText().toString());
                    Log.d("marko", "onCheckedChanged: druzenjeChip removed");
                }
            }
        });
    }
}
