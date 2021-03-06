package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.bluetooth.le.ScanRecord;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter.InterestRecycleAdapter;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter.UpcomingEventsRecyclerViewAdapter;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDao;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.FirebaseViewModel;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment {
    public User myUser;
    @BindView(R.id.name_profile_fragment)
    TextView name;
    @BindView(R.id.mobile_number_profile_fragment)
    TextView mobileNumber;
    @BindView(R.id.mail_profile_fragment)
    TextView email;
    @BindView(R.id.picture_profile_fragment)
    ImageView profilePicture;
    @BindView(R.id.interestRV)
    RecyclerView recyclerView;
    @BindView(R.id.saveUser)
    MaterialButton saveButton;
    @BindView(R.id.range_profile_fragment)
    SeekBar range;

    EventDao eventDao;
    FirebaseAuth mAuth;
    FirebaseViewModel viewModel;
    InterestRecycleAdapter adapter;
    LinearLayoutManager manager;
    FirebaseStorage storage;

    public void setupAdapter(View view,User user) {

        adapter = new InterestRecycleAdapter(getContext(),this);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();

        adapter.addInterest(getResources().getString(R.string.Football));
        adapter.addInterest(getResources().getString(R.string.Badminton));
        adapter.addInterest(getResources().getString(R.string.Handball));
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

    @OnClick(R.id.saveUser)
    public void saveCurrentUser(){
        if(myUser != null){
            myUser.setRange(range.getProgress());
            Completable.fromAction(()-> eventDao.updateUser(myUser)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onComplete() {
                    Log.d("marko", "User updated");
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("marko", "onError: " +e);
                }
            });
            viewModel.updateUserInFirebas(myUser);
        }
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        eventDao = EventDatabase.getInstance(getContext()).getEventDao();
        mAuth = FirebaseAuth.getInstance();
        viewModel = ViewModelProviders.of(getActivity()).get(FirebaseViewModel.class);
        storage = FirebaseStorage.getInstance();
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
                myUser = user;
                Glide
                        .with(getContext())
                        .load(myUser.getProfilePictureUrl())
                        .circleCrop()
                        .into(profilePicture);
                fillProfileInformations();
                setupAdapter(view,user);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("marko", "onError: user couldnt be fetched from database!" + e.getMessage());
            }
        });

    }

    private void fillProfileInformations() {
        name.setText(myUser.getName());
        email.setText(myUser.getEmail());
        range.setProgress(myUser.getRange());
        mobileNumber.setText("+" + myUser.getPhoneNumber());
    }
}
