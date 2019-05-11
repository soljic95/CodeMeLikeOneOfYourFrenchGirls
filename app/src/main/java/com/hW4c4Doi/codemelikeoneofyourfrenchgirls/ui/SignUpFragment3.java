package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.UpdateUserId;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui.MainActivity;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.FirebaseViewModel;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.MyViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui.SignUpFragment4.PASSED_USER_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment3 extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private User user;
    private Uri mPictureUri;

    @BindView(R.id.btnComplete)
    Button btnComplete;

    @BindView(R.id.profilePicture)
    public ImageView profilePicture;

    @BindView(R.id.btnGallery)
    public Button btnGallery;

    @BindView(R.id.btnCamera)
    public Button btnCamera;

    private FirebaseViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(getActivity(), new MyViewModelFactory(getActivity().getApplication(), EventDatabase.getInstance(getContext())))
                .get(FirebaseViewModel.class);
        user = getArguments().getParcelable(PASSED_USER_TAG);


    }

    @OnClick(R.id.btnComplete)
    void completeAndCreateUserAccount() {
        user.setProfilePictureUri(mPictureUri.toString());
        viewModel.createUser(user);
        getContext().startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }


    @OnClick(R.id.btnGallery)
    void openFileChooser() {

        startActivityForResult(new Intent()
                .setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT), PICK_IMAGE_REQUEST);
    }

    @OnClick(R.id.btnCamera)
    void cameraIntent() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mPictureUri = data.getData();
            Glide
                    .with(SignUpFragment3.this)
                    .load(mPictureUri)
                    .circleCrop()
                    .into(profilePicture);

        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            mPictureUri = data.getData();
            Glide
                    .with(SignUpFragment3.this)
                    .load(mPictureUri)
                    .circleCrop()
                    .into(profilePicture);

        }
    }
}
