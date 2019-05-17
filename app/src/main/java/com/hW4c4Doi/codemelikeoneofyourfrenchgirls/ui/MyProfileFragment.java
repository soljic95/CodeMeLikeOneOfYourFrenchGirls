package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.bluetooth.le.ScanRecord;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDao;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {

    @BindView(R.id.name_profile_fragment)
    TextView name;
    @BindView(R.id.surname_profile_fragment)
    TextView surname;
    @BindView(R.id.mail_profile_fragment)
    TextView email;
    @BindView(R.id.football_chip_profile_fragment)
    Chip footballChip;
    @BindView(R.id.basketball_chip_profile_fragment)
    Chip basketballChip;
    @BindView(R.id.fitness_chip_profile_fragment)
    Chip fitnessChip;
    @BindView(R.id.handball_chip_profile_fragment)
    Chip handballChip;
    @BindView(R.id.running_chip_profile_fragment)
    Chip runningChip;
    @BindView(R.id.social_chip_profile_fragment)
    Chip socialChip;
    @BindView(R.id.board_games_chip_profile_fragment)
    Chip boardGamesChip;

    @BindView(R.id.range_profile_fragment)
    SeekBar range;

    EventDao eventDao;
    FirebaseAuth mAuth;
    public MyProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        eventDao = EventDatabase.getInstance(getContext()).getEventDao();
        mAuth = FirebaseAuth.getInstance();
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Log.d("marko","UID"+mAuth.getUid());
        eventDao.getCurrentUser(mAuth.getUid()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(User user) {
                Log.d("marko","onSuccess: user fetched from database and ready to fill!");
                fillProfileInformations(user);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("marko", "onError: user couldnt be fetched from database!" + e.getMessage());
            }
        });

    }

    private void fillProfileInformations(User user){
        name.setText(user.getName());
        email.setText(user.getEmail());
        range.setProgress(user.getRange());

        for(String interest : user.getInterests()){
            if( interest.equals(footballChip.getText().toString())) footballChip.setChecked(true);
            else if( interest.equals(basketballChip.getText().toString())) basketballChip.setChecked(true);
            else if( interest.equals(handballChip.getText().toString())) handballChip.setChecked(true);
            else if( interest.equals( fitnessChip.getText().toString())) fitnessChip.setChecked(true);
            else if( interest.equals(runningChip.getText().toString())) runningChip.setChecked(true);
            else if( interest.equals(boardGamesChip.getText().toString())) boardGamesChip.setChecked(true);
            else if( interest.equals(socialChip.getText().toString())) socialChip.setChecked(true);
        }
    }

    public void setChipsListener(User user) {
        footballChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                user.addInterest(footballChip.getText().toString());
                Log.d("marko", "onCheckedChanged: nogometChip added");
            } else if (!isChecked) {
                user.removeInterest(footballChip.getText().toString());
                Log.d("marko", "onCheckedChanged: nogometChip removed");
            }
        });
        basketballChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                user.addInterest(basketballChip.getText().toString());
                Log.d("marko", "onCheckedChanged: kosarkaChip added");
            } else if (!isChecked) {
                user.removeInterest(basketballChip.getText().toString());
                Log.d("marko", "onCheckedChanged: kosarkaChip removed");
            }
        });
        handballChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                user.addInterest(handballChip.getText().toString());
                Log.d("marko", "onCheckedChanged: rukometChip added");
            } else if (!isChecked) {
                user.removeInterest(handballChip.getText().toString());
                Log.d("marko", "onCheckedChanged: rukometChip removed");
            }
        });
        runningChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                user.addInterest(runningChip.getText().toString());
                Log.d("marko", "onCheckedChanged: trcanjeChip added");
            } else if (!isChecked) {
                user.removeInterest(runningChip.getText().toString());
                Log.d("marko", "onCheckedChanged: trcanjeChip removed");
            }
        });
        fitnessChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                user.addInterest(fitnessChip.getText().toString());
                Log.d("marko", "onCheckedChanged: teretanaChip added");
            } else if (!isChecked) {
                user.removeInterest(fitnessChip.getText().toString());
                Log.d("marko", "onCheckedChanged: teretanaChip removed");
            }
        });
        boardGamesChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                user.addInterest(boardGamesChip.getText().toString());
                Log.d("marko", "onCheckedChanged: drustveneChip added");
            } else if (!isChecked) {
                user.removeInterest(boardGamesChip.getText().toString());
                Log.d("marko", "onCheckedChanged: drustveneChip removed");
            }
        });
        socialChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                user.addInterest(socialChip.getText().toString());
                Log.d("marko", "onCheckedChanged: druzenjeChip added");
            } else if (!isChecked) {
                user.removeInterest(socialChip.getText().toString());
                Log.d("marko", "onCheckedChanged: druzenjeChip removed");
            }
        });
    }

}
