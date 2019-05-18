package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.FirebaseViewModel;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.MyViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

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
    // Create a storage reference from our app
    private StorageReference storageRef;
    private FirebaseStorage firebaseStorage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        firebaseStorage = FirebaseStorage.getInstance();
        storageRef = firebaseStorage.getReference("ProfilePictures");
        viewModel = ViewModelProviders.of(getActivity(), new MyViewModelFactory(getActivity().getApplication(), EventDatabase.getInstance(getContext())))
                .get(FirebaseViewModel.class);
        btnComplete.setEnabled(false);
        user = getArguments().getParcelable(PASSED_USER_TAG);


    }

    @OnClick(R.id.btnComplete)
    void completeAndCreateUserAccount() {
        //user.setProfilePictureUri(mPictureUri.toString());

        // Creating user in Firebase and sending event to create it in Room database
        viewModel.registerUserInFirebase(user).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                getContext().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });


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
            uploudProfilePicture(mPictureUri);
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

    private void uploudProfilePicture(Uri uri) {
        final StorageReference fileReferance = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(mPictureUri));
        UploadTask uploadTask = fileReferance.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("marko", "picture could not be uplouded" + e);
            }
        }).
                addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileReferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                user.setProfilePictureUri(uri.toString());
                                btnComplete.setEnabled(true);
                            }
                        });

                    }
                });

    }
    private String getFileExtension(Uri uri) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}
