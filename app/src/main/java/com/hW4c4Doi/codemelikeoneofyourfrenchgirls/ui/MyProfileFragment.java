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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter.InterestRecycleAdapter;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter.UpcomingEventsRecyclerViewAdapter;
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
    static User myUser;
    @BindView(R.id.name_profile_fragment)
    TextView name;
    @BindView(R.id.mobile_number_profile_fragment)
    TextView mobileNumber;
    @BindView(R.id.mail_profile_fragment)
    TextView email;
    @BindView(R.id.interestRV)
    RecyclerView recyclerView;

    @BindView(R.id.range_profile_fragment)
    SeekBar range;

    EventDao eventDao;
    FirebaseAuth mAuth;
    InterestRecycleAdapter adapter;
    LinearLayoutManager manager;

    public void setupAdapter(View view,User user) {

        adapter = new InterestRecycleAdapter(getContext(),user);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();

        adapter.addInterest(getResources().getString(R.string.Football));
        adapter.addInterest(getResources().getString(R.string.Badminton));
        adapter.addInterest(getResources().getString(R.string.Handball));
        adapter.addInterest(getResources().getString(R.string.Running));
        adapter.addInterest(getResources().getString(R.string.Fitness));
        adapter.addInterest(getResources().getString(R.string.Boardgames));
        adapter.addInterest(getResources().getString(R.string.Social));
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
        Log.d("marko", "UID" + mAuth.getUid());
        eventDao.getCurrentUser(mAuth.getUid()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(User user) {
                Log.d("marko", "onSuccess: user fetched from database and ready to fill!");
                fillProfileInformations(user);
                myUser = user;
                setupAdapter(view,user);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("marko", "onError: user couldnt be fetched from database!" + e.getMessage());
            }
        });

    }

    private void fillProfileInformations(User user) {
        name.setText(user.getName());
        email.setText(user.getEmail());
        range.setProgress(user.getRange());
        mobileNumber.setText("+" + user.getPhoneNumber());
    }

    public static void addInterestToUser(String interest) {
        myUser.addInterest(interest);
    }

    public static void removeInterestFromUser(String interest) {
        myUser.removeInterest(interest);
    }

}
